/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.*;

/**
 *
 * @author USER
 */

public class addProductController extends AnchorPane implements Initializable {
    static int addedImage=0; ;

    PrintWriter pr1;



     @FXML
    private ImageView productImage;

    @FXML
    private ImageView productImage1;
    @FXML
    private ImageView productImage2;
    @FXML
    private ImageView productImage3;


    @FXML
    private TextField label_caption;

    @FXML
    private Label label_profile;

    @FXML
    private Button btn_next;

    @FXML
    private Button btn_back;

    @FXML
    private TextField product_tag;

    @FXML
    private TextField product_name;

    @FXML
    private Button btn_chooseImage;

    @FXML
    private TextField product_cost;
    @FXML
    private TextField product_category;

    @FXML
    private Button btn_submit;

    @FXML
    private Button btn_selectImage;
    @FXML
    private ChoiceBox<String> categoryChoice;

    ObservableList<String>categoryList= FXCollections.observableArrayList();

    private void loadCategoryData(){

        categoryList.removeAll(categoryList);

        String a="Book";
        String b="Electronic Device";
        String c="Lab Instrument";
        String d="Others";

        categoryList.addAll(a,b,c,d);

        categoryChoice.getItems().addAll(categoryList);

    }

    @FXML
    private Label categoryLabel;
    @FXML
    private Label labelForSubmitData;



    @FXML
    private void submitAction(ActionEvent event){
        Date date = new Date();
        SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm:ss a");
        SimpleDateFormat dateFormat=new SimpleDateFormat ("dd MMMM yyyy zzz");
        String strTime=timeFormat.format(date);
        String strDate= dateFormat.format(date);
        System.out.println("time="+strTime+" date="+strDate);

        String category= categoryChoice.getValue();
        String cost= product_cost.getText();
        String name= product_name.getText();
        String tag= product_tag.getText();
        if(category==null){
            category="Others";
        }
        if(cost==null){
            cost= "";
        }
        if(name==null){
            name="Others";
        }
        if(tag==null){
            tag="Others";
        }



        pr1.println("#AddProduct#" + name + "#" + cost + "#" + tag+ "#" + category + "#" + Main.currentUser + "#"+Integer.toString(addedImage)+"#"+strTime+"#"+strDate);
        //labelForSubmitData.setText("Data Submitted Sucessful");
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("addProductSucessful.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Added Product");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    static String name="";
    static String path="";
    static int imageNo= 1;
    static File sfile;




    @FXML
    public void choosingImage(ActionEvent event) throws FileNotFoundException {

        FileChooser fc = new FileChooser();
        sfile = fc.showOpenDialog(null);

        if (sfile != null) {
            name = sfile.getName();
            path = sfile.getAbsolutePath();
        } else {
            name = null;
            path = null;
            System.out.println("file is not valid");
        }

        Image image = new Image(sfile.toURI().toString());
        productImage.setImage(image);
    }


    @FXML
    public void selectImage(ActionEvent event) {


        Image image = new Image(sfile.toURI().toString());
        switch(imageNo) {
            case 1 :
                productImage1.setImage(image);
                break;
            case 2 :
                productImage2.setImage(image);
                break;
            case 3 :
                productImage3.setImage(image);
            default :
                System.out.println("Invalid image No");
        }

        pr1.println("#GetImage#"+Integer.toString(imageNo));


        addedImage++;

        try
        {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = Main.s.getOutputStream();
            byte[] contents;
            long fileLength = file.length();
            pr1.println(String.valueOf(fileLength));		//These two lines are used
            pr1.flush();									//to send the file size in bytes.
            System.out.println("size of image inside client:"+ fileLength);


            long current = 0;

            Thread.sleep(100);

            while(current!=fileLength){
                int size = 10000;
                System.out.println("current="+current);
                if(fileLength - current >= size)
                    current += size;
                else{
                    size = (int)(fileLength - current);
                    current = fileLength;
                }
                contents = new byte[size];
                bis.read(contents, 0, size);
                os.write(contents);

            }
            os.flush();
            System.out.println("Image sent successfully (inside addProductController");
            imageNo++;
        }
        catch(Exception e)
        {
            System.out.println("Could not transfer Path="+path);
        }

    }





    @FXML
    public void backToProduct(ActionEvent event) throws IOException{

        Parent homePage=FXMLLoader.load(getClass().getResource("Product.fxml"));
        Scene homePageScene=new Scene(homePage);

        Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
        homeStage.setTitle("Product");

        homeStage.setScene(homePageScene);
        homeStage.show();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

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



