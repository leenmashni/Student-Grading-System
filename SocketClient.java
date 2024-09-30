import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             OutputStream output = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(output, true);
             InputStream input = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             Scanner scanner = new Scanner(System.in)) {

            // Prompt user for email and password
            System.out.print("Enter your email: ");
            String email = scanner.nextLine().trim(); // Get email input from user
            
            System.out.print("Enter your password: ");
            String password = scanner.nextLine().trim(); // Get password input from user

            // Send login credentials
            writer.println(email);
            writer.println(password);

            // Receive the response
            String response = reader.readLine();
            System.out.println(response);

            // If successful, receive grades
            if (response.equals("Login successful")) {
                String gradeLine;
                while ((gradeLine = reader.readLine()) != null) {
                    System.out.println(gradeLine); // Print each grade received from the server
                }
            }

        } catch (UnknownHostException e) {
            System.out.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

