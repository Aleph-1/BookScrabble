package Model;

import Model.MileStone3.test.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

import java.io.*;
import java.util.*;

public class multClientHandler extends Observable implements ClientHandler, Runnable { // -1 - Illegal command, 0 - Too many players, 1 - Accepted, 2 - Update Board according to provided string, 3 -Not your turn, 4 - Next turn is id specified

    static int connections; //To check if someone is trying to give commands when it's not his turn.
    static Board b = Board.getBoard();

    public StringProperty status;
    String newGameData; //The X,Y,V/H,etc...
    HashMap<Integer,PrintWriter> users = new HashMap<>();
    DictionaryManager dm = new DictionaryManager();
    BufferedReader in;
    PrintWriter out;


    public boolean checkOutcomeWords(Word w, String... booksGiven) { //booksGiven - Q,Books,Word

        boolean state = true;
        ArrayList<String> books = new ArrayList<>();
        Collections.addAll(books, booksGiven);
        ArrayList<Word> listWords = b.getWords(w);

        for (Word wo : listWords) {
            books.remove(books.size() - 1);
            books.add(wo.toStringW());
            String[] str = new String[books.size()];
            books.toArray(str);

            if (Objects.equals(booksGiven[0], "Q")) {
                state = state && query(str);
            } else {
                state = state && challenge(str); //In the future we'll need to add extra points accordingly.
            }
        }

        return state;
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

    private void sendApproved(Word w) {
        connections++;


        out.println(1 + " " + b.tryPlaceWord(w)); // 1 means Approved word,b.tryPlaceWord returns the score.

        out.println(2 + " " + "[" + w.getRow() + "," + w.getCol() + "," + w.isVertical() + "," + w.toStringW()+"]");


        /*
        for(PrintWriter pw : users.values()) //Notify all
            pw.println(2 + " " + "[" + w.getRow() + "," + w.getCol() + "," + w.isVertical() + "," + w.toStringW()+"]");  // 2 means update board, each customer will update his board according to that.
         */


        if(connections < 4)
            out.println(4 + " " + "Not enough users to determine whose turn next"); // At least 4 users are needed to know which turn's next.
        else
        users.get(connections % 4).println(4 + " Your Turn!");
        out.println(System.lineSeparator());
    }


    //The client will send the request with this convention - ID
    //[x,y,V/H]
    // Q/C,[BOOK_NAMES separated by comma],WORD
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) { //The host can be a player he just needs to connect to the server like regular clients.

        in = new BufferedReader(new InputStreamReader(inFromclient));
        out = new PrintWriter(outToClient, true);

    }


    @Override
    public void close() {

        try {
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean challenge(String... args) {
        return dm.challenge(Arrays.copyOfRange(args, 1, args.length));
    }

    public boolean query(String... args) {
        return dm.query(Arrays.copyOfRange(args, 1, args.length));
    }

    @Override
    public void run() {

        String id;
        String boardDetails;
        String wordsDetails;


        //Get data from client.
        try {
            id = in.readLine();
            boardDetails = in.readLine();
            wordsDetails = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int intId = Integer.parseInt(id);

        if (users.size() < 4 && !users.keySet().contains(intId))
            users.put(intId,out);
        else if (users.keySet().contains(intId) && users.keySet().stream().toList().get((connections % 4)) != intId) { // If there's less than 4 connections and a user is already there.
            out.println("3 Not your turn!"); //Code 3 means not your turn
            return;
        }
        // Initially was continue, if the player wants to make another turn he'll send another request
        else if (!users.keySet().contains(intId)) {
            out.println("0 Too many players, please enter in a new session!"); // 0 means too many players
            return;
        }

        //[x,y,V/H]
        String[] boardData = boardDetails.replace("[", "").replace("]", "").split(",");
        String[] wordsData = wordsDetails.split(",");

        Word w = new Word(get(wordsData[wordsData.length - 1]), Integer.parseInt(boardData[0]), Integer.parseInt(boardData[1]), boardData[2].compareTo("V") == 0);

        if (b.boardLegal(w) && checkOutcomeWords(w, wordsData)) {
            sendApproved(w); //We can't update the board live for all the clients until we have threads.
        }
        else
            out.println("-1 Illegal Command Try again!"); //-1 means Illegal command

        close();

        //In the future we might want to put everything in a while loop and have a timeout for connecting.

    }
}
