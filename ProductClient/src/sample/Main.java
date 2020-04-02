package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main Application. This class handles navigation and user session.
 */


public class Main extends Application {
    InetAddress ip=null;
    static Socket s= null;
    static BufferedReader br=null;
    static PrintWriter pr= null;
    static InputStream is= null;
    static ObjectInputStream ois=null;
    static String  currentUser=null;
    static int sendStatus=0;

    {
        try {
            //ip = InetAddress.getByName("192.168.1.102");
            //ip=InetAddress.getByName("127.0.0.1");
            ip = InetAddress.getByName("172.20.17.69");
            s = new Socket(ip, 5678);

            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            pr = new PrintWriter(s.getOutputStream(), true);
            is= s.getInputStream();

            ois= new ObjectInputStream(s.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("connection failed");

        }
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Inside Start()");
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Product Share Login");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

