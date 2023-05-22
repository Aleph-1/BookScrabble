package Model.MileStone3.test;

import java.util.Objects;
import java.util.Random;

public class Tile {

    final char letter;
    final int score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    private Tile(Tile t){
        this.letter = t.letter;
        this.score = t.score;
    }

    public static class Bag{

        int repeatABC[] = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        final int repeats[] = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
        final int scores[] = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
        Tile Tiles[] = new Tile[26];
        char alphabet[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        public Tile getRand(){
            Random r = new Random();
            int chNum = r.nextInt(26); // chosen number
            int finalIndex;
            if(repeatABC[chNum] > 0) {
                repeatABC[chNum]--;
                finalIndex = chNum;
            }

            else {
                for(int i = chNum; i%chNum != 0 ; i++){
                    if(repeatABC[i] > 0) {
                        repeatABC[i]--;
                        finalIndex = i;
                        break;
                    }
                }
                return null;
            }

            return Tiles[finalIndex];

        }

        public Tile getTile(char ch){

            if(ch < 'A' || ch > 'Z')
                return null;

            if(repeatABC[ch-'A'] > 0){
                repeatABC[ch-'A']--;
                return Tiles[ch-'A'];
            }
            return null;

        }

        public void put(Tile t){

            if(t == null)
                return;
            if(repeatABC[t.letter - 'A'] == repeats[t.letter-'A'] ) {
                return;
            }
            repeatABC[t.letter-'A']++;
        }

        public int size(){

            int count = 0;
            for(int occ : repeatABC){
                count++;
            }
            return count;

        }


        public int[] getQuantities(){
            int[] newArr = new int[26];
            System.arraycopy(repeatABC,0,newArr,0,26);
            return newArr;
        }

        private static Bag b = null;
        public static Bag getBag(){
            if(b == null){
                b = new Bag();
            }
            return  b;
        }

        private Bag(){
            for(int i = 0; i < 26; i++){
                Tiles[i] = new Tile(alphabet[i],scores[i]);
            }
        }


    }


}
