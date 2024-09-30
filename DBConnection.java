import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_grading_system";
    private static final String USER = "root";
    private static final String PASS = "leen@2004";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Ensure MySQL driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

