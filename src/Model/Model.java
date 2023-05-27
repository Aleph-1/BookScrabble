package Model;

import java.io.IOException;

public class Model {

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
