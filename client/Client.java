package csci2020u.lab10.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Application {
    private Socket socket = new Socket("localhost", 1234);
    private BufferedReader in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
    private PrintWriter out  = new PrintWriter(socket.getOutputStream());
    Label userLabel=new Label("Username");
    Label messageLabel = new Label("Message");
    TextField usernameField = new TextField();
    TextField messageField = new TextField();;
    Button exit = new Button("Exit");
    Button send = new Button("Send");

    public Client() throws IOException {
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        send.setOnAction(e->{
            out.println(usernameField.getText() + ": " + messageField.getText());
            out.flush();
        });
        exit.setOnAction(e->{
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.exit(0);
        });
        GridPane root = new GridPane();
        root.setHgap(5);
        root.setVgap(5);
        root.addRow(0, userLabel, usernameField);
        root.addRow(1, messageLabel, messageField);
        root.addRow(2,send);
        root.addRow(3,exit);
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}