package sample;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SpecificProduct {
    public int getTotalImageNo(String postNo) {
        int totalImage=0;
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            String sql="SELECT * FROM `product_name` WHERE `PostNo`='"+postNo+"'" ;
            //String sql="SELECT * FROM product WHERE ip_address= '"+address+"'";
            System.out.println("sql="+sql);
            resultSet=statement.executeQuery(sql);

            String imageNo=null;
            if (resultSet.next()) {

                imageNo = resultSet.getString("ImageNo");

           }
            connection.close();
            totalImage=Integer.parseInt(imageNo);
            System.out.println("total Image="+totalImage);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }

        return totalImage;
    }


    public void sendSpecificImage(Socket s, PrintWriter pr, String path){
        try
        {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = s.getOutputStream();
            byte[] contents;
            long fileLength = file.length();
            pr.println(String.valueOf(fileLength));		//These two lines are used
            pr.flush();									//to send the file size in bytes.
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

        }
        catch(Exception e)
        {
            System.out.println("Could not transfer Path="+path);
        }
    }
}
