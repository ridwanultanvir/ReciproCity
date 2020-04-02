package sample;


import java.io.*;
import java.net.URL;

import java.util.*;
import java.util.function.Predicate;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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


public class ProductController extends AnchorPane implements Initializable,Serializable{
    PrintWriter pr1;
    BufferedReader br1;
    ObjectInputStream ois1;

    public static String currentPost="";
    public static String currentProduct="";
    public static String currentUploader="";
    public static String currentCategory="";
    public static String currentCost="";
    public static String currentTag="";

    public static String currentSubject="";



    @FXML
    private TableView<ProductClass> productTable;

    @FXML
    private TableColumn<ProductClass, String> productCol;

    @FXML
    private TableColumn<ProductClass,String> uploaderCol;

    @FXML
    private TableColumn<ProductClass,String> categoryCol;
    @FXML
    private TableColumn<ProductClass,String> tagCol;
    @FXML
    private TableColumn<ProductClass,String> costCol;


    @FXML
    private TextField searchBox;

    @FXML
    private Button btn_my_profile;

    @FXML
    private Button btn_messages;

    @FXML
    private Button btn_postings;

    @FXML
    private Button btn_addProduct;


    public  static  ObservableList<ProductClass> productData = FXCollections.observableArrayList();
    public static ObservableList<ProductClass> bookData= FXCollections.observableArrayList();

    public static ObservableList<ProductClass> electronicData= FXCollections.observableArrayList();

    public static ObservableList<ProductClass> labData= FXCollections.observableArrayList();

    public static ObservableList<ProductClass> otherData= FXCollections.observableArrayList();


