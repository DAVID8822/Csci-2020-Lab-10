package csci2020u.lab10.server;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Application {
    static TextArea textBox = new TextArea();
    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setScene(scene);
        grid.add(textBox,0,0);


        Button exitButton = new Button("Exit");
        grid.add(exitButton,0,2);
        exitButton.setOnAction(e -> {
            System.exit(0);
        });
        primaryStage.show();
        Thread serverThread = new Thread((Runnable) new Server());
        serverThread.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}




class Server implements Runnable{

    ServerSocket serverSocket;
    Socket socket;
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(1234);
    }
    public void run() {
        while(true){
            socket = null;

            try {
                socket = serverSocket.accept(); // socket object to receive incoming client requests
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                Thread thread = new ClientHandler(socket,dis,dos);
                thread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }




        }
    }

}

class ClientHandler extends Thread implements Runnable {
    private Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos){
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    public void run(){
        try {
            while(true) {
                BufferedReader in = new BufferedReader(new InputStreamReader(dis));
                Main.textBox.appendText(in.readLine());
                Main.textBox.appendText( "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


