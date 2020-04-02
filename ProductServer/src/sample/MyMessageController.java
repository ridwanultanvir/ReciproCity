package sample;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyMessageController  {


    public void getMyMessageServer(String UserId, PrintWriter pr){

        String from=null;
        String message=null;
        String time=null;
        String date =null;

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            String sql="SELECT * FROM `message_data` WHERE `MessageTo`='"+UserId+"'" ;
            //String sql="SELECT * FROM product WHERE ip_address= '"+address+"'";
            System.out.println("sql="+sql);
            resultSet=statement.executeQuery(sql);


            while (resultSet.next()) {

                from = resultSet.getString("MessageFrom");
                message= resultSet.getString("Message");
                time= resultSet.getString("Time");
                date= resultSet.getString("Date");

                System.out.println("Uploader name from db(tan)=" + from+"\t"+message+"\t"+time+"\t"+date);
                pr.println(from+"#"+message+"#"+time+"#"+date);



            }
            connection.close();
            pr.println("AllReceivedSentFromServer");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }

    }
    public void getMySentMessage(String userId, PrintWriter pr){

        String to=null;
        String message=null;
        String time=null;
        String date =null;

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            String sql="SELECT * FROM `message_data` WHERE `MessageFrom`='"+userId+"'" ;
            //String sql="SELECT * FROM product WHERE ip_address= '"+address+"'";
            System.out.println("sql="+sql);
            resultSet=statement.executeQuery(sql);


            while (resultSet.next()) {

                to = resultSet.getString("MessageTo");
                message= resultSet.getString("Message");
                time= resultSet.getString("Time");
                date= resultSet.getString("Date");

                System.out.println("Uploader name from db(tan)=" + to+"\t"+message+"\t"+time+"\t"+date);
                pr.println(to+"#"+message+"#"+time+"#"+date);



            }
            connection.close();
            pr.println("YourSentMessageFinished");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }



    }



}
