package ViewModel;

import Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Observable;
import java.util.Observer;

public class ViewModel implements Observer{
    Model m;
    public StringProperty protocol;
    public ViewModel(Model m){
        this.m=m;
        m.addObserver(this);
        protocol=new SimpleStringProperty();
        protocol.addListener((o,ov,nv)->m.startHost(3000));
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}