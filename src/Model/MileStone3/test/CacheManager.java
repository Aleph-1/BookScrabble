package Model.MileStone3.test;

import java.util.HashSet;

public class CacheManager {

    HashSet<String> words = new HashSet<String>();
    int maxSize;
    CacheReplacementPolicy crp;
    public CacheManager(int size, CacheReplacementPolicy crp){

        maxSize = size;
        this.crp = crp;

    }

    public Boolean query(String s){
        return words.contains(s);
    }

    public void add(String s){
        crp.add(s);
        words.add(s);
        if(words.size() > maxSize)
            words.remove(crp.remove());
    }

	

}
