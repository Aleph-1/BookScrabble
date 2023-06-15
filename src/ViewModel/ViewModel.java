package ViewModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import Model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewModel implements Observer {

    Model m;

    @Override
    public void update(Observable o, Object arg) {

    }
}
