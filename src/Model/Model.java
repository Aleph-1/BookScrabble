package Model;

import java.io.IOException;
import java.util.Observable;

public class Model extends Observable {

    hostServer hs = new hostServer();

    public void startHost(int port){
        hs.startConnection(port);
    }

    public void closeConnection(){
        hs.closeConnection();
    }

    public void guestGame(String ip, int port) throws IOException {
        hs.guestMode(ip,port);
    }


}
