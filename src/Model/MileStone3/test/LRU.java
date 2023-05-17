package Model.MileStone3.test;
import java.util.LinkedHashSet;

public class LRU implements CacheReplacementPolicy {

    LinkedHashSet<String> words = new LinkedHashSet<String>();
    @Override
    public void add(String word){
        if(words.contains(word))
            words.remove(word);
        words.add(word);
    }

    @Override
    public String remove() {

        String s = words.stream().findFirst().get();
        words.remove(s);
        return s;
    }
}
