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

    public static Tile[] get(String s) {
        Tile[] ts = new Tile[s.length()];
        int i = 0;
        for (char c : s.toCharArray()) {
            ts[i] = Tile.Bag.getBag().getTile(c);
            i++;
        }
        return ts;
    }

    public void closeConnection(){
        mch.close();
        server.close();
    }


}