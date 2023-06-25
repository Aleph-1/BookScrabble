package Model;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;
import ViewModel.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model extends Observable {

    ViewModel vm;
    hostServer hs = new hostServer();

    public  void setViewModel(ViewModel vm){
        this.vm = vm;
        this.addObserver(vm);
    }

    public void startHost(int port) {
        hs.startConnection(port);
    }

    public void closeConnection() {
        hs.closeConnection();
    }


    public void guestGame(String request, String ip, int port) throws IOException {


        IntegerProperty score = new SimpleIntegerProperty();
        Socket server = new Socket(ip, port);
        StringProperty returnString = new SimpleStringProperty();
        PrintWriter outToServer = new PrintWriter(server.getOutputStream());
        Scanner in = new Scanner(server.getInputStream());
        outToServer.println(request);
        outToServer.flush();
        String response = in.next();

        vm.response.bind(returnString);

        if (response.compareTo("-1") == 0) { //The first index means the type of event
            returnString.set("-1");
            setChanged();
            notifyObservers();
        }

        if (response.compareTo("1") == 0) { // If your word got accepted, 2 will happen to you as well
            vm.score.bind(score);
            score.add(Integer.parseInt(in.next()));
            in.next(); //Update Board Protocol
            returnString.set(in.nextLine());
            setChanged();
            notifyObservers();
        }


    }


}
