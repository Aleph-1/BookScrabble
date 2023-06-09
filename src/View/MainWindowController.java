package View;

import Model.MileStone3.test.Board;
import Model.MileStone3.test.Tile;
import Model.Model;
import ViewModel.ViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.awt.event.MouseEvent;
import java.io.Console;
import java.util.HashMap;
import java.util.Map;

public class MainWindowController {
    @FXML
    GridPane myGrid;
    @FXML
    TextField Text;
    char h_v;
    int id;
    int x,y;
    String word;

    String protocol;
    Button[][] buttons=new Button[15][15];


    boolean clicked=false;
    Map<Button,String> styleMap;

    public static String text(int id,int x, int y, char v_or_h,char q_or_c,String word){
        return id+"\n["+x+","+y+","+v_or_h+"]"+"\n"+q_or_c+",bee.txt,"+word;
    }


    public void queryButton(){
        if(clicked){
            word=Text.getText();
            protocol=text(id,x,y,h_v,'Q',word);
            System.out.println(protocol);
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

    Button getButtonByLocation(int row,int col){
        Button l=new Button();
        for(Node but:myGrid.getChildren()){
            if(but instanceof Button){
                if((myGrid.getRowIndex(but)==row)&&(myGrid.getColumnIndex(but)==col)){
                    return (Button) but;
                }
            }
        }
        return l;
    }


}
