package test;

import Model.MileStone3.test.MainTrain;
import Model.MileStone3.test.MyServer;
import Model.hostServer;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class hostServerTest {



    public static void client1(int port) throws Exception{
        Socket server=new Socket("localhost", port);
        String text = "GORILLA";
        PrintWriter outToServer=new PrintWriter(server.getOutputStream());
        Scanner in=new Scanner(server.getInputStream());
        outToServer.println(text);
        outToServer.flush();
        String response=in.next();
        if(response==null)
            System.out.println("problem getting the right response from your server, cannot continue the test (-100)");
        in.close();
        outToServer.println(text);
        outToServer.close();
        server.close();
    }


    public static boolean testServer() {
        boolean ok=true;
        Random r=new Random();
        int port=6000+r.nextInt(1000);
        MyServer s=new MyServer(port, new hostServer.mult );
        int c = Thread.activeCount();
        s.start(); // runs in the background
        try {
            client1(port);
        }catch(Exception e) {
            System.out.println("some exception was thrown while testing your server, cannot continue the test (-100)");
            ok=false;
        }
        s.close();

        try {Thread.sleep(2000);} catch (InterruptedException e) {}

        if (Thread.activeCount()!=c) {
            System.out.println("you have a thread open after calling close method (-100)");
            ok=false;
        }
        return ok;
    }


    public static void main(String[] args){

    }



}
