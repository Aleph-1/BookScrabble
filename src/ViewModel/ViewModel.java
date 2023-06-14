package ViewModel;

import Model.MileStone3.test.Board;
import Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Observable;
import java.util.Observer;

public class ViewModel implements Observer{
    Model m;
    public StringProperty protocol;
    public StringProperty text;
    public Board board=Board.getBoard();
    public ViewModel(Model m){
        this.m=m;
        m.addObserver(this);
        protocol=new SimpleStringProperty();
        text=new SimpleStringProperty();
        text.bind(protocol);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}