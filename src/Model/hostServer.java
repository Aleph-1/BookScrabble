package Model;

import Model.MileStone3.test.*;

import java.io.*;
import java.util.Arrays;

public class hostServer {

    MyServer server;
    DictionaryManager dm;
    BookScrabbleHandler bsh = new BookScrabbleHandler();
    Board b =Board.getBoard();


    class multClientHandler implements ClientHandler{


        private static Tile[] get(String s) {
            Tile[] ts=new Tile[s.length()];
            int i=0;
            for(char c: s.toCharArray()) {
                ts[i] = Tile.Bag.getBag().getTile(c);
                i++;
            }
            return ts;
        }

        BufferedReader in;
        PrintWriter out;


        //The client will send the request with this convention - [x,y,V/H] Q/C[BOOK_NAMES separated by comma],
        @Override
        public void handleClient(InputStream inFromclient, OutputStream outToClient) {

            bsh.handleClient(inFromclient,outToClient);

            in = new BufferedReader(new InputStreamReader(inFromclient));
            out = new PrintWriter(outToClient, true);

            String boardDetails = null;
            String wordsDetails = null;
            try {
                boardDetails = in.readLine();
                wordsDetails = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //[x,y,V/H]
            String[] boardData = boardDetails.replace("[","").replace("]","").split(",");
            String[] wordsData = wordsDetails.split(",");

            //Creating Tiles for the words from PTM1

            Word w = new Word(get(wordsData[0]),Integer.parseInt(wordsData[1]),Integer.parseInt(wordsData[2]),wordsData[3].compareTo("V")==0);

            if(wordsData[0].equals("Q")) {
                dm = new DictionaryManager();
                out.println(dm.query(Arrays.copyOfRange(wordsData, 1, wordsData.length)));
                out.println(System.lineSeparator());
            }

            if(wordsData[0].equals("C")) {
                dm = new DictionaryManager();
                out.println(dm.challenge(Arrays.copyOfRange(wordsData, 1, wordsData.length)));
                out.println(System.lineSeparator());
            }




        }

        @Override
        public void close() {

        }
    }

    public boolean query(String... args){
        return dm.query(args);
    }


    public boolean challenge(String... args){
        return dm.challenge(args);
    }

    public void startConnection(int port, ClientHandler ch){
        server = new MyServer(port,ch);
    }



}
