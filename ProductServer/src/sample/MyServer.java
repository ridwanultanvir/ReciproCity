package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class MyServer{



    public static int threadCount = 0;

    public static void main(String[] args) {
        Socket clientSocket;


        int id= 1;


        try{
            //InetAddress ia= InetAddress.getLocalHost();
            System.out.println("ip adrress is " + InetAddress.getLocalHost().getHostAddress());
            ServerSocket serverSocket= new ServerSocket(5678);
            System.out.println("Server successfully Initialized");
            //SeparateClientThread clientThread= new SeparateClientThread(); wrong
            while(true){
                clientSocket=serverSocket.accept(); ///TCP

                SeparateClientThread clientThread= new SeparateClientThread(clientSocket,id);
                //clientThread.start(); wrong

                Thread t= new Thread(clientThread);

                t.start();
                threadCount++;
                System.out.println("Client [" + id + "] is now connected. No. of worker threads = " + threadCount);
                id++;



            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail for serversocket");
        }

    }

}

class SeparateClientThread implements  Runnable{


    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;

    private  BufferedReader bufferedReader;
    private PrintWriter printWriter;



    private int clientId;


    SeparateClientThread(Socket s,int id) throws IOException {
        this.socket= s;

        //this.inputStream= s.getInputStream(); //Unhandled java.io exception

        this.inputStream= s.getInputStream();
        this.outputStream= s.getOutputStream();

        this.clientId= id;


    }




    @Override
    public void run() {
        System.out.println("Inside Run of SeparateClient Thread");

        try {


            bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));

            printWriter = new PrintWriter(this.outputStream, true);
            objectOutputStream = new ObjectOutputStream(outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        //printWriter.println("Hello This is Client Id:"+clientId);
        while (true){

            try{

                String clientStr= bufferedReader.readLine();
                System.out.println("clinetStr="+clientStr);


                if(clientStr!= null) {

                    System.out.println("This is Inside MyServer Thread");
                    //System.out.println("id:" + clientId + "\tsays:" + clientStr);
                    System.out.println("clientStr="+clientStr);

                    String[] strings = clientStr.split("#");

                    //System.out.println("strings[1]="+strings[1]);

                    if(strings.length>1) {


                        if (strings[1].equals("login")) {
                            //System.out.println("clientStr="+clientStr+" length="+strings.length);

                            System.out.println("Client trying to Login");

                            if (checkUser.validStudent(strings[2], strings[3])) {
                                System.out.println("Correct User Login");
                                printWriter.println("loginOk");
                            } else {
                                System.out.println("Failed Login User");
                                printWriter.println("login Failed");
                            }



                        } else if (strings[1].equals("getMessageData")) {
                            System.out.println("getMessageData String found");
                            MyMessageController m = new MyMessageController();
                            m.getMyMessageServer(strings[2],printWriter);


                        }else if(strings[1].equals("GetAllDemandData")){
                            AddDemand addDemand=new AddDemand();
                            if(strings.length>=3)addDemand.getDemandData(printWriter,strings[2]);


                        }else if(strings[1].equals("GiveMyProductData")){
                            Product product= new Product();
                            product.getMyProductData(printWriter,objectOutputStream,strings[2]);


                        }else if(strings[1].equals("GiveMySoldItem")){
                            Product product= new Product();
                            product.getMySoldData(printWriter,objectOutputStream,strings[2]);

                        }else if (strings[1].equals("getSentMessageData")) {

                            MyMessageController m = new MyMessageController();
                            m.getMySentMessage(strings[2],printWriter);



                        }else if (strings[1].equals("GetImage")){
                            System.out.println("server got-GetImage");
                            AddProduct addProduct = new AddProduct();
                            addProduct.downloadImageToServer(socket,strings[2]);

                        }else  if(strings[1].equals("CreateAccount")){
                            System.out.println("Inside Server create Account");
                            CreateAccount createAccount= new CreateAccount();
                            if(strings.length>=7){
                                if(!createAccount.checkExistingProfile(strings[2])) {
                                    System.out.println("no account exists so saving profile");
                                    createAccount.savingProfile(strings[2], strings[3], strings[4], strings[5], strings[6], strings[7], printWriter);
                                }else{
                                    printWriter.println("#AlreadyAccount#");
                                }
                            }

                        }else if(strings[1].equals("GiveProductData")){
                            System.out.println("Client Asking for Product Data");
                            Product p= new Product();
                            p.getProductData(printWriter,objectOutputStream,strings[2]);

                        }else if(strings[1].equals("AddProduct")){
                            System.out.println("Adding Product Inside Server");
                            AddProduct addProduct= new AddProduct();
                            if(strings.length>=10){
                                addProduct.addProductInfo(strings[2],strings[3],strings[4],strings[5],strings[6],strings[7],strings[8],strings[9]);
                            }

                        }else if(strings[1].equals("logout")){
                            System.out.println("client"+clientId+" loggedOut");
                            break;
                        }else if(strings[1].equals("GiveSpecificImage")){
                            SpecificProduct specificProduct= new SpecificProduct();
                            int totalImage= specificProduct.getTotalImageNo(strings[2]); //strings[2]= postNo
                            printWriter.println(totalImage);
                            for(int i=1; i<=totalImage; i++) {
                                specificProduct.sendSpecificImage(socket, printWriter, "ProductImage/" + strings[2]+"-"+Integer.toString(i) + ".jpg");
                            }

                        }else if(strings[1].equals("GetProfileData")){

                            Profile profile= new Profile();
                            profile.getProfile(strings[2],printWriter);


                        }else if(strings[1].equals("GetImageNo")){
                            AddProduct addProduct= new AddProduct();
                            addProduct.setTotalImageNo(strings[2]);

                        }else if(strings[1].equals("InsertMessage")){
                            SendMessage sendMessage= new SendMessage();
                            System.out.println("\n\nMessage:"+clientStr);
                            if(strings.length>=8){
                                sendMessage.sendMessageToDatabase(strings[2],strings[3],strings[4],strings[5],strings[6],strings[7]);
                            }
                        }else if(strings[1].equals("AddDemand")){

                            AddDemand addDemand = new AddDemand();
                            if(strings.length>=7) addDemand.addDemandToDatabase(strings[2],strings[3],strings[4],strings[5],strings[6]);
                        }else if(strings[1].equals("GetBoughtPostNo")){
                            AddProduct addProduct=new AddProduct();
                            if(strings.length>=6)addProduct.updateSoldInfo(strings[2],strings[3],strings[4],strings[5]);

                        }else if(strings[1].equals("RemoveItem")) {
                            AddProduct addProduct = new AddProduct();
                            addProduct.updateRemoveInfo(strings[2]);
                        }

                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Failed read client string ");

                break;///closing the thread

            }

        }

    }



}


