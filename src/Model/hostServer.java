package Model;

import Model.MileStone3.test.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;



public class hostServer{

    int score = 0;
    threadedServer server;
    Board b;
    public multClientHandler mch = new multClientHandler();

    public void startConnection(int port){
        server = new threadedServer(port,mch);
        server.start();
    }

    private static Tile[] get(String s) {
        Tile[] ts = new Tile[s.length()];
        int i = 0;
        for (char c : s.toCharArray()) {
            ts[i] = Tile.Bag.getBag().getTile(c);
            i++;
        }
        return ts;
    }

    public void guestMode(String ip, int port) throws IOException { //Since the function is based on the View-Model currently we're not testing although the tests in main are similar.

        Socket server=new Socket(ip, port);
        String text = ""; // Will be updated based on View&View-Model
        String diffText="";
        PrintWriter outToServer=new PrintWriter(server.getOutputStream());
        Scanner in=new Scanner(server.getInputStream());
        outToServer.println(text);
        outToServer.flush();
        String response=in.next();

        while(response.compareTo("-1") == 0){
            outToServer.println(diffText); //Will be taken from view
        }

        if(response.compareTo("1") == 0){ // If your word got accepted, 2 will happen to you as well

            score += Integer.parseInt(in.next());

        }

        if(in.next().compareTo("2") == 0){ //Update Board

            String details = in.nextLine();
            String[] data = details.replace("[","").replace("]","").split(",");
            Word w = new Word(get(data[3]),Integer.parseInt(data[0]),Integer.parseInt(data[1]),data[2].compareTo("true") == 0);
            b.tryPlaceWord(w);
        }


    }

    public void closeConnection(){
        mch.close();
        server.close();
    }


}
