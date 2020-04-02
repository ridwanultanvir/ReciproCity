package sample;


import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class checkUser {



    public static boolean validStudent(String userId, String password){
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        boolean validate=false;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            String sql="SELECT `Password` FROM `account_data` WHERE `StudentId`='"+userId+"'" ;
            //String sql="SELECT * FROM product WHERE ip_address= '"+address+"'";
            System.out.println("sql="+sql);
            resultSet=statement.executeQuery(sql);


            if(resultSet.next()) {

                String passwordFromServer = resultSet.getString("Password");
                System.out.println("passwordFromServer="+passwordFromServer);
//
                if(passwordFromServer.equals(password)){
                    validate=true;
                }
            }
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }
        return validate;
    }

}
