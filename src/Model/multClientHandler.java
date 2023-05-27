package Model;

import Model.MileStone3.test.*;

import java.io.*;
import java.util.*;

public class multClientHandler implements ClientHandler { // -1 - Illegal command, 0 - Too many players, 1 - Accepted, 2 - Update Board according to provided string, 3 -Not your turn

    static int connections; //To check if someone is trying to give commands when it's not his turn.
    static Board b = Board.getBoard();
    ArrayList<Integer> users = new ArrayList<>();
    DictionaryManager dm;

    public boolean checkOutcomeWords(Word w,String... booksGiven){ //booksGiven - Q,Books,Word

        boolean state = true;
        ArrayList<String> books = new ArrayList<>();
        Collections.addAll(books,booksGiven);
        ArrayList<Word> listWords = b.getWords(w);

        for(Word wo: listWords){
            books.remove(books.size()-1);
            books.add(wo.toStringW());
            String[] str=new String[books.size()];
            books.toArray(str);

            if(Objects.equals(booksGiven[0], "Q")){
                state = state && query(str);
            }
            else {
                state = state && challenge(str);
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

    BufferedReader in;
    PrintWriter out;

    //The client will send the request with this convention - ID(0,1,2)
    //[x,y,V/H]
    // Q/C,[BOOK_NAMES separated by comma],WORD
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {

        in = new BufferedReader(new InputStreamReader(inFromclient));
        out = new PrintWriter(outToClient, true);

        String id;
        String boardDetails = null;
        String wordsDetails = null;

        while (true) {

            //Get data from client.
            try {
                id = in.readLine();
                boardDetails = in.readLine();
                wordsDetails = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int intId = Integer.parseInt(id);

            if (users.size() < 3 && !users.contains(intId))
                users.add(intId);
            else if (users.contains(intId) && users.get(connections % 3) != intId) { // If there's less than 3 connections and a user is already there.
                out.println("3 Not your turn!"); //Code 3 means not your turn
                break; // Initially was continue, if the player wants to make another turn he'll send another request
            }
            else if(!users.contains(intId)){
                out.println("0 Too many players, please enter in a new session!"); // 0 means too many players
                break;
            }

            //[x,y,V/H]
            String[] boardData = boardDetails.replace("[", "").replace("]", "").split(",");
            String[] wordsData = wordsDetails.split(",");

            Word w = new Word(get(wordsData[wordsData.length - 1]), Integer.parseInt(boardData[0]), Integer.parseInt(boardData[1]), boardData[2].compareTo("V") == 0);
            boolean dictionaryLegal = false;

            dm = new DictionaryManager();
            dictionaryLegal = checkOutcomeWords(w,wordsData);



            boolean state = b.boardLegal(w) && dictionaryLegal;


            if (state) {
                out.println(1 + " " + b.tryPlaceWord(w)); // 1 means Approved word,b.tryPlaceWord returns the score.
                out.println(2 + " " + "[" + w.getRow() + "," + w.getCol() + "," + w.isVertical() + "]"); // 2 means update board, each customer will update his board according to that.
                out.println(System.lineSeparator());
                connections++;
                break;
            }
            else {
                out.println("-1 Illegal Command Try again!"); //-1 means Illegal command
                break;
            }
        }

        close();


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
        return dm.challenge(Arrays.copyOfRange(args,1,args.length));
    }

    public boolean query(String... args) {
        return dm.query(Arrays.copyOfRange(args,1,args.length));
    }

}
