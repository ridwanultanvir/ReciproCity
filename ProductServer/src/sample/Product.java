package sample;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Product {



    public void getMyProductData(PrintWriter pr,ObjectOutputStream oos,String userId){


            System.out.println("Inside databaseCode() ");
            Connection connection;
            Statement statement;
            ResultSet resultSet;

            try {

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
                System.out.println("Product Share Database Opened...");
                statement = connection.createStatement();
                //String sql="SELECT * FROM product_name" ;
                String sql="SELECT * FROM `product_name` WHERE (`Uploader`='"+userId+"') AND (`Sold`='No')";
                System.out.println("sql="+sql);
                resultSet=statement.executeQuery(sql);

                resultSet.last();
                int relatedPostRowNo = resultSet.getRow();
                pr.println(Integer.toString(relatedPostRowNo));
                resultSet.beforeFirst();


                String productName=null;
                String uploaderName=null;
                String productCategory=null;
                String  productTag=null;
                String productCost=null;

                while (resultSet.next()) {
                    String currentPost= resultSet.getString("PostNo");


                    uploaderName = resultSet.getString("Uploader");
                    productName= resultSet.getString("ProductName");
                    productCategory=resultSet.getString("Category");
                    productTag=resultSet.getString("Tag");
                    productCost=resultSet.getString("Cost");

                    String productTime= resultSet.getString("Time");
                    String productDate= resultSet.getString("Date");
                    System.out.println("productName="+productName);

                    oos.writeObject(new PInfo(currentPost,productName, uploaderName,productCategory,productCost,productTag,productTime,productDate));

                }
                connection.close();

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Problem loading database");
            }
    }

    public void getProductData(PrintWriter pr, ObjectOutputStream oos,String userId) {

        System.out.println("Inside databaseCode() ");
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            //String sql="SELECT * FROM product_name" ;
//            String sql="SELECT * FROM `product_name` WHERE ((NOT `Uploader`='"+userId+"') AND (`Sold`='No'))";
            String sql="SELECT * FROM `product_name` WHERE (`Sold`='No')";
            System.out.println("sql="+sql);
            resultSet=statement.executeQuery(sql);


            String productName=null;
            String uploaderName=null;
            String productCategory=null;
            String  productTag=null;
            String productCost=null;

            while (resultSet.next()) {

                String currentPost= resultSet.getString("PostNo");


                uploaderName = resultSet.getString("Uploader");
                productName= resultSet.getString("ProductName");
                productCategory=resultSet.getString("Category");
                productTag=resultSet.getString("Tag");
                productCost=resultSet.getString("Cost");
                String productTime= resultSet.getString("Time");
                String productDate= resultSet.getString("Date");
                pr.println(currentPost+"#"+productName+"#"+uploaderName+"#"+productCategory+"#"+productCost+"#"+productTag);


                System.out.println("uploader="+uploaderName);
            }
            connection.close();
            pr.println("ProductDataFinished");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }

    }


    public void getMySoldData(PrintWriter pr,ObjectOutputStream oos,String userId){


        System.out.println("Inside databaseCode() ");
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            //String sql="SELECT * FROM product_name" ;
            String sql="SELECT * FROM `product_name` WHERE (`Uploader`='"+userId+"') AND (`Sold`='Yes')";
            //System.out.println("sql="+sql);
            resultSet=statement.executeQuery(sql);

            resultSet.last();
            int relatedPostRowNo = resultSet.getRow();
            pr.println(Integer.toString(relatedPostRowNo));
            resultSet.beforeFirst();


            String productName="";
            String buyerName="";
            String productCategory="";
            String  productTag="";
            String productCost="";

            while (resultSet.next()) {
                String currentPost= resultSet.getString("PostNo");


                buyerName = resultSet.getString("SoldWhom");
                productName= resultSet.getString("ProductName");
                productCategory=resultSet.getString("Category");
                productTag=resultSet.getString("Tag");
                productCost=resultSet.getString("Cost");

                String productTime= resultSet.getString("SoldTime");
                String productDate= resultSet.getString("SoldDate");
                System.out.println("buyerName="+buyerName);

                oos.writeObject(new PInfo(currentPost,productName, buyerName,productCategory,productCost,productTag,productTime,productDate));

            }
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }
    }



}