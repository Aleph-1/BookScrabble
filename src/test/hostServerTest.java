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




    public static void client1(int port) throws Exception{
        Socket server=new Socket("localhost", port);
        int numPlayer=0;
        int x= 6;
        int y= 2;
        char v_or_h='V';
        char q_or_c='Q';


        String text =0+"\n["+x+","+y+","+v_or_h+"]"+"\n"+q_or_c+",mobydick.txt,Frank Herbert - Dune.txt,shakespeare.txt,"+"WHALE";
        PrintWriter outToServer=new PrintWriter(server.getOutputStream());
        Scanner in=new Scanner(server.getInputStream());
        outToServer.println(text);
        outToServer.flush();
        String response=in.next();
        if(response==null)
            System.out.println("problem getting the right response from your server, cannot continue the test (-100)");
        if(response.charAt(0)!='1'||in.next().charAt(0)!='2'){
            System.out.println("Wrong, (-100)");
        }
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
            client1(port);
        }catch(Exception e) {
            System.out.println("some exception was thrown while testing your server, cannot continue the test (-100)");
            ok=false;
        }

        hs.mch.close();

        return ok;
    }


    public static void main(String[] args){

        if(testServer()){
            System.out.println("Y0");
        }



    }



}