    @FXML
    public void profileAction(ActionEvent event) {

        try {
            productData.clear();
            bookData.clear();
            labData.clear();
            otherData.clear();
            electronicData.clear();


            Parent homePage=FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("My Profile");
            homeStage.show();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cannot Open MyProfile.fxml");
        }
    }
    private boolean selectedTableItem=false;
    @FXML
    private void displayParticularTableItem(MouseEvent event){
        ProductClass Item= productTable.getSelectionModel().getSelectedItem();
        if(Item==null){
            selectedTableItem=false;

        }else{
            selectedTableItem=true;
            currentPost= Item.getPostNo();

            currentProduct= Item.getProductName();
            currentUploader= Item.getProductUploader();
            currentCategory= Item.getProductCategory();
            currentCost= Item.getProductCost();
            currentTag= Item.getProductTag();

            currentSubject= Item.getProductName();
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
        uploaderCol.setCellValueFactory(new PropertyValueFactory<ProductClass,String>("productUploader"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<ProductClass,String>("productCategory"));
        tagCol.setCellValueFactory(new PropertyValueFactory<ProductClass,String>("productTag"));
        costCol.setCellValueFactory(new PropertyValueFactory<ProductClass,String>("productCost"));
        getProductData();

        productTable.setItems(productData);


    }

    @FXML private void gotoSpecificProduct(ActionEvent event){
        if(selectedTableItem) {
            try {
                productData.clear();
                bookData.clear();
                labData.clear();
                otherData.clear();
                electronicData.clear();
                gotoSpecificProductLabel.setText("Processing ...");

                Parent homePage = FXMLLoader.load(getClass().getResource("SpecificProduct.fxml"));
                Scene homePageScene = new Scene(homePage);

                Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                homeStage.setScene(homePageScene);
                homeStage.setTitle("Specific Product");
                homeStage.show();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }else{
            gotoSpecificProductLabel.setText("Select Table Item First");

        }
    }
    @FXML Label gotoSpecificProductLabel;

    public  void getProductData() {
        pr1.println("#GiveProductData#" + Main.currentUser);
        while (true){
            try{
                String recMessage= br1.readLine();
                System.out.println(recMessage);

                if(recMessage.equals("ProductDataFinished")){

                    break;
                }


                String[] strings= recMessage.split("#");
                if(strings.length>=6) {
                    productData.add(new ProductClass(strings[0], strings[1],strings[2],strings[3],strings[4],strings[5]));
                    if(strings[3].equals("Others")){
                        otherData.add(new ProductClass(strings[0], strings[1],strings[2],strings[3],strings[4],strings[5]));

                    }else if(strings[3].equals("Lab Instrument")){
                        labData.add(new ProductClass(strings[0], strings[1],strings[2],strings[3],strings[4],strings[5]));
                    }else if(strings[3].equals("Electronic Device")){
                        electronicData.add(new ProductClass(strings[0], strings[1],strings[2],strings[3],strings[4],strings[5]));
                    }else if(strings[3].equals("Book")){
                        bookData.add(new ProductClass(strings[0], strings[1],strings[2],strings[3],strings[4],strings[5]));
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                //System.out.println("recMessage failure");
            }
            //Electronic Device"   Lab Instrument Others

        }
    }


    public static class ProductClass {
        final private SimpleStringProperty postNo;
        final private SimpleStringProperty productName;
        final private SimpleStringProperty productUploader;
        final private SimpleStringProperty productCategory;
        final private SimpleStringProperty productCost;
        final private SimpleStringProperty productTag;


        //final private SimpleStringProperty frequency;

        ProductClass(String postNo,String productName, String productUploader, String  productCategory,String productCost, String productTag) {

            this.postNo= new SimpleStringProperty(postNo);
            this.productName = new SimpleStringProperty(productName);
            this.productUploader= new SimpleStringProperty(productUploader);
            this.productCategory= new SimpleStringProperty(productCategory);
            this.productCost= new SimpleStringProperty(productCost);
            this.productTag= new SimpleStringProperty(productTag);



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

    }

    @FXML
    public void searchRecord(ActionEvent event) {

        FilteredList filteredList = new FilteredList(productData);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((Predicate<? super ProductClass>) (ProductClass p) -> {

                //System.out.println("p.getFullWord()=" +p.getFullWord());

                if (p.getProductName().contains(newValue)) {
                    return true;
                }


                return false;
            });


        });
        SortedList sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(productTable.comparatorProperty());

        productTable.setItems(sortedList);
    }
    @FXML
    private TextField searchTag;


    @FXML
    private void searchTag(ActionEvent event){
        FilteredList filteredList = new FilteredList(productData,e->true);

        searchTag.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((Predicate<? super ProductClass>) (ProductClass p) -> {


            if (p.getProductTag().contains(newValue)) {
                    return true;
            }


                return false;
            });


        });
        SortedList sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(productTable.comparatorProperty());

        productTable.setItems(sortedList);

    }

    @FXML
    private void addProduct(ActionEvent event) throws IOException{
        productData.clear();
        bookData.clear();
        labData.clear();
        otherData.clear();
        electronicData.clear();

        Parent homePage=FXMLLoader.load(getClass().getResource("addProduct.fxml"));
        Scene homePageScene=new Scene(homePage);

        Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
        homeStage.setScene(homePageScene);
        homeStage.show();

    }

    @FXML
    private Button ch_book;
    @FXML
    private Button ch_lab;
    @FXML
    private Button ch_electrical;
    @FXML
    private Button ch_other;
    @FXML
    private Button ch_all;

    @FXML
    private void setCh_book(ActionEvent event){
        productTable.setItems(bookData);

    }

    @FXML
    private void setCh_electrical(ActionEvent event){
        productTable.setItems(electronicData);
    }

    @FXML
    private void setCh_lab(ActionEvent event){
        productTable.setItems(labData);
    }

    @FXML
    private void setCh_other(ActionEvent event){

        productTable.setItems(otherData);
    }

    @FXML
    private void setCh_all(ActionEvent event){
        productTable.setItems(productData);

    }

    @FXML private void myProductAction(ActionEvent Event){
        try{
            productData.clear();
            bookData.clear();
            labData.clear();
            otherData.clear();
            electronicData.clear();

            Parent homePage=FXMLLoader.load(getClass().getResource("MyProduct.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("My Product");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("Cannot Open Login.fxml");
        }
    }

    @FXML private void demandAction(ActionEvent Event){
        try{
            productData.clear();
            bookData.clear();
            labData.clear();
            otherData.clear();
            electronicData.clear();

            Parent homePage=FXMLLoader.load(getClass().getResource("addDemand.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Add Demand");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("Cannot Open Login.fxml");
        }
    }
    @FXML
    private Button btnAllDemand;
    @FXML
    private void  allDemand(ActionEvent Event){



        try{
            productData.clear();
            bookData.clear();
            labData.clear();
            otherData.clear();
            electronicData.clear();

            Parent homePage=FXMLLoader.load(getClass().getResource("Demand.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Demand");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("Cannot Open Login.fxml");
        }


    }






}

