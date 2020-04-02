package sample;

import javafx.scene.input.MouseEvent;
import sample.Main;
import java.io.*;
import java.net.URL;

import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.fxml.*;
import javafx.scene.control.*;


import javafx.scene.control.cell.*;
import javafx.collections.transformation.*;
import javafx.beans.property.SimpleStringProperty;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.logging.*;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import sample.Main;
import java.lang.StringBuffer; 


public class MyMessageController extends AnchorPane implements Initializable {
    

    
    public static ObservableList<MyMessageController.ReceiveClass> messageData = FXCollections.observableArrayList();
    

    
    @FXML
    private TableView<ReceiveClass> messageTable;
    
    @FXML
    private TableColumn<ReceiveClass, String> fromCol;
    
    @FXML
    private TableColumn<ReceiveClass,String> fromMessageCol;
    @FXML
    private TableColumn<ReceiveClass,String> fromTimeCol;
    @FXML
    private TableColumn<ReceiveClass,String> fromDateCol;

    
    public ObservableList<ReceiveClass> getReceiveData() {

        return messageData;
    }
    
    @FXML
    private AnchorPane root; 
    
    @FXML
    private Button back;
    @FXML
    public void backAction(ActionEvent event){
        try{
            messageData.clear();

            Parent homePage=FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("My Profile");
            homeStage.show();


            //System.out.println("Send Message Action Successful");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Cannot Open Product.fxml");
        }

    }
    PrintWriter pr1;
    BufferedReader br1;
    

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
        
        fromCol.setCellValueFactory(new PropertyValueFactory<ReceiveClass, String>("from"));
        fromMessageCol.setCellValueFactory(new PropertyValueFactory<ReceiveClass,String>("receiveMessage"));
        fromTimeCol.setCellValueFactory(new PropertyValueFactory<ReceiveClass,String>("receiveTime"));
        fromDateCol.setCellValueFactory(new PropertyValueFactory<ReceiveClass,String>("receiveDate"));


        messageTable.setItems(getReceiveData());

    }
    
    
    public static class ReceiveClass {
        final private SimpleStringProperty from;
        final private SimpleStringProperty receiveMessage;
        final private SimpleStringProperty receiveTime;
        final private SimpleStringProperty receiveDate;



        //final private SimpleStringProperty frequency;

        ReceiveClass(String fullWord, String receiveMessage,String receiveTime,String receiveDate) {
            this.from = new SimpleStringProperty(fullWord);
            this.receiveMessage= new SimpleStringProperty(receiveMessage);
            this.receiveTime=new SimpleStringProperty(receiveTime);
            this.receiveDate= new SimpleStringProperty(receiveDate);

        }

        public String getFrom() {
            return from.get();
        }

        public String getReceiveMessage() {
            return receiveMessage.get();
        }

        public String getReceiveDate() {
            return receiveDate.get();
        }

        public String getReceiveTime() {
            return receiveTime.get();
        }





        public void setFrom(String fName) {
            from.set(fName);
        }

        public void setReceiveMessage(String fName) {
            receiveMessage.set(fName);
        }
        public void setReceiveDate(String fName) {
            receiveDate.set(fName);
        }
        public void setReceiveTime(String fName) {
            receiveTime.set(fName);
        }


    }
    private boolean selectedTableItem=false;
    @FXML Label labelReply;
    public static String whomReply="";
    public static String subjectReply="";
    @FXML
    private void particularTableItemSelected(MouseEvent event){
        ReceiveClass Item= messageTable.getSelectionModel().getSelectedItem();
        if(Item==null){
            selectedTableItem=false;
            //labelRemoveItem.setText("Select Row First");


        }else{
            selectedTableItem=true;
            whomReply=Item.getFrom();

        }
    }
    @FXML
    private void replyFromInbox(ActionEvent event){
        if(selectedTableItem){
            Main.sendStatus= 1;


            try{
                messageData.clear();
                Parent homePage=FXMLLoader.load(getClass().getResource("SendMessage.fxml"));
                Scene homePageScene=new Scene(homePage);

                Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
                homeStage.setScene(homePageScene);
                homeStage.setTitle("Send Message");
                homeStage.show();
            }catch (Exception e){
                e.printStackTrace();

            }
        }else{
            labelReply.setText("Select Row First");
        }
    }
    
    

    
   
}
