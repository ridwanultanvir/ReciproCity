package sample;
;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

import javafx.scene.control.*;
import javafx.event.*;
import java.io.BufferedReader;
import java.net.URL;
import java.util.ResourceBundle;

public class addProductSucessfulController extends AnchorPane implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
//    @FXML private Button btnback;
//    @FXML private Button btnNext;

    @FXML
    private void back(ActionEvent Event){
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("MyProfile");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();

        }
    }
    @FXML
    private void gotoMyProduct(ActionEvent Event){
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("MyProduct.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("My Product");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
