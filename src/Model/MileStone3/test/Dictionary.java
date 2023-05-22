package Model.MileStone3.test;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {

    CacheManager cm1;
    CacheManager cm2;
    BloomFilter bf;
    String[] files;

    public Dictionary(String... filenames) {
        cm1 = new CacheManager(400,new LRU());
        cm2 = new CacheManager(100,new LFU());
        bf = new BloomFilter(256,"MD5","SHA1");
        files = filenames.clone();

        for(String f : filenames) {

            File file = new File(f);
            Scanner scanner = null;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            while (scanner.hasNext() == true) {
                String s = scanner.next();
                bf.add(s);
            }
            scanner.close();
        }
    }


    public Boolean query(String word) {
        if(cm1.query(word))
            return true;
        else if(cm2.query(word))
            return false;
        else if(bf.contains(word)) {
            cm1.add(word);
        }
        else {
            cm2.add(word);
        }

        return bf.contains(word);

    }

    public Boolean challenge(String word) {

        boolean b;
        b = IOSearcher.search(word,files);

        if(b)
            cm1.add(word);
        else
            cm2.add(word);

        return b;

    }

}
