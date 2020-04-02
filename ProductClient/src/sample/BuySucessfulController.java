package sample;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class BuySucessfulController extends  AnchorPane implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    @FXML
    private Button btnback;
    @FXML private Button btnNext;

    @FXML
    private void backToProduct(ActionEvent Event){
        try{

            Parent homePage= FXMLLoader.load(getClass().getResource("Product.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Product");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();

        }
    }
    @FXML
    private void gotoMyProfile(ActionEvent Event){
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("My Profile");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
