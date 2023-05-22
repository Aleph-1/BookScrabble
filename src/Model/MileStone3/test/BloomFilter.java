package Model.MileStone3.test;

import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.security.MessageDigest;
import java.math.*;


public class BloomFilter {

    int size;
    BitSet bits = new BitSet();
    String[] algs;

    BloomFilter(int size, String... algs) {
        this.size = size;
        this.algs = algs;
    }

    public void add(String s) {

        MessageDigest md;
        for (int i = 0; i < algs.length; i++) {
            try {
                md = MessageDigest.getInstance(algs[i]);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] bts = md.digest(s.getBytes());
            BigInteger b = new BigInteger(bts);
            bits.set(Math.abs(b.intValue())%size);
        }

    }

    public Boolean contains(String s) {

        MessageDigest md;
        for (int i = 0; i < algs.length; i++) {
            try {
                md = MessageDigest.getInstance(algs[i]);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] bts = md.digest(s.getBytes());
            BigInteger b = new BigInteger(bts);
            int on = Math.abs(b.intValue())%size;
            if(!bits.get(on))
                return false;
        }
        return true;

    }

    @Override
    public String toString() {



        if(bits.isEmpty())
            return "";

            else {
                String on = bits.toString();
                on = on.replace("{", "");
                on = on.replace("}", "");
                String words[] = on.split(", ");

                StringBuilder sb = new StringBuilder(bits.length());
                for (int i = 0; i < bits.length(); i++) {
                    sb.append(0);
                }

                for (int i = 0; i < words.length; i++) {
                    int place = Integer.parseInt(words[i]);
                    sb.setCharAt(place, '1');
                }
                return sb.toString();
            }
    }
}







