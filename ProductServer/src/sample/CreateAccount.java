package sample;

import java.io.*;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class CreateAccount{



    public boolean checkExistingProfile(String userId){
        boolean exists= false;

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            //SELECT * FROM `account_data` WHERE `StudentId`='1705016'
            String sql="SELECT * FROM account_data WHERE `StudentId`='"+userId+"'" ;
            //String sql="SELECT * FROM product WHERE ip_address= '"+address+"'";
            System.out.println("sql="+sql);
            resultSet=statement.executeQuery(sql);

            if( resultSet.next() ) {
                String str = resultSet.getString("StudentId");
                System.out.println("\n\nExisiting ID="+str);
                // ResultSet processing here
                exists = true;
            }


            connection.close();



        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading product_database");
        }

        return exists;
    }


    public void savingProfile(String userId, String password,  String email, String name, String hall,String room, PrintWriter pr) {

        System.out.println("inside saving Profile");
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            //INSERT INTO `product_name`(`PostNo`, `ProductName`, `Uploader`, `Cost`, `Category`, `Tag`) VALUES ([value-1],[value-2],[value-3],[value-4],[value-5],[value-6])
            String sql="INSERT INTO `account_data`(`StudentId`,`Password`, `Email`, `Name`, `Hall`, `RoomNo`) VALUES ('"+userId+"','"+password+"','"+email+"','"+name+"', '"+hall+"','"+room+"') ";
            //String sql="SELECT * FROM product WHERE ip_address= '"+address+"'";
            System.out.println("sql="+sql);
            statement.executeUpdate(sql);
            pr.println("#CreateAccountSucessful#");

            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading product_database");
        }
    }



}
