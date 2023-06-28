package test;

import Model.MileStone3.test.Board;
import Model.MileStone3.test.Tile;
import Model.MileStone3.test.Word;
import View.MainWindowController;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class viewTest {
    private static Tile[] get(String s) {
        Tile[] ts = new Tile[s.length()];
        int i = 0;
        for (char c : s.toCharArray()) {
            ts[i] = Tile.Bag.getBag().getTile(c);
            i++;
        }
        return ts;
    }
    View.MainWindowController wc=new MainWindowController();


    public void testView(){
        GridPane g=wc.myGrid;
        wc.saveButtons();
        for(int i=0;i<wc.buttons[i].length;i++){
            for(int j=0;j<wc.buttons[j].length;j++){

            }
        }
        Board b=Board.getBoard();
        Word w=new Word(get("Bomb"),7,7,true);
        b.tryPlaceWord(w);
    }
}
