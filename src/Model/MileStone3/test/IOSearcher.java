package Model.MileStone3.test;


import java.io.*;

public class IOSearcher {

    public static Boolean search(String word, String... filenames) {


        for (String spfile : filenames) {  //spfile is specific file


            File f1 = new File(spfile);
            FileReader fr = null;  //Creation of File Reader object
            try {
                fr = new FileReader(f1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
            String s;
            String[] words = null;

            while (true) {
                try {
                    if (!((s = br.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                words = s.split(" ");
                for (String w : words) {
                    if (w.equals(word))
                        return true;
                }
            }

            try {
                fr.close();
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}

