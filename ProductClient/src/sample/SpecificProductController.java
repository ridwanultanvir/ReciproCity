package sample;


import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;


public class SpecificProductController implements Initializable {
    BufferedReader br1;
    PrintWriter pr1;
    Socket s1;

    static int totalImage=0;



    @FXML private Label productLabel;
    @FXML private Label costLabel;
    @FXML private Label tagLabel;
    @FXML private Label categoryLabel;
    @FXML private Label uploaderLabel;




    @FXML private ImageView imageView1;
    @FXML private ImageView imageView2;
    @FXML private ImageView imageView3;

    @FXML private Button gotoProductButton;

    private void deleteImage(int totalImage){
        for(int i=1; i<=totalImage; i++){
        try{

            File file = new File("ClientImage/"+Integer.toString(i)+".jpg");

            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
            }

        }catch(Exception e){

            e.printStackTrace();

        }
        }
    }



    public void gotoProductAction(ActionEvent Event){
        deleteImage(totalImage);

        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("Product.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("Cannot Open Login.fxml");
        }

    }

    @FXML
    private ImageView messageImage;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try{
            pr1= Main.pr;
            br1= Main.br;
            s1= Main.s;

            if(pr1==null) System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("productName="+ProductController.currentProduct);

        productLabel.setText(ProductController.currentProduct);
        costLabel.setText(ProductController.currentCost);
        uploaderLabel.setText(ProductController.currentUploader);
        tagLabel.setText(ProductController.currentTag);
        categoryLabel.setText(ProductController.currentCategory);

        pr1.println("#GiveSpecificImage#"+ProductController.currentPost);

        try {
            String str= br1.readLine();
            totalImage= Integer.parseInt(str);
            System.out.println("total Image from Server="+totalImage);

            for(int i=1; i<=totalImage; i++) {

                downloadImageToClient(s1, Integer.toString(i));

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        int imageNo=1;


        try {
            while (imageNo <= totalImage) {

                System.out.println("before image= new Image");
                Image image = new Image("file:ClientImage/"+Integer.toString(imageNo)+".jpg");
                System.out.println("after image= new Image");
                switch (imageNo) {
                    case 1:

                        imageView1.setImage(image);
                        break;
                    case 2:

                        imageView2.setImage(image);
                        break;
                    case 3:

                        imageView3.setImage(image);
                        break;
                    default:
                        System.out.println("imageNo="+imageNo);
                        System.out.println("Invalid image No");
                }
                imageNo++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void downloadImageToClient(Socket s, String imageNo) {
        try {
            InputStream is= s.getInputStream();
            BufferedReader br= new BufferedReader(new InputStreamReader(is));
            String recv= br.readLine();

            int fileSize= Integer.parseInt(recv);
            System.out.println("size (inside downloadImageToServer())="+fileSize);

            byte[] datum= new byte[10000];

            File fileImageDownload= new File("ClientImage/"+imageNo+".jpg");

            FileOutputStream fos= new FileOutputStream(fileImageDownload);
            BufferedOutputStream bos= new BufferedOutputStream(fos);


            int bytesRead = 0;
            int total = 0;            //how many bytes read

            while (total != fileSize)    //loop is continued until received byte=totalfilesize
            {

                bytesRead= is.read(datum);
                System.out.println("bytesRead="+bytesRead);
                total += bytesRead;
                bos.write(datum, 0, bytesRead);


            }
            bos.flush();
            bos.close();//this has been added now
            System.out.println("Downloaded Image To Client");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not transfer file. inside downloadImageToServer()");
        }

    }
    


    @FXML
    private Button sendMessageButton;

    public void sendMessageAction(Event Event){
        System.out.println("Inside sendMessageAction Event");
        Main.sendStatus=4;
        try{

            Parent homePage=FXMLLoader.load(getClass().getResource("SendMessage.fxml"));
            Scene homePageScene=new Scene(homePage);

            Stage homeStage=(Stage)((Node) Event.getSource()).getScene().getWindow();
            homeStage.setScene(homePageScene);
            homeStage.setTitle("Send Message");
            homeStage.show();
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("Cannot Open Login.fxml");
        }

    }
    @FXML private Label labelBuy;
    @FXML private  Button btnBuy;
    @FXML private void setBtnBuy(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setWidth(1000);
        alert.setTitle("Confirm Buy");
        alert.setHeaderText("Do you Confirm Buy?");
        alert.setContentText("Then proceed Further And Click Ok\nElse Click Cancel");
        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent()){
            System.out.println("Exited Alert");

        }
        // alert is exited, no button has been pressed.
        else if(result.get() == ButtonType.OK){
            System.out.println("\nSucessful Buy");
            Date date = new Date();
            SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat dateFormat=new SimpleDateFormat ("dd MMMM yyyy zzz");
            String strTime=timeFormat.format(date);
            String strDate= dateFormat.format(date);
            System.out.println("time="+strTime+" date="+strDate);

            downloadReceipt();
            pr1.println("#GetBoughtPostNo#"+ProductController.currentPost+"#"+Main.currentUser+"#"+strTime+"#"+strDate);
            try{

                Parent homePage=FXMLLoader.load(getClass().getResource("BuySucessful.fxml"));
                Scene homePageScene=new Scene(homePage);

                Stage homeStage=(Stage)((Node) event.getSource()).getScene().getWindow();
                homeStage.setScene(homePageScene);
                homeStage.setTitle("Buy Sucessful");
                homeStage.show();
            }catch (Exception e){
                e.printStackTrace();

            }

        }
        //ok button is pressed
        else if(result.get() == ButtonType.CANCEL){
            System.out.println("\n\n\nBuy Cancelled");
        }
        // cancel button is pressed

    }
    private void downloadReceipt(){
        String fn="Receipt.pdf";

        Document d=new Document();

        try {
            PdfWriter.getInstance(d,new FileOutputStream(fn));

            d.open();

            Paragraph p=new Paragraph("Buyer Id:\t"+Main.currentUser+"\n\n\n");
            p.setAlignment(Paragraph.ALIGN_LEFT);
            d.add(p);

            Paragraph p1=new Paragraph("Product Name:\t"+ProductController.currentProduct);
            p1.setAlignment(Paragraph.ALIGN_LEFT);
            d.add(p1);

            Paragraph p2= new Paragraph("Product Uploader:\t"+ProductController.currentUploader+
                                              "\nProduct Cost:\t"+ProductController.currentCost+
                                                "\nProduct Category:\t"+ProductController.currentCategory+"\n\n\n");
            p2.setAlignment(Paragraph.ALIGN_LEFT);
            d.add(p2);

            if(totalImage>=1) {

                com.itextpdf.text.Image image1 = com.itextpdf.text.Image.getInstance("ClientImage/1.jpg");

                image1.scaleToFit(200f, 150f);
                d.add(image1);
            }
            if(totalImage>=2) {

                com.itextpdf.text.Image image2 = com.itextpdf.text.Image.getInstance("ClientImage/2.jpg");
                image2.scaleToFit(200f, 150f);
                d.add(image2);
            }
            if(totalImage>=3) {

                com.itextpdf.text.Image image3 = com.itextpdf.text.Image.getInstance("ClientImage/3.jpg");
                image3.scaleToFit(200f, 150f);


                d.add(image3);
            }


            d.close();
            System.out.println("Pdf Created");
            labelBuy.setText("Buying Sucessful");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
