package Model;

import Model.MileStone3.test.MyServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class threadedServer extends MyServer {
    private final int MAX_PLAYERS = 4;
    ExecutorService threadPool = Executors.newFixedThreadPool(MAX_PLAYERS);
    public threadedServer(int port, multClientHandler ch) {
        super(port, ch);
        this.port = port;
    }


    public void start(){
        stop=false;
        new Thread(()->startLogic()).start();
    }

    private void startLogic(){ //The overloaded function

        try {
            ServerSocket server = new ServerSocket(port);
            //server.setSoTimeout(1000);

            while (!stop) {
                    Socket aClient = server.accept();
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream()); // Might cause problems since the initialization of the streams might be incorrect by the time the thread turn will come.
                    threadPool.execute((multClientHandler)ch);  // This is a server that assumes you will be using a multithreaded client handler (Which is an instance of Runnable)
            }

            ch.close();
            server.close();

        }catch (IOException e){};

    }

}
