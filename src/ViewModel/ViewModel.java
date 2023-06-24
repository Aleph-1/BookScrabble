package ViewModel;

import Model.MileStone3.test.Board;
import Model.MileStone3.test.Tile;
import Model.MileStone3.test.Word;
import View.ViewRun;
import Model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicReference;


public class ViewModel implements Observer {
    Model m;
    View.MainWindowController v;
    public String request; //Bounded to view
    public IntegerProperty score; // Bound to View
    public StringProperty response; // Bounded to Model
    public StringProperty statusMessage; //Should be bounded to View as well
    public Board board = Board.getBoard();

    public void setView(View.MainWindowController v) {
        this.v = v;
        this.v.score.bind(score);
        this.v.statusMessage.bind(statusMessage);

    }

    public ViewModel(Model m) {
        this.m = m;
        score = new SimpleIntegerProperty();
        response = new SimpleStringProperty();
        statusMessage = new SimpleStringProperty();

    }


    @Override
    public void update(Observable o, Object arg) {

        if (o == m) {

            if (response.toString().compareTo("-1") == 0)
                statusMessage.set("Invalid word try again!");

            else {

                String[] data = response.toString().replace("[", "").replace("]", "").split(",");
                Word w = new Word(get(data[3]), Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2].compareTo("true") == 0);
                board.tryPlaceWord(w);
            }

        }

        if(o == v){
            try {
                m.guestGame(request,arg.toString().split(" ")[0],Integer.parseInt(arg.toString().split(" ")[1])); //IP,PORT
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


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
}