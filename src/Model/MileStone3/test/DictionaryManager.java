package Model.MileStone3.test;
import java.util.HashMap;

public class DictionaryManager {

    HashMap<String,Dictionary> books;
    private static volatile DictionaryManager dm = null;

    public DictionaryManager(){
        books = new HashMap<String, Dictionary>();
    }

    public boolean query(String... args) {

        boolean flag = false;

        for (int i = 0; i < args.length - 1; i++) {

            if (books.get(args[i]) == null)
                books.put(args[i], new Dictionary(args[i]));

            flag = flag || books.get(args[i]).query(args[args.length-1]);
        }

        return flag;
    }

    public boolean challenge(String... args) {

        boolean flag = false;

        for (int i = 0; i < args.length - 1; i++) {
            if (books.get(args[i]) == null)
                books.put(args[i], new Dictionary(args[i]));

            flag = flag || books.get(args[i]).challenge(args[args.length-1]);
        }

        return flag;
    }


    public int getSize(){
        return books.size();
    }


    public static DictionaryManager get(){
        if(dm == null)
            dm = new DictionaryManager();
        return dm;
    }

}


