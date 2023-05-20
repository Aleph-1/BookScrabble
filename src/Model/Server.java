package Model;

import Model.MileStone3.test.BloomFilter;
import Model.MileStone3.test.ClientHandler;
import Model.MileStone3.test.DictionaryManager;
import Model.MileStone3.test.MyServer;

public class Server {
    DictionaryManager dm=new DictionaryManager();
    public void startConnection(int port, ClientHandler ch){
        MyServer server=new MyServer(port,ch);
        server.start();
    }
    public boolean query(String... args){
        return dm.query(args);
    }
    public boolean challenge(String... args){
        return dm.challenge(args);
    }
}
