package Model.MileStone3.test;


import java.io.*;
import java.util.Arrays;

public class BookScrabbleHandler implements ClientHandler {

    BufferedReader in;
    PrintWriter out;

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
    try {

        DictionaryManager dm = null;

        in = new BufferedReader(new InputStreamReader(inFromclient));
        out = new PrintWriter(outToClient, true);
        String line = in.readLine();

        String[] words = line.split(",");


        if(words[0].equals("Q")) {
            dm = new DictionaryManager();
            out.println(dm.query(Arrays.copyOfRange(words, 1, words.length)));
            out.println(System.lineSeparator());
        }

        if(words[0].equals("C")) {
            dm = new DictionaryManager();
            out.println(dm.challenge(Arrays.copyOfRange(words, 1, words.length)));
            out.println(System.lineSeparator());
        }

        close();


    }catch (IOException e){}
    }

    @Override
    public void close() {

        try{
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
