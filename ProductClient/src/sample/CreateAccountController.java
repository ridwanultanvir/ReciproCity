package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class CreateAccountController extends AnchorPane implements Initializable {
    BufferedReader br1;
    PrintWriter pr1;

    @FXML
    private ImageView back_icon;


    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_hall;
    @FXML
    private TextField tf_room;

    @FXML
    private PasswordField tf_pw;

    @FXML
    private PasswordField tf_conpw;

    @FXML
    private Button btn_next;

    @FXML
    private Label errorMessage;

    @FXML AnchorPane root;

    private static final Map<String,String> ALLSTUDENT = new HashMap<String,String>();


    @FXML
    public void savingProfile(ActionEvent event) throws IOException{
        System.out.println("pr1=" + pr1 + " br1=" + br1);

        if(!tf_pw.getText().equals(tf_conpw.getText())){
            errorMessage.setText("Passwords Do not Match");

        }else {

            pr1.println("#CreateAccount#" + tf_id.getText() + "#" + tf_pw.getText()  + "#" + tf_email.getText() + "#" + tf_name.getText() + "#" + tf_hall.getText() + "#" + tf_room.getText());

            String str = br1.readLine();
            System.out.println("str=" + str);
            if (str.equals("#AlreadyAccount#")) {
                errorMessage.setText("Already Account Exists");
            } else if (str.equals("#CreateAccountSucessful#")) {
                System.out.println("Sucessfully Account Created !!!");
                errorMessage.setText("Sucessfully Account Created !!!");
            }
        }


    }
       

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            pr1= Main.pr;
            System.out.println("pr1="+pr1);
            br1= Main.br;
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        Image image= new Image("file:ClientImage/back_icon.jpg");
        back_icon.setImage(image);
    }
    @FXML
    private void setBack_icon(Event event){
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene homePageScene=new Scene(homePage);


            Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Login");

            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("Cannot Open Login.fxml");
        }

    }

    // public void

}
