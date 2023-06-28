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


    public void setView(View.MainWindowController v){//Connecting the view and the view model
        this.v = v;
        this.v.score.bind(score);
        this.v.statusMessage.bind(statusMessage);
    }

    public ViewModel(Model m) {//Constructor
        this.m = m;
        score = new SimpleIntegerProperty();
        response = new SimpleStringProperty();
        statusMessage = new SimpleStringProperty();

    }


    @Override
    public void update(Observable o, Object arg) {//Works on notify

        if (o == m) {


            if (response.getValue().compareTo("-1") == 0)//Means the word isn't correct
                statusMessage.set("Invalid word try again!");
            else {
                if (response.getValue().compareTo("3")==0)//Means it's not the players turn
                    statusMessage.set("Not Your Turn!");
                else {
                    statusMessage.set("Word Approved!");//Word is approved by the server
                    String[] data = response.getValue().replace("[", "").replace("]", "").split(",");//Collecting the data
                    Word w = new Word(get(data[3]), Integer.parseInt(data[0].trim()), Integer.parseInt(data[1]), data[2].compareTo("true") == 0);//Creating the wanted word
                    v.score.bind(score);//Binding view score to view model score
                    board.tryPlaceWord(w);//Placing the word on the board
                    v.updateBoard(board);//Updating the board
                }
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

    public static Tile[] get(String s) {// Function that creating a word from string
        Tile[] ts = new Tile[s.length()];
        int i = 0;
        for (char c : s.toCharArray()) {
            ts[i] = Tile.Bag.getBag().getTile(c);
            i++;
        }
        return ts;
    }
}

