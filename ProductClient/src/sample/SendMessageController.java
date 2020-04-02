package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SendMessageController implements Initializable {
    PrintWriter pr1;


    @FXML
    AnchorPane root;

    @FXML
    private TextArea textAreaMessage;
    @FXML
    private TextField textFieldSendingWhom;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(Main.sendStatus==4){
            textFieldSendingWhom.setText(ProductController.currentUploader);
            txtSubject.setText(ProductController.currentSubject);
        }else if(Main.sendStatus==3){
            textFieldSendingWhom.setText(DemandController.demander);
            txtSubject.setText(DemandController.demanderSubject);
        }else if(Main.sendStatus==2){

        }else if(Main.sendStatus==1){
            textFieldSendingWhom.setText(MyMessageController.whomReply);
        }

        try{
            pr1= Main.pr;

            if(pr1==null){
                System.exit(0);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }

    }

    @FXML
    private Button sendMessage;
    @FXML private TextField txtSubject;
//    @
//    FXML private Label sendMessageLabel;



    public void sendMessageAction(ActionEvent Event)  {
        Date date = new Date();
        SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm:ss a");
        SimpleDateFormat dateFormat=new SimpleDateFormat ("dd MMMM yyyy zzz");
        String strTime=timeFormat.format(date);
        String strDate= dateFormat.format(date);
        System.out.println("time="+strTime+" date="+strDate);

        String  subject=txtSubject.getText();
        String text= textAreaMessage.getText();

//        System.out.println("text="+text);

        text = text.replaceAll("\\r|\\n", "");
//        System.out.println("textAreaMessage.getText()="+textAreaMessage.getText());

        //System.out.println("\n\n\nNow text="+text);
        if(subject==null|| subject.equals("")){
            subject="Message";
        }


        pr1.println("#InsertMessage#"+Main.currentUser+"#"+textFieldSendingWhom.getText()+"#"+text+"#"+subject+"#"+strTime+"#"+strDate);
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("SendMessageSucessful.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Sent Message");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    @FXML
    private Button back;

    public void backAction(ActionEvent Event){
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("Product.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Product");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();

        }

    }


}
