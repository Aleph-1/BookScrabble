package test;

import Model.MileStone3.test.Board;
import Model.MileStone3.test.Tile;
import Model.MileStone3.test.Word;
import Model.Model;
import View.MainWindowController;
import View.ViewRun;
import ViewModel.ViewModel;
import com.sun.tools.javac.Main;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import static java.lang.Thread.sleep;

public class viewTest extends MainWindowController{
    @FXML
    public GridPane myGrid;
    @FXML
    public TextField Text;

    @FXML
    public TextArea area;

    @FXML
    public TextArea serRes;

    @FXML
    public TextArea scoreText;
    @FXML
    public  TextArea userName;

    public void idApply(){// Applying the user's id
        b=Board.getBoard();
        id= Integer.parseInt(userName.getText());
        updateBoard(b);
    }

    char h_v;
    int id;
    int x,y;
    String word;
    public ArrayList<Character> bagOfChars=new ArrayList<>();

    ViewModel vm=new ViewModel(new Model());
    String IP = "localhost";
    int PORT = 8085;
    public Board b = Board.getBoard();
    Tile.Bag bag= Tile.Bag.getBag();
    public IntegerProperty score=new SimpleIntegerProperty();
    public StringProperty statusMessage=new SimpleStringProperty();


    String protocol;
    public Button[][] buttons=new Button[15][15];


    boolean clicked=false;
    Map<Button,String> styleMap;




    public void init(ViewModel vm){//Initiating the view
        this.vm=vm;
        this.addObserver(vm);
        score = new SimpleIntegerProperty();
        statusMessage = new SimpleStringProperty();
        score.set(0);
        statusMessage.set("");
    }




    public static String text(int id,int x, int y, char v_or_h,char q_or_c,String word){//A function that creates the protocol we created
        return id+" ["+x+","+y+","+v_or_h+"]"+" "+q_or_c+",bee.txt,"+word;
    }


    public void queryButton(){
        check('Q');
    }//On query Button click
    public void challengeButton(){
        check('C');
    }//On challenge Button click

    public void check(char q_c){//The function that sends the request to the vm

        if(clicked){

            word=Text.getText();//Getting the text from the text box

            if(wordInBag(word)){//Checking if you can create the word from the bag
                protocol=text(id,y-1,x-1,h_v,q_c,word);//creating the protocol
                Boolean n= h_v=='V';
//                Word w=new Word(get(word),y-1,x-1,n);
                vm.request=protocol;
                setChanged();
                notifyObservers(IP + " " + PORT);//Notifying the observers
                System.out.println(protocol); //For testing reasons.
                serRes.setText((statusMessage).getValue());//Showing the server response on the screen
                scoreText.setText(String.valueOf(score.getValue()));//Updating the score;
                b=Board.getBoard();
                updateBoard(b);//Updating the board on view
                if(!statusMessage.getValue().equals("Invalid word try again!")){
                    bagRemove(word);//Removing the letters from the bag
                }
            }
            else {
                serRes.setText("Not From Bag");
            }


        }

    }
    public Boolean wordInBag(String w){//Checking if a certain word can be created by the bag chars
        for(int i=0;i<w.length();i++)
            if(!bagOfChars.contains(w.charAt(i)))
                return false;
        return true;
    }

    public void bagRemove(String w){//Removing the letters of a certain word from the bag
        ArrayList<Character> bag1=new ArrayList<>();
        for(int i=0;i<w.length();i++)
            if(!bagOfChars.contains(w.charAt(i))&&w.charAt(i)!=' '){
                bag1.add(w.charAt(i));
                i++;
            }

        bagOfChars.clear();
        bagOfChars=bag1;
        draw();
    }


    public void saveStyles(){//Saving the style of all the buttons
        styleMap=new HashMap<>();
        for(Node but:myGrid.getChildren()){
            if(but instanceof Button){
                styleMap.put((Button) but,but.getStyle());
            }
        }
    }

