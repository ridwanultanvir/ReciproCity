package sample;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;



public class SendMessage {

    public void sendMessageToDatabase(String from, String to,String message, String subject,String time,String date) throws IOException {
        File file = new File("totalMessage.txt");
        Scanner scn = new Scanner(file);
//
        int totalMes=scn.nextInt()+1;

        String totalMessage= Integer.toString(totalMes);
        BufferedWriter writer1 = new BufferedWriter(new FileWriter("totalMessage.txt", false));
        writer1.write(totalMessage);
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
            String sql="INSERT INTO `message_data`(`MessageNo`,`MessageFrom`,`MessageTo`, `Subject`,`Message`, `Time`, `Date`) VALUES ('"+totalMessage+"','"+from+"','"+to+"','"+subject+"','"+message+"','"+time+"', '"+date+"') ";
            statement.executeUpdate(sql);
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }finally {

        }
    }
}
