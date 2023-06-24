package Model;

import Model.MileStone3.test.MyServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
                try {
                    Socket aClient = server.accept();
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream()); // Might cause problems since the initialization of the streams might be incorrect by the time the thread turn will come.
                    multClientHandler m=(multClientHandler) ch;
                    threadPool.execute(m);  // This is a server that assumes you will be using a multithreaded client handler (Which is an instance of Runnable)
                    int i=0;
                    while(m.in==null){
                        i++;
                    }
                    if(m.in!=null){
                        ch.close();
                    }
                    aClient.close();
                }catch (SocketTimeoutException e) {}

            }
            server.close();

        }catch (IOException e){};

    }

}