    public void updateBoard(Board b1){//Updating the view based on the board
        saveButtons();
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                Button but=buttons[i][j];
                if(b1.getTiles()[i][j]!=null){
                    but.setText(String.valueOf(b1.getTiles()[i][j].letter));
                }

            }
        }
    }

    public void draw(){//Drawing a card from the bag
        int s=0;
        while(bagOfChars.size()<6){
            Tile c= bag.getRand();
            if(c!=null) {
                bagOfChars.add(c.letter);
            }
        }
        while(s!=1){
            Tile c=bag.getRand();
            if(c!=null) {
                bagOfChars.add(c.letter);
                s++;
            }
        }

        displayBag();
    }

    public void displayBag(){// Displaying all the chars from the bag in view
        StringBuilder str=new StringBuilder();
        for(char c:bagOfChars){
            str.append(c+",");
            if(str.length()%17==0&&str.length()!=0)
                str.append("\n");

        }
        String s=str.toString();
        area.setText(s);
    }


    public void saveButtons(){//Collecting all the buttons once to an array
        for(Node but:myGrid.getChildren()){
            if(but instanceof Button){
                buttons[myGrid.getRowIndex(but)-1][myGrid.getColumnIndex(but)-1]= (Button) but;
            }
        }
    }

    public void clicked(ActionEvent actionEvent) {//Happens on click of a button
        saveButtons();
        b=Board.getBoard();
        updateBoard(b);
        Button but=(Button) actionEvent.getSource();//takes the initiating button
        int col= myGrid.getColumnIndex(but);
        int row= myGrid.getRowIndex(but);
        x=col;
        y=row;
        if(col==15)
            h_v='V';
        else{
            Button right = buttons[row-1][col];//taking the button to the right
            if((!right.getStyle().contains("-fx-border-color: #00ff00;")&&col!=15)||row==15){//checking if the button to the right is colored so it can change whether its horizontal or vertical
                h_v='H';
            }
            else{
                h_v='V';
            }
        }

        if(!clicked)
            saveStyles();

        for(Node but1:myGrid.getChildren()){//Resetting the board
            if(but1 instanceof Button){
                but1.setStyle(styleMap.get(but1));
            }
        }
        if(h_v=='H'){//Coloring all the horizontal buttons
            for(int i=col;i<16;i++){
                Button n=buttons[row-1][i-1];
                n.setStyle("-fx-border-color: #00ff00;");
            }
        }
        else{//Coloring all the vertical buttons
            for(int i=row;i<16;i++){
                Button n=buttons[i-1][col-1];
                n.setStyle("-fx-border-color: #00ff00;");
            }
        }




        clicked=true;
    }
    private Tile[] get(String s) {
        Tile[] ts = new Tile[s.length()];
        int i = 0;
        for (char c : s.toCharArray()) {
            ts[i] = Tile.Bag.getBag().getTile(c);
            i++;
        }
        return ts;
    }



    public void testView1() throws InterruptedException {


        saveButtons();
        bagOfChars.add('B');
        bagOfChars.add('O');
        bagOfChars.add('M');
        bagOfChars.add('B');

        Text.setText("BOMB");
        buttons[7][7].fireEvent(new ActionEvent());
        queryButton();
        System.out.println("Not in dictionary so shouldn't work");

        if(protocol.compareTo("0 [7,7,H] Q,bee.txt,BOMB")!=0){
            System.out.println("Wrong server response! (-50)");
        }
    }

    public void testView2() throws InterruptedException {


        saveButtons();
        bagOfChars.add('D');
        bagOfChars.add('O');
        bagOfChars.add('G');

        Text.setText("DOG");
        buttons[7][7].fireEvent(new ActionEvent());
        queryButton();
         System.out.println("Should work");

        if(protocol.compareTo("0 [7,7,V] Q,bee.txt,DOG")!=0){
            System.out.println("Wrong server response! (-50)");
        }
    }

    public void  testView() throws InterruptedException {
        testView1();
        testView2();
        System.out.println("Done");
    }

}
