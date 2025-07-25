import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.lang.classfile.Label;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClientGUI extends Application {

    private TextArea chatArea;
    private TextField inputField;
    private TextField nameField;
    private PrintWriter out;
    private String username;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Login");

        Label nameLabel = new Label("Enter your name:");
        nameField = new TextField();
        Button joinButton = new Button("Join Chat");

        VBox loginLayout = new VBox(10, nameLabel, nameField, joinButton);
        loginLayout.setPadding(new Insets(20));
        Scene loginScene = new Scene(loginLayout, 300, 150);
        primaryStage.setScene(loginScene);
        primaryStage.show();

        joinButton.setOnAction(e -> {
            username = nameField.getText().trim();
            if (!username.isEmpty()) {
                primaryStage.close();
                showChatWindow();
                connectToServer();
            }
        });
    }
private void showChatWindow() {
    Stage chatStage = new Stage();
    chatStage.setTitle("Chat - " + username);

    chatArea = new TextArea();
    chatArea.setEditable(false);
    chatArea.setWrapText(true);

    // FULLY TRANSPARENT TEXTAREA
    chatArea.setStyle(
        "-fx-control-inner-background: transparent;" +
        "-fx-background-color: transparent;" +
        "-fx-background-insets: 0;" +
        "-fx-padding: 10;" +
        "-fx-font-size: 14px;" +
        "-fx-text-fill: black;" +
        "-fx-border-color: transparent;" +
        "-fx-focus-color: transparent;" +
        "-fx-faint-focus-color: transparent;" +
        "-fx-highlight-fill: transparent;" +
        "-fx-highlight-text-fill: black;" +
        "-fx-display-caret: true;" +
        "-fx-background-radius: 10;" +
        "-fx-border-radius: 10;"
    );

    inputField = new TextField();
    inputField.setPromptText("Type a message...");
    inputField.setStyle(
        "-fx-background-color: rgba(255,255,255,0.3);" +
        "-fx-text-fill: black;" +
        "-fx-prompt-text-fill: gray;" +
        "-fx-font-size: 14px;" +
        "-fx-background-radius: 10;" +
        "-fx-border-color: transparent;"
    );

    inputField.setOnAction(e -> {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            out.println(message);
            inputField.clear();
        }
    });

    VBox layout = new VBox(10, chatArea, inputField);
    layout.setPadding(new Insets(10));
    VBox.setVgrow(chatArea, Priority.ALWAYS);

    StackPane root = new StackPane();
    root.getChildren().add(layout);

    try {
        Image image = new Image(new FileInputStream("wallpaper.jpg"));
        BackgroundImage bg = new BackgroundImage(
            image,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        root.setBackground(new Background(bg));
    } catch (FileNotFoundException e) {
        System.out.println("⚠️ Wallpaper not found. Skipping background.");
    }

    Scene scene = new Scene(root, 500, 400);
    chatStage.setScene(scene);
    chatStage.show();
}

private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 1234);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    out.println(username); // send username to server
                    String message;
                    while ((message = in.readLine()) != null) {
                        String finalMessage = message;
                        javafx.application.Platform.runLater(() -> {
                            chatArea.appendText(finalMessage + "\n");
                        });
                    }
                } catch (IOException e) {
                    javafx.application.Platform.runLater(() -> {
                        chatArea.appendText("⚠️ Disconnected from server.\n");
                    });
                }
            }).start();
        } catch (IOException e) {
            javafx.application.Platform.runLater(() -> {
                chatArea.appendText("❌ Unable to connect to server.\n");
            });
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
