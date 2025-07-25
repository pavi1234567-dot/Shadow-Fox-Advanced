import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatServer {
    private static final int PORT = 1234;
    private static Map<String, PrintWriter> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Server started on port " + PORT);
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            new ClientHandler(socket).start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private String userName;

        public ClientHandler(Socket socket) {
            this.socket = socket;

        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Get username
                out.println("Enter your username:");
                userName = in.readLine();
                synchronized (clientMap) {
                    clientMap.put(userName, out);
                }

                out.println("🟢 " + userName + " joined the chat!");

                String msg;
                while ((msg = in.readLine()) != null) {
                    String time = new SimpleDateFormat("hh:mm a").format(new Date());

                    // Private message
                    if (msg.startsWith("/pm")) {
                        String[] split = msg.split(" ", 3);
                        if (split.length == 3) {
                            String targetUser = split[1];
                            String message = split[2];
                            PrintWriter pw = clientMap.get(targetUser);
                            if (pw != null) {
                                pw.println("(Private) " + time + " | " + userName + ": " + message);
                                out.println("(Private to " + targetUser + ") " + time + " | " + userName + ": " + message);
                            } else {
                                out.println("User not found: " + targetUser);
                            }
                        } else {
                            out.println("Format: /pm <username> <message>");
                        }
                    } else {
                        broadcast(time + " | " + userName + ": " + msg);
                    }
                }
            } catch (IOException e) {
                System.out.println("Connection error with " + userName);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {}
                synchronized (clientMap) {
                    clientMap.remove(userName);
                }
                broadcast("🔴 " + userName + " left the chat.");
            }
        }

        private void broadcast(String message) {
            synchronized (clientMap) {
                for (PrintWriter writer : clientMap.values()) {
                    writer.println(message);
                }
            }
        }
    }
}
