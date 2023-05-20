package Model;

import Model.MileStone3.test.DictionaryManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandle {
    private void joinServer(String ip,int port) throws IOException {
        ServerSocket server=new ServerSocket(port);
        while(true){
            try{
                Socket aClient =server.accept();
                try{
                    DictionaryManager dm = null;

                    BufferedReader in = new BufferedReader(new InputStreamReader(aClient.getInputStream()));
                    PrintWriter out = new PrintWriter(aClient.getOutputStream(), true);
                    String line = in.readLine();

                    String[] words = line.split(",");
                    if()
                }
            }
        }
    }
}
