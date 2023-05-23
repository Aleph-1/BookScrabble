package Model;

import Model.MileStone3.test.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class multClientHandler implements ClientHandler { // -1 - Illegal command, 0 - Too many players, 1 - Accepted, 2 - Update Board according to provided string, 3 -Not your turn

    static int connections; //To check if someone is trying to give commands when it's not his turn.
    static Board b = Board.getBoard();
    ArrayList<Integer> users = new ArrayList<>();
    DictionaryManager dm;


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

            if (users.size() < 3)
                users.add(intId);
            else if (users.get(connections % 3) != intId) {
                out.println("3 Not your turn!"); //Code 3 means not your turn
                continue;
            } else {
                out.println("0 Too many players, please enter in a new session!"); // 0 means too many players
                break;
            }

            //[x,y,V/H]
            String[] boardData = boardDetails.replace("[", "").replace("]", "").split(",");
            String[] wordsData = wordsDetails.split(",");

            Word w = new Word(get(wordsData[wordsData.length - 1]), Integer.parseInt(boardData[0]), Integer.parseInt(boardData[1]), boardData[2].compareTo("V") == 0);
            boolean dictionaryLegal = false;

            if (wordsData[0].equals("Q")) {
                dm = new DictionaryManager();
                dictionaryLegal = query(wordsData);
            }

            if (wordsData[0].equals("C")) {
                dm = new DictionaryManager();
                dictionaryLegal = challenge(wordsData); //Need to add bonus points accordingly
            }


            boolean state = b.boardLegal(w) && dictionaryLegal;


            if (state) {
                out.println(1 + " " + b.tryPlaceWord(w)); // 1 means Approved word,b.tryPlaceWord returns the score.
                out.print(2 + " " + "[" + w.getRow() + "," + w.getCol() + "," + w.isVertical() + "]"); // 2 means update board.
                out.println(System.lineSeparator());
                connections++;
                break;
            } else
                out.println("-1 Illegal Command Try again!"); //-1 means Illegal command
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
