package sample;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Profile {

    public void getProfile(String UserId, PrintWriter pr)  {

        Connection connection;
        Statement statement;
        ResultSet resultSet;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/product_database", "root", "");
            System.out.println("Product Share Database Opened...");
            statement = connection.createStatement();
            String sql="SELECT * FROM `account_data` WHERE `StudentId`='"+UserId+"'" ;
            //SELECT * FROM `account_data` WHERE `StudentId`='1705016'
            //String sql="SELECT * FROM product WHERE ip_address= '"+address+"'";
            System.out.println("sql="+sql);
            resultSet=statement.executeQuery(sql);


            if(resultSet.next()) {

                 String email= resultSet.getString("Email");
                 String name=resultSet.getString("Name");
                 String hall= resultSet.getString("Hall");
                 String room=resultSet.getString("RoomNo");
                pr.println("#ReceiveProfileData#"+name+"#"+email+"#"+hall+"#"+room);
            }else{
                System.out.println("User"+UserId+" not found");
            }
            connection.close();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Problem loading database");
        }
    }
}
