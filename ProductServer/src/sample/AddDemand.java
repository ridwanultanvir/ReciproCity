package sample;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class AddDemand {

    public void getDemandData(PrintWriter pr,String userId)  {

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            String sql="SELECT * FROM `demand_data` ORDER BY `PostNo` DESC" ;
            //SELECT * FROM `demand_data` WHERE NOT `MessageFrom`='1705004'

            System.out.println("sql="+sql);
            resultSet=statement.executeQuery(sql);
            String from=null;
            String message=null;
            String time=null;
            String date=null;

            while (resultSet.next()) {


                from= resultSet.getString("MessageFrom");
                message= resultSet.getString("Message");
                time=resultSet.getString("Time");
                date=resultSet.getString("Date");
                System.out.println("from="+from+" message="+message);
                pr.println(from+"#"+message+"#"+time+"#"+date);
            }

            connection.close();
            pr.println("AllDemandDataSentFromServer");

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }


    }



    public void addDemandToDatabase(String from,String message,String category, String time, String date) throws IOException {

        System.out.println("inside addProductInfo()");
        File file = new File("totalDemand.txt");
        Scanner scn = new Scanner(file);
//
        int postNum=scn.nextInt()+1;

        String strPostNum=Integer.toString(postNum);
        BufferedWriter writer1 = new BufferedWriter(new FileWriter("totalDemand.txt", false));
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
            String sql="INSERT INTO `demand_data`(`No`,`MessageFrom`, `Sub`,`Message`, `Time`, `Date`) VALUES ('"+postNum+"','"+from+"','"+category+"','"+message+"','"+time+"', '"+date+"') ";

            System.out.println("sql="+sql);
            statement.executeUpdate(sql);


            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }

    }
}
