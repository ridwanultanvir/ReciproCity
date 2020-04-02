package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class MySentMessageController extends AnchorPane implements Initializable {



    public static ObservableList<MySentMessageController.ReceiveClass> messageData = FXCollections.observableArrayList();
    

    
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
    private Button back;

    public void backAction(ActionEvent event){
        try{
            messageData.clear();

            Parent homePage=FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("My Profile");
            homeStage.show();


        }catch (Exception e){
            e.printStackTrace();
        }

    }
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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

        ReceiveClass(String from, String receiveMessage,String receiveTime,String receiveDate) {
            this.from = new SimpleStringProperty(from);
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
