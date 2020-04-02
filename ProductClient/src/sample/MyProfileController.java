
package sample;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;


public class MyProfileController extends AnchorPane implements Initializable {
    BufferedReader br1;
    PrintWriter pr1;


    @FXML
    private AnchorPane root;
    @FXML
    private Label userId;
    @FXML
    private Label nickName;
    @FXML
    private Label emailId;
    @FXML
    private Label hall;
    @FXML
    private Label room;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //errorMessage.setText("");
        userId.setText(Main.currentUser);
        try{
            pr1= Main.pr;
            System.out.println("pr1="+pr1);
            br1= Main.br;
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        pr1.println("#GetProfileData#"+Main.currentUser);
        try{
            String str= br1.readLine();
            String[] strings= str.split("#");
            if(strings.length>=6 && strings[1].equals("ReceiveProfileData")){
                nickName.setText(strings[3]);
                emailId.setText(strings[2]);
                hall.setText(strings[4]);
                room.setText(strings[5]);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void goToNotificationSoldItem(ActionEvent event){
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("SoldItem.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Notification");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();

        }
    }


    @FXML
    private Button btnLogout;
    @FXML
    private void logoutAction(ActionEvent Event){
        try{
            pr1.println("#logout#");

            Parent homePage=FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Cannot Open Login.fxml");
        }
    }

    @FXML
    private Button btnMyMessage;
    @FXML
    private void  myMessageAction(ActionEvent Event){
        System.out.println("br1="+br1+" pr1="+pr1);

        pr1.println("#getMessageData#" + Main.currentUser);




        while (true){
            try{
                String recMessage= br1.readLine();
                System.out.println(recMessage);

                if(recMessage.equals("AllReceivedSentFromServer")){

                    break;
                }


                String[] strings= recMessage.split("#");
                if(strings.length>=4) {
                    MyMessageController.messageData.add(new MyMessageController.ReceiveClass(strings[0], strings[1],strings[2],strings[3]));
                }

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("recMessage failure");
            }

        }
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("MyMessage.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Inbox");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("Cannot Open Login.fxml");
        }


    }

    @FXML
    private Button btnMySentMessage;
    @FXML
    private void  mySentMessageAction(ActionEvent Event){

        pr1.println("#getSentMessageData#" + Main.currentUser);

        while (true){
            try{
                String recMessage= br1.readLine();
                System.out.println(recMessage);

                if(recMessage.equals("YourSentMessageFinished")){

                    break;
                }


                String[] strings= recMessage.split("#");
                if(strings.length>=4) {
                    MySentMessageController.messageData.add(new MySentMessageController.ReceiveClass(strings[0], strings[1],strings[2],strings[3]));
                }

            }catch (Exception e){
                e.printStackTrace();
                //System.out.println("recMessage failure");
            }

        }
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("MySentMessage.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("SentMessage");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("Cannot Open Login.fxml");
        }


    }

    @FXML
    private Button back;

    public void backAction(ActionEvent event){
        try{


            Parent homePage=FXMLLoader.load(getClass().getResource("Product.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Product");
            homeStage.show();


            //System.out.println("Send Message Action Successful");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Cannot Open Product.fxml");
        }

    }


}