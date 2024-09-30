import java.io.*;
import java.net.*;
import java.sql.*;

public class SocketServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is listening on port 8080");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (InputStream input = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             OutputStream output = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(output, true)) {

            String email = reader.readLine();
            String password = reader.readLine();

            if (StudentGradingCLI.authenticateUser(email, password)) {
                writer.println("Login successful");
                StudentGradingCLI.showGrades(email); // Send grades back via socket
            } else {
                writer.println("Invalid credentials");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

