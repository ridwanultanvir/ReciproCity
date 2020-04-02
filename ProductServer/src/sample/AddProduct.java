package sample;


import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AddProduct {
    static public String strPostNum;
    public void getTotalPostNum() throws FileNotFoundException {
        File file = new File("totalPost.txt");
        Scanner scn = new Scanner(file);
//
        int postNum=scn.nextInt()+1;

        strPostNum=Integer.toString(postNum);
    }
    public void setTotalImageNo(String totalImage) {
        /*
        Here Be careful no need to add ++

         */
        try {
            BufferedWriter writer7 = new BufferedWriter(new FileWriter("allImageNo.txt", true));

            System.out.println("\n\n\nstrPostNum=" + strPostNum + "\t" + "totalImage=" + totalImage);
            writer7.write(strPostNum + " " + totalImage);
            writer7.newLine();
            writer7.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateRemoveInfo(String postNo) {

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();


            String sql="DELETE FROM `product_name`  WHERE `PostNo`='"+postNo+"' ";
            //DELETE FROM `demand_data` WHERE `No`='6'

            statement.executeUpdate(sql);


            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }

    }

    public void addProductInfo(String name,String cost,String tag,String category, String uploader,String totalImage,String Time,String Date) throws IOException {
        System.out.println("inside addProductInfo()");
        File file = new File("totalPost.txt");
        Scanner scn = new Scanner(file);
//
        int postNum=scn.nextInt()+1;

        strPostNum=Integer.toString(postNum);
        BufferedWriter writer1 = new BufferedWriter(new FileWriter("totalPost.txt", false));
        writer1.write(strPostNum);
        writer1.close();

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            //INSERT INTO `product_name`(`PostNo`, `ProductName`, `Uploader`, `Cost`, `Category`, `Tag`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5],[value-6])
            String sql="INSERT INTO `product_name`(`PostNo`,`ProductName`, `Uploader`, `Cost`, `Category`, `Tag`,`ImageNo`,`Sold`,`Time`,`Date`) VALUES ('"+postNum+"','"+name+"','"+uploader+"','"+cost+"', '"+category+"','"+tag+"','"+totalImage+"','No','"+Time+"','"+Date+"') ";
            //String sql="SELECT * FROM product WHERE ip_address= '"+address+"'";
            System.out.println("sql="+sql);
            statement.executeUpdate(sql);


            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }




    }

    public void updateSoldInfo(String postNo,String Buyer,String Time,String Date) throws IOException {

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();


           String sql="UPDATE `product_name` SET `Sold`='Yes',`SoldWhom`='"+Buyer+"',`SoldTime`='"+Time+"',`SoldDate`='"+Date+"' WHERE `PostNo`='"+postNo+"' ";

            statement.executeUpdate(sql);


            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }

    }

    public void downloadImageToServer(Socket s,String imageNo) throws FileNotFoundException {

        getTotalPostNum();
        System.out.println("strpostNum="+strPostNum);


        try {
            BufferedReader br= new BufferedReader(new InputStreamReader(s.getInputStream()));
            String strRecv = br.readLine();                    //These two lines are used to determine
            int filesize = Integer.parseInt(strRecv);        //the size of the receiving file
            System.out.println("size (inside downloadImageToServer())="+filesize);
            byte[] contents = new byte[10000];

            File fileImageDownload = new File("ProductImage/"+strPostNum+"-"+imageNo+".jpg");

            FileOutputStream fos = new FileOutputStream(fileImageDownload);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            InputStream is = s.getInputStream();

            //System.out.println(is);

            int bytesRead = 0;
            int total = 0;            //how many bytes read

            while (total != filesize)    //loop is continued until received byte=totalfilesize
            {
                //System.out.println("bytesread="+bytesRead);
                bytesRead= is.read(contents);
                System.out.println("bytesRead="+bytesRead);
                total += bytesRead;


                bos.write(contents, 0, bytesRead);

            }
            bos.flush();
            bos.close();//this has been added now
            System.out.println("Downloaded Image To server");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not transfer file. inside downloadImageToServer()");
        }

    }




}
