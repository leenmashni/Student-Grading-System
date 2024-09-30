import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

public class StudentGradingCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Student Grading System");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (authenticateUser(email, password)) {
            System.out.println("Login successful!");
            showGrades(email);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    public static boolean authenticateUser(String email, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT password FROM students WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
             String storedHash = rs.getString("password");
             return BCrypt.checkpw(password, storedHash);
            }
        } catch (Exception e) {
            System.out.println("Error during authentication: " + e.getMessage());
        }
        return false;
    }

    public static void showGrades(String email) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT courses.course_name, grades.grade FROM students " +
                    "JOIN grades ON students.student_id = grades.student_id " +
                    "JOIN courses ON grades.course_id = courses.course_id " +
                    "WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            System.out.println("Your Grades:");
            while (rs.next()) {
                System.out.println("Course: " + rs.getString("course_name") + " | Grade: " + rs.getDouble("grade"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving grades: " + e.getMessage());
        }
    }
}

