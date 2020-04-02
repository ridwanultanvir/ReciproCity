package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;


import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.stage.*;

public class addDemandController extends AnchorPane implements Initializable {
    PrintWriter pr1;
    @FXML
    private Button btnsubmit;
    @FXML
    private Button back;
    @FXML
    private Label submitLabel;
    @FXML
    private TextField textFieldDemandFrom;
    @FXML
    private TextField textFieldDemandMessage;
    @FXML
    private void backAction(ActionEvent Event){
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("Product.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Product");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("Cannot Open Login.fxml");
        }
    }
    @FXML private void setBtnsubmit(ActionEvent event){
        String category= categoryChoice.getValue();
        if(category==null){
            submitLabel.setText("Submit Data First");
        }
        else {
            Date date = new Date();
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy zzz");
            String strTime = timeFormat.format(date);
            String strDate = dateFormat.format(date);
            System.out.println("time=" + strTime + " date=" + strDate);
            pr1.println("#AddDemand#" + Main.currentUser + "#" + textFieldDemandMessage.getText() + "#" +category+"#"+ strTime + "#" + strDate);
            submitLabel.setText("Submission Sucessful");
        }
    }
    ObservableList<String> categoryList= FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> categoryChoice;
    private void loadCategoryData(){
        categoryList.removeAll(categoryList);
        //categoryList.r
        String a="Book";
        String b="Electronic Device";
        String c="Lab Instrument";
        String d="Others";

        categoryList.addAll(a,b,c,d);

        categoryChoice.getItems().addAll(categoryList);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFieldDemandFrom.setText(Main.currentUser);
        try{
            pr1= Main.pr;
            if(pr1==null){
                System.exit(0);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        loadCategoryData();
    }
}
