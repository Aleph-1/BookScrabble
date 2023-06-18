package View;

import Model.MileStone3.test.Board;
import Model.MileStone3.test.Tile;
import Model.MileStone3.test.Word;
import Model.Model;
import ViewModel.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Console;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class MainWindowController extends Observable {
    @FXML
    GridPane myGrid;
    @FXML
    TextField Text;
    char h_v;
    int id;
    int x,y;
    String word;

    ViewModel vm=new ViewModel(new Model());
    String IP = "localhost";
    int PORT = 3080;
    Board b = Board.getBoard();
    public IntegerProperty score=new SimpleIntegerProperty();
    public StringProperty statusMessage=new SimpleStringProperty();
    String protocol;
    Button[][] buttons=new Button[15][15];


    boolean clicked=false;
    Map<Button,String> styleMap;
    void init(ViewModel vm){
        this.vm=vm;
        this.addObserver(vm);
        score.set(0);
        statusMessage.set("");
    }

    public static String text(int id,int x, int y, char v_or_h,char q_or_c,String word){
        return id+" ["+x+","+y+","+v_or_h+"]"+" "+q_or_c+",bee.txt,"+word;
    }


    public void queryButton(){
        if(clicked){
            word=Text.getText();
            protocol=text(id,x,y,h_v,'Q',word);
            Boolean n=h_v=='V';
            Word w=new Word(get(word),x,y,n);
            vm.request=protocol;
            setChanged();
            notifyObservers(IP + " " + PORT);
            System.out.println(protocol); //For testing reasons.
            b=Board.getBoard();
            updateBoard(b);
        }

    }


    public void saveStyles(){
        styleMap=new HashMap<>();
        for(Node but:myGrid.getChildren()){
            if(but instanceof Button){
                styleMap.put((Button) but,but.getStyle());
            }
        }
    }

    public void updateBoard(Board b1){
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                Button but=buttons[i][j];
                if(b1.getTiles()[i][j].letter==' '){
                    but.setText(String.valueOf(b1.getTiles()[i][j].letter));
                }
            }
        }
    }



    private static Tile[] get(String s) {
        Tile[] ts = new Tile[s.length()];
        int i = 0;
        for (char c : s.toCharArray()) {
            ts[i] = Tile.Bag.getBag().getTile(c);
            i++;
        }
        return ts;
    }


    public void saveButtons(){
        for(Node but:myGrid.getChildren()){
            if(but instanceof Button){
                buttons[myGrid.getRowIndex(but)-1][myGrid.getColumnIndex(but)-1]= (Button) but;
            }
        }
    }

    public void clicked(ActionEvent actionEvent) {
        saveButtons();
        Button but=(Button) actionEvent.getSource();
        int col= myGrid.getColumnIndex(but);
        int row= myGrid.getRowIndex(but);
        x=col;
        y=row;
        Button right = buttons[row-1][col];
        if(!clicked)
            saveStyles();
        if((!right.getStyle().contains("-fx-border-color: #00ff00;")&&col!=15)||row==15){
            h_v='H';
        }
        else{
            h_v='V';
        }
        for(Node but1:myGrid.getChildren()){
            if(but1 instanceof Button){
                but1.setStyle(styleMap.get(but1));
            }
        }
        if(h_v=='H'){
            for(int i=col;i<16;i++){
                Button n=buttons[row-1][i-1];
                n.setStyle("-fx-border-color: #00ff00;");
            }
        }
        else{
            for(int i=row;i<16;i++){
                Button n=buttons[i-1][col-1];
                n.setStyle("-fx-border-color: #00ff00;");
            }
        }




        clicked=true;
    }




}