import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Handle server messages in new thread
            new Thread(() -> {
                String fromServer;
                try {
                    while ((fromServer = in.readLine()) != null) {
                        System.out.println(fromServer);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            // Handle user input
            String input;
            while ((input = consoleReader.readLine()) != null) {
                out.println(input);
            }

        } catch (IOException e) {
            System.out.println("Unable to connect to server.");
            e.printStackTrace();
        }
    }
}
