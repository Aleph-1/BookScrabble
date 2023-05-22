package Model.MileStone3.test;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.LinkedHashSet;

public class LFU implements CacheReplacementPolicy{
    LinkedHashSet<AbstractMap.SimpleEntry<String,Integer>> words = new LinkedHashSet<AbstractMap.SimpleEntry<String,Integer>>();
    @Override
    public void add(String word) {

        Boolean flag = false;

        for(AbstractMap.SimpleEntry<String,Integer> pair : words){
            if(pair.getKey().equals(word)) {
                pair.setValue(pair.getValue() + 1);
                flag = true;
            }
        }

        if(!flag) {
            AbstractMap.SimpleEntry<String, Integer> p = new AbstractMap.SimpleEntry<String,Integer>(word,1);
            words.add(p);
        }
    }

    @Override
    public String remove() {
        AbstractMap.SimpleEntry<String,Integer> t = Collections.min(words,(p1,p2)->p1.getValue()-p2.getValue());
        String s = t.getKey();
        words.remove(t);
        return s;
    }
}
