package sample;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.image.Image;



/**
 * Login Controller.
 */
public class LoginController extends AnchorPane implements Initializable {

    PrintWriter pr1;
    BufferedReader br1;
    ObjectInputStream ois1;

    @FXML
    TextField userId;
    @FXML
    PasswordField password;
    @FXML
    Button login;
    @FXML
    Label messageForLogin;
    @FXML
    Button btn_signup;
    @FXML
    AnchorPane root;
    @FXML
    ImageView imageView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageForLogin.setText("");
        try{
            pr1= Main.pr;
            br1= Main.br;
            if(pr1==null){
                System.exit(0);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        Image image= new Image("file:ClientImage/login.jpg");
        imageView.setImage(image);



    }


    @FXML
    private void processLogin(ActionEvent Event) throws IOException, ClassNotFoundException {

        System.out.println(userId.getText()+" "+password.getText());
        messageForLogin.setText("Processing ...");
        System.out.println("pr1="+pr1+" br1="+br1);
        String str=null;
        System.out.println("userId="+userId.getText().length());
        //System.out.println("password="+password.getText()+" length="+sizpassword.getText());

        if(userId.getText().length()==0 || password.getText().length()==0){
            messageForLogin.setText("Fill up both Userid and Password");

        }
        else {

            pr1.println("#login#" + userId.getText() + "#" + password.getText());

            while (true) {

                try {
                    str = br1.readLine();

                    if (str.equals("loginOk")) {
                        Main.currentUser = userId.getText();

                        try {

                            Parent homePage = FXMLLoader.load(getClass().getResource("Product.fxml"));
                            Scene homePageScene = new Scene(homePage);

                            Stage homeStage = (Stage) ((Node) Event.getSource()).getScene().getWindow();
                            homeStage.setScene(homePageScene);
                            homeStage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            //System.out.println("Cannot Open Login.fxml");
                        }

                    } else {
                        messageForLogin.setText("Wrong UserID or Password");

                    }
                    break;


                } catch (Exception e) {
                    e.printStackTrace();
                    break;


                }
            }
        }


    }



    @FXML
    public void processSignUp(ActionEvent event){


        try {
            Parent homePage=FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Create Account");
            homeStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


