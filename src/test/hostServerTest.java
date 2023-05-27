package test;

import Model.MileStone3.test.MainTrain;
import Model.MileStone3.test.MyServer;
import Model.hostServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class hostServerTest {

    public static String text(int id,int x, int y, char v_or_h,char q_or_c,String word){
        return id+"\n["+x+","+y+","+v_or_h+"]"+"\n"+q_or_c+",bee.txt,"+word;
    }

    public static void client1(int port) throws Exception{
        Socket server=new Socket("localhost", port);



        //Frank Herbert - Dune.txt,shakespeare.txt
        String text = text(0,7,5,'H','C',"HORN");
        PrintWriter outToServer=new PrintWriter(server.getOutputStream());
        Scanner in=new Scanner(server.getInputStream());
        outToServer.println(text);
        outToServer.flush();
        String response=in.next();
        if(response==null)
            System.out.println("problem getting the right response from your server, cannot continue the test (-100)");

        if(response.compareTo("1") == 0)
            System.out.println(response + in.nextLine() + " " + in.nextLine());
        else
            System.out.println(response + in.nextLine());

        in.close();
        outToServer.close();
        server.close();
    }
    public static void client2(int port) throws Exception{
        Socket server=new Socket("localhost", port);

        //Frank Herbert - Dune.txt,shakespeare.txt
        String text = text(1,5,7,'V','C',"FA_M");
        PrintWriter outToServer=new PrintWriter(server.getOutputStream());
        Scanner in=new Scanner(server.getInputStream());
        outToServer.println(text);
        outToServer.flush();
        String response=in.next();
        if(response==null)
            System.out.println("problem getting the right response from your server, cannot continue the test (-100)");
        if(response.compareTo("1") == 0)
            System.out.println(response + in.nextLine() + " " + in.nextLine());
        else
            System.out.println(response + in.nextLine());
        in.close();
        outToServer.close();
        server.close();
    }

    public static void client3(int port) throws Exception{
        Socket server=new Socket("localhost", port);

        //Frank Herbert - Dune.txt,shakespeare.txt
        String text = text(2,9,5,'H','Q',"PASTE");
        PrintWriter outToServer=new PrintWriter(server.getOutputStream());
        Scanner in=new Scanner(server.getInputStream());
        outToServer.println(text);
        outToServer.flush();
        String response=in.next();
        if(response==null)
            System.out.println("problem getting the right response from your server, cannot continue the test (-100)");
        if(response.compareTo("1") == 0)
            System.out.println(response + in.nextLine() + " " + in.nextLine());
        else
            System.out.println(response + in.nextLine());
        in.close();
        outToServer.close();
        server.close();
    }
    public static void client4(int port) throws Exception{
        Socket server=new Socket("localhost", port);

        //Frank Herbert - Dune.txt,shakespeare.txt
        String text = text(3,8,7,'H','C',"_OB");
        PrintWriter outToServer=new PrintWriter(server.getOutputStream());
        Scanner in=new Scanner(server.getInputStream());
        outToServer.println(text);
        outToServer.flush();
        String response=in.next();
        if(response==null)
            System.out.println("problem getting the right response from your server, cannot continue the test (-100)");
        if(response.compareTo("1") == 0)
            System.out.println(response + in.nextLine() + " " + in.nextLine());
        else
            System.out.println(response + in.nextLine());
        in.close();
        outToServer.close();
        server.close();
    }



    public static void client5(int port) throws Exception{
        Socket server=new Socket("localhost", port);
        int numPlayer=0;
        int x= 7;
        int y= 1;
        char v_or_h='H';
        char q_or_c='C';


        //Frank Herbert - Dune.txt,shakespeare.txt
        String text =0+"\n["+x+","+y+","+v_or_h+"]"+"\n"+q_or_c+",bee.txt,"+"HIJACKING";
        PrintWriter outToServer=new PrintWriter(server.getOutputStream());
        Scanner in=new Scanner(server.getInputStream());
        outToServer.println(text);
        outToServer.flush();
        String response=in.next();
        if(response==null)
            System.out.println("problem getting the right response from your server, cannot continue the test (-100)");
        if(response.compareTo("1") == 0)
            System.out.println(response + in.nextLine() + " " + in.nextLine());
        else
            System.out.println(response + in.nextLine());
        in.close();
        outToServer.close();
        server.close();
    }

    public static void client6(int port) throws Exception{
        Socket server=new Socket("localhost", port);
        int numPlayer=0;
        int x= 5;
        int y= 9;
        char v_or_h='V';
        char q_or_c='Q';


        //Frank Herbert - Dune.txt,shakespeare.txt
        String text =0+"\n["+x+","+y+","+v_or_h+"]"+"\n"+q_or_c+",bee.txt,"+"DO_";
        PrintWriter outToServer=new PrintWriter(server.getOutputStream());
        Scanner in=new Scanner(server.getInputStream());
        outToServer.println(text);
        outToServer.flush();
        String response=in.next();
        if(response==null)
            System.out.println("problem getting the right response from your server, cannot continue the test (-100)");
        if(response.compareTo("1") == 0)
            System.out.println(response + in.nextLine() + " " + in.nextLine());
        else
            System.out.println(response);
        in.close();
        outToServer.close();
        server.close();
    }








    public static boolean testServer() {
        boolean ok=true;
        Random r=new Random();
        int port=6000+r.nextInt(1000);
        hostServer hs = new hostServer();

        hs.startConnection(port); // runs in the background
        try {


            //client6(port);

            client1(port);
            client2(port);
            client3(port);
            client4(port);
            client5(port);

        }catch(Exception e) {
            System.out.println("some exception was thrown while testing your server, cannot continue the test (-100)");
            ok=false;
        }

        hs.mch.close();
        hs.closeConnection();

        return ok;
    }


    public static void main(String[] args){

        if(testServer()){
            System.out.println("done");
        }



    }



}
