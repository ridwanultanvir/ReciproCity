package sample;
import java.io.*;
import java.net.URL;

import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

public class DemandController  extends AnchorPane implements Initializable {
    PrintWriter pr1;
    BufferedReader br1;



    public ObservableList<DemandClass> demandData = FXCollections.observableArrayList();



    @FXML
    private TableView<DemandClass> demandTable;

    @FXML
    private TableColumn<DemandClass, String> fromCol;

    @FXML
    private TableColumn<DemandClass,String> fromMessageCol;
    @FXML
    private TableColumn<DemandClass,String> fromTimeCol;
    @FXML
    private TableColumn<DemandClass,String> fromDateCol;



    public ObservableList<DemandClass> getDemandData() {

        return demandData;
    }



    @FXML
    private Button back;
    @FXML
    public void backAction(ActionEvent event){
        try{
            demandData.clear();

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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            pr1= Main.pr;
            br1= Main.br;


        }catch (Exception e){
            e.printStackTrace();
        }


        pr1.println("#GetAllDemandData#" + Main.currentUser);


        while (true){
            try{
                String recMessage= br1.readLine();
                System.out.println(recMessage);

                if(recMessage.equals("AllDemandDataSentFromServer")){

                    break;
                }


                String[] strings= recMessage.split("#");
                if(strings.length>=4) {
                    demandData.add(new DemandClass(strings[0], strings[1],strings[2],strings[3]));
                }

            }catch (Exception e){
                e.printStackTrace();

            }

        }

        fromCol.setCellValueFactory(new PropertyValueFactory<DemandClass, String>("from"));
        fromMessageCol.setCellValueFactory(new PropertyValueFactory<DemandClass,String>("receiveMessage"));
        fromTimeCol.setCellValueFactory(new PropertyValueFactory<DemandClass,String>("receiveTime"));
        fromDateCol.setCellValueFactory(new PropertyValueFactory<DemandClass,String>("receiveDate"));


        demandTable.setItems(getDemandData());

    }
    private boolean selectedTableItem=false;
    public static String demander;
    public static String demanderSubject;
    @FXML
    private void selectParticularTableItem(MouseEvent event){
        DemandClass Item= demandTable.getSelectionModel().getSelectedItem();
        if(Item==null){
            selectedTableItem=false;


        }else{
            selectedTableItem=true;
            demander=Item.getFrom();
            demanderSubject= Item.getReceiveMessage();

        }
    }

    @FXML private Button btnSendMessageDemand;
    @FXML private Label labelSendMessage;
    @FXML private void setBtnSendMessageDemand(ActionEvent Event){
        if(selectedTableItem){
            Main.sendStatus=3;

            try{
                demandData.clear();

                Parent homePage=FXMLLoader.load(getClass().getResource("SendMessage.fxml"));
                Scene homePageScene=new Scene(homePage);

                Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
                homeStage.setTitle("Send Message");
                homeStage.setScene(homePageScene);
                homeStage.show();
            }catch (Exception e){
                e.printStackTrace();
                //System.out.println("Cannot Open Login.fxml");
            }

        }else{
            labelSendMessage.setText("First Select Table Row");
        }


    }


    public static class DemandClass {
        final private SimpleStringProperty from;
        final private SimpleStringProperty receiveMessage;
        final private SimpleStringProperty receiveTime;
        final private SimpleStringProperty receiveDate;



        //final private SimpleStringProperty frequency;

        DemandClass(String fullWord, String receiveMessage,String receiveTime,String receiveDate) {
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





}
