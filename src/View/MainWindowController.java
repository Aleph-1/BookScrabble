package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class MainWindowController {
    @FXML
    GridPane myGrid;
    char h_v;
    boolean clicked=false;
    Map<Button,String> styleMap;

    public void saveStyles(){
        styleMap=new HashMap<>();
        for(Node but:myGrid.getChildren()){
            if(but instanceof Button){
                styleMap.put((Button) but,but.getStyle());
            }
        }
    }


    public void clicked(ActionEvent actionEvent) {
        Button but=(Button) actionEvent.getSource();
        int col= myGrid.getColumnIndex(but);
        int row= myGrid.getRowIndex(but);
        Button right = getButtonByLocation(row,col+1);
        if(!clicked)
            saveStyles();
        if(!right.getStyle().contains("-fx-background-color: #00ff00;")){
            h_v='H';
            right.setStyle("-fx-background-color: #00ff00;");
        }
        else{
            h_v='V';
            right.setStyle("-fx-background-color: blue;");
        }
        for(Node but1:myGrid.getChildren()){
            if(but1 instanceof Button){
                but1.setStyle(styleMap.get(but1));
            }
        }
        if(h_v=='H'){
            for(int i=col;i<16;i++){
                Button n=getButtonByLocation(row,i);
                n.setStyle("-fx-background-color: #00ff00;");
            }
        }
        else{
            for(int i=row;i<16;i++){
                Button n=getButtonByLocation(i,col);
                n.setStyle("-fx-background-color: #00ff00;");
            }
        }



        but.setStyle("-fx-background-color: #00ff00;");
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
