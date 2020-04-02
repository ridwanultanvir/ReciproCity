package sample;

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
import java.io.*;

public class SoldItemController  extends AnchorPane implements Initializable {
    PrintWriter pr1;
    BufferedReader br1;
    ObjectInputStream ois1;

    @FXML
    private TableView<ProductClass> productTable;

    @FXML
    private TableColumn<ProductClass, String> productCol;

    @FXML
    private TableColumn<ProductClass,String> timeCol;
    @FXML
    private TableColumn<ProductClass,String> buyerCol;
    @FXML
    private TableColumn<ProductClass,String> dateCol;


    public  static  ObservableList<ProductClass> productData = FXCollections.observableArrayList();



    @FXML
    private void goBack(ActionEvent event){
        try{

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
        try{
            pr1= Main.pr;
            br1= Main.br;
            ois1= Main.ois;
        }catch (Exception e){
            e.printStackTrace();
        }

        productCol.setCellValueFactory(new PropertyValueFactory<ProductClass, String>("productName"));
        buyerCol.setCellValueFactory(new PropertyValueFactory<ProductClass,String>("productUploader"));
        ///here productUploader= actually buyer
        timeCol.setCellValueFactory(new PropertyValueFactory<ProductClass,String>("productTime"));
        dateCol.setCellValueFactory(new PropertyValueFactory<ProductClass,String>("productDate"));
        getProductData();

        productTable.setItems(productData);


    }



    public  void getProductData() {
        pr1.println("#GiveMySoldItem#"+Main.currentUser);

        try {
            String str = br1.readLine();
            System.out.println("str=" + str);
            int totalPost = Integer.parseInt(str);
            System.out.println("\n\ntotalPost="+totalPost);
            productData.clear();
            for (int i = 0; i < totalPost; i++) {

                PInfo x = (PInfo) ois1.readObject();
                System.out.println("x.uploaderId="+x.uploaderId);

                //System.out.println("post=" + x.postNo + "\t" + x.productName + " " + x.uploaderId + " " + x.category + " " + x.cost);
                productData.add(new ProductClass( x.productName, x.uploaderId,x.time,x.date));

            }


        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }



    ///here uploader = buyer
    public static class ProductClass {

        final private SimpleStringProperty productName;
        final private SimpleStringProperty productUploader;

        final private SimpleStringProperty productDate;
        final private SimpleStringProperty productTime;



        ProductClass(String productName, String productUploader,String productDate, String productTime) {


            this.productName = new SimpleStringProperty(productName);
            this.productUploader= new SimpleStringProperty(productUploader);

            this.productDate= new SimpleStringProperty(productDate);
            this.productTime= new SimpleStringProperty(productTime);

        }



        public String getProductName() {
            return productName.get();
        }

        public String getProductUploader() {
            return productUploader.get();
        }

        public String getProductTime(){return productTime.get();}
        public String getProductDate(){return productDate.get(); }





        public void setProductName(String fName) {
            productName.set(fName);
        }

        public void setProductUploader(String fName) {
            productUploader.set(fName);
        }

        public void setProductDate(String f){ productDate.set(f);}
        public void setProductTime(String f){ productTime.set(f);}

    }





}
