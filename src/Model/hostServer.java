package Model;

import Model.MileStone3.test.*;
import java.io.*;
import java.util.ArrayList;


public class hostServer{

    MyServer server;
    DictionaryManager dm;
    public multClientHandler mch = new multClientHandler();

    public void startConnection(int port){
        server = new MyServer(port,mch);
        server.start();
    }

    public void closeConnection(){
        server.close();
    }


}
