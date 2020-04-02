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



import javafx.stage.Stage;

import java.io.*;

public class MyProductController  extends AnchorPane implements Initializable {
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
    private TableColumn<ProductClass,String> costCol;
    @FXML
    private TableColumn<ProductClass,String> dateCol;


    public  static  ObservableList<ProductClass> productData = FXCollections.observableArrayList();

    private boolean selectedTableItem=false;
    @FXML Label labelRemoveItem;
    public static String selectedPostNo="";
    @FXML
    private void particularTableItemSelected(MouseEvent event){
        ProductClass Item= productTable.getSelectionModel().getSelectedItem();
        if(Item==null){
            selectedTableItem=false;
            //labelRemoveItem.setText("Select Row First");


        }else{
            selectedTableItem=true;
            selectedPostNo= Item.getPostNo();
            System.out.println("selectedPostNO="+Item.getPostNo());
        }
    }
    @FXML
    private void removeTableItem(ActionEvent event){
        if(selectedTableItem){
            pr1.println("#RemoveItem#"+selectedPostNo);
            try{
                productData.clear();

                Parent homePage=FXMLLoader.load(getClass().getResource("ProductRemove.fxml"));
                Scene homePageScene=new Scene(homePage);

                Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
                homeStage.setScene(homePageScene);
                homeStage.setTitle("Product Remove");
                homeStage.show();
            }catch (Exception e){
                e.printStackTrace();

            }
        }else{
            labelRemoveItem.setText("Select Row First");
        }
    }

    @FXML
    private void goToNotificationSoldItem(ActionEvent event){
        try{
            productData.clear();

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
    private void goToMyProfile(ActionEvent event){
        try{
            productData.clear();

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

    @FXML
    private void goBackToProduct(ActionEvent event){
        try{
            productData.clear();
            Parent homePage=FXMLLoader.load(getClass().getResource("Product.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Product");
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
            ois1=Main.ois;
        }catch (Exception e){
            e.printStackTrace();
        }

        productCol.setCellValueFactory(new PropertyValueFactory<ProductClass, String>("productName"));
        costCol.setCellValueFactory(new PropertyValueFactory<ProductClass,String>("productCost"));
        timeCol.setCellValueFactory(new PropertyValueFactory<ProductClass,String>("productTime"));
        dateCol.setCellValueFactory(new PropertyValueFactory<ProductClass,String>("productDate"));
        getProductData();

        productTable.setItems(productData);


    }



    public  void getProductData() {
        pr1.println("#GiveMyProductData#"+Main.currentUser);
        System.out.println("sent server GiveMyProductData");
        try {
            String str = br1.readLine();
            System.out.println("str=" + str);
            int totalPost = Integer.parseInt(str);
            System.out.println("\n\ntotalPost="+totalPost);
            productData.clear();
            for (int i = 0; i < totalPost; i++) {

                PInfo x = (PInfo) ois1.readObject();

                //System.out.println("post=" + x.postNo + "\t" + x.productName + " " + x.uploaderId + " " + x.category + " " + x.cost);
                productData.add(new ProductClass(x.postNo, x.productName, x.uploaderId, x.category, x.cost, x.tag,x.time,x.date));
            }



        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }




    public static class ProductClass {
        final private SimpleStringProperty postNo;
        final private SimpleStringProperty productName;
        final private SimpleStringProperty productUploader;
        final private SimpleStringProperty productCategory;
        final private SimpleStringProperty productCost;
        final private SimpleStringProperty productTag;
        final private SimpleStringProperty productDate;
        final private SimpleStringProperty productTime;



    ProductClass(String postNo,String productName, String productUploader, String  productCategory,String productCost, String productTag,String productDate, String productTime) {

        this.postNo= new SimpleStringProperty(postNo);
        this.productName = new SimpleStringProperty(productName);
        this.productUploader= new SimpleStringProperty(productUploader);
        this.productCategory= new SimpleStringProperty(productCategory);
        this.productCost= new SimpleStringProperty(productCost);
        this.productTag= new SimpleStringProperty(productTag);
        this.productDate= new SimpleStringProperty(productDate);
        this.productTime= new SimpleStringProperty(productTime);

    }

    public String getPostNo() {
        return postNo.get();
    }

    public String getProductName() {
        return productName.get();
    }

    public String getProductUploader() {
        return productUploader.get();
    }
    public String getProductCategory(){
        return productCategory.get();
    }
    public String getProductCost(){
        return productCost.get();
    }
    public String getProductTag(){
        return productTag.get();
    }
    public String getProductTime(){return productTime.get();}
    public String getProductDate(){return productDate.get(); }

    public void setPostNo(String fName) {
        postNo.set(fName);
    }



    public void setProductName(String fName) {
        productName.set(fName);
    }

    public void setProductUploader(String fName) {
        productUploader.set(fName);
    }

    public  void setProductCategory(String fName){ productCategory.set(fName);}
    public  void setProductCost(String fName){ productCost.set(fName);}
    public  void setProductTag(String fName){ productTag.set(fName);}

    public void setProductDate(String f){ productDate.set(f);}
    public void setProductTime(String f){ productTime.set(f);}

}





}
