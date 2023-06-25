package Model.MileStone3.test;


import java.util.ArrayList;

public class Board {
private Tile[][] grid = new Tile[15][15];
private static boolean firstTurn = true;

private Board(){
    for(int i = 0; i < 15;i++){
        for(int j = 0 ; j < 15; j++){
            grid[i][j]=null;
        }
    }
}

public Tile[][] getTiles(){
    Tile[][] gr = new Tile[15][15];
    for(int i = 0; i < 15;i++){
        for(int j = 0 ; j < 15; j++){
            gr[i][j]=grid[i][j];
        }
    }


    return gr;
}


private static Board board = null;
public static Board getBoard(){
    if(board == null){
        board = new Board();
    }
    return board;
}
private boolean isInBoard(Word w){
    if(w.col < 0 || w.col > 14 || w.row < 0 || w.row > 14)
        return false;

    if(w.vertical == true && (w.row+w.tiles.length-1 > 14))
        return false;

    if(w.vertical == false && (w.col+w.tiles.length-1 > 14))
        return false;

    return true;
}

private boolean isOnOrNear(Word w){

    if(firstTurn){
        return true;
    }

    for(int i = 0; i < w.tiles.length; i++){
        if(w.vertical == true) {
            if (w.tiles[i] == grid[w.row+i][w.col])
                return true;
        }
        else if(w.vertical == false){
            if (w.tiles[i] == grid[w.row][w.col+i])
                return true;
        }
    }
return false;


}
public boolean boardLegal(Word w){
    if(!isInBoard(w))
        return false;

    if(firstTurn && w.col-1 != 7 && w.row-1 != 7)
        return false;

    if((w.vertical == true && w.row+w.tiles.length < 7) || (w.vertical==false && w.col+w.tiles.length < 7)){
        return false;
    }
return true;
}
public boolean dictionaryLegal(Word w){
    return true;
}
private Word makeWord(ArrayList<Tile> tiles, int row, int col, boolean vert){
    Word word = new Word(null,row,col,vert);
    word.tiles = new Tile[tiles.size()];
    for(int i = 0; i < tiles.size();i++)
        word.tiles[i]= tiles.get(i);
    return word;
}

private Word horizontalWord(Word w, Tile[][] tempGrid,ArrayList<Tile> word){
    int left = 0;
    int finalCol;
    while(tempGrid[w.row][w.col-left] != null)
        left++;
    left--;
    finalCol = w.col-left;

    while(tempGrid[w.row][w.col+left] != null){
        word.add(tempGrid[w.row][w.col+left]);
        left++;
    }

    return makeWord(word,w.row,finalCol,w.vertical);
}

    private Word verticalWord(Word w, Tile[][] tempGrid,ArrayList<Tile> word){


        int top = 0;
        int finalRow;

        while(tempGrid[w.row-top][w.col] != null)
            top++;
        top--;
        finalRow = w.row-top;
        while(tempGrid[w.row-top][w.col] != null){
            word.add(tempGrid[w.row-top][w.col]);
            top--;
        }
        return makeWord(word,finalRow,w.col,w.vertical);
    }


    public Tile[][] copyGrid(Tile[][] gr){

    Tile[][] tempGrid = new Tile[15][15];

    for(int i = 0; i < 15; i++){
        for(int j = 0; j < 15; j++){
            tempGrid[i][j] = gr[i][j];
        }
    }

    return tempGrid;
    }



public ArrayList<Word> getWords(Word w) {


    //grid.clone();
    Tile[][] tempGrid = copyGrid(grid);
    placeOnBoard(w, tempGrid);
    ArrayList<Tile> tempWord = new ArrayList<Tile>();
    ArrayList<Word> words = new ArrayList<Word>();
    int top = 0;
    int left = 0;
    int finalRow;
    int finalCol = 0;
    boolean flagEnterWhileH = false; //Horizontal
    boolean flagEnterWhileV = false; // Vertical

    if (w.vertical == false) {
        for (int i = 0; i < w.tiles.length; i++) {
                if(w.tiles[i] == null)
                    continue;

                while (tempGrid[w.row - (top + 1)][w.col + i] != null)
                    top++;

                finalRow = w.row - top;

                while (tempGrid[w.row - (top - 1)][w.col + i] != null) {
                    tempWord.add(tempGrid[w.row - top][w.col + i]);
                    top--;
                    flagEnterWhileH = true;
                }

                if (flagEnterWhileH) {
                    tempWord.add(tempGrid[w.row - top][w.col + i]);
                    flagEnterWhileH = false;
                }
                finalCol = w.col + i;


                if (tempWord.size() != 0)
                    words.add(makeWord(tempWord, finalRow, finalCol, true));
                tempWord.clear();
            }
        words.add(horizontalWord(w, tempGrid, tempWord));
    }

    else {

        for (int i = 0; i < w.tiles.length; i++) {
            left = 0;
            if(w.tiles[i] == null)
                continue;
            //find horizontal words in vertical words

            while (tempGrid[w.row+i][w.col - (left+1)] != null){
                left++;
            }

            finalCol = w.col-left;
            while (tempGrid[w.row+i][w.col + left+1] != null) {
                tempWord.add(tempGrid[w.row+i][w.col + left]);
                left++;
                flagEnterWhileV = true;
            }

            if(flagEnterWhileV) {
                tempWord.add(tempGrid[w.row + i][w.col + left]);
                flagEnterWhileV = false;
            }
        }

        if (tempWord.size() != 0)
            words.add(makeWord(tempWord, w.row, finalCol, false));
        tempWord.clear();

        words.add(verticalWord(w, tempGrid, tempWord));
    }
    return words;
}






private boolean isDLS(int row, int col){
    if((row == 0 &&  col == 3) || (row == 0 &&  col == 11) || (row == 14 &&  col == 3) ||
            (row == 14 &&  col == 11) || (row == 3 &&  col == 0) ||
            (row == 3 &&  col == 14) || (row == 11 &&  col == 0) || (row == 11 &&  col == 14) || (row == 2 &&  col == 6) || (row == 2 &&  col == 8) || (row == 3 &&  col == 7) || (row == 6 &&  col == 2) ||
            (row == 8 &&  col == 2) || (row == 7 &&  col == 3) || (row == 12 &&  col == 6) || (row == 11 &&  col == 7)||
            (row == 12 &&  col == 8) || (row == 6 &&  col == 12) || (row == 7 &&  col == 11) ||(row == 8 &&  col == 12) ||
            (row == 6 &&  col == 8) || (row == 6 &&  col == 6) || (row == 8 &&  col == 6) || (row == 8 &&  col == 8)
    )
        return true;
    return false;
}

private boolean isDWS(int row, int col){

        if(row == col && col == 7)
            return false;

        if((row == col || row+col == 14) && !isTWS(row,col) && !isTLS(row,col) && !isDLS(row,col))
            return true;
return false;
}

private boolean isTLS(int row, int col){

    if((row==1 && col == 5) || (row==1 && col == 9) || (row==5 && col == 1) || (row==5 && col == 5)
            ||(row==5 && col == 9) || (row==5 && col == 13 || (row==9 && col == 1) || (row==9 && col == 5)
            || (row==9 && col == 9) || (row==9 && col == 13) || (row==13 && col == 5)|| (row==13 && col == 9)))
        return true;
    return false;

}

private boolean isTWS(int row, int col){

    if((row==0 && col == 0) || (row==14 && col == 14)
    || (row==7 && col == 0) || (row==7 && col == 13) || (row==14 && col == 0 || (row==14 && col == 14)))
        return true;
    return false;

}

private  boolean isStar(int row, int col){

    if( row == col && col == 7 && firstTurn == true)
        return true;

    return false;
}

private int getWordScore(Word w){

    int score = 0;

    for(int i = 0; i < w.tiles.length; i++){
            score += w.tiles[i].score;
    }

    return score;
}


private int getScore(Word w) {

    int score = 0;
    boolean flag = false;


    for (int i = 0; i < w.tiles.length; i++) {

        if (w.vertical == true) {

            if (isDLS(w.row + i, w.col)) {
                score += 2 * w.tiles[i].score;
                flag = true;
            }

            if (isTLS(w.row + i, w.col)) {
                score += 3 * w.tiles[i].score;
                flag = true;
            }

            if (!flag)
                score += w.tiles[i].score;
            flag = false;
        }

        else {
            if (isDLS(w.row, w.col+i)) {
                score += 2 * w.tiles[i].score;
                flag = true;
            }

            if (isTLS(w.row, w.col+i)) {
                score += 3 * w.tiles[i].score;
                flag = true;
            }

            if (!flag)
                score += w.tiles[i].score;
            flag = false;

        }

    }


    for (int i = 0; i < w.tiles.length; i++) {

                if (w.vertical == true) {

                    if (isDWS(w.row + i, w.col) || isStar(w.row+i,w.col)) {
                        score *= 2;

                    }

                    if (isTWS(w.row + i, w.col)) {
                        score *= 3 ;

                    }
                }

                else {
                    if (isDWS(w.row, w.col+i) || isStar(w.row, w.col+i)) {
                        score *= 2;

                    }

                    if (isTWS(w.row, w.col+i)) {
                        score *= 3;

                    }
                }
            }




    return score;
}

private void placeOnBoard(Word w, Tile[][] gr){


    if(w.vertical == true){

        for(int i = w.row; i < w.row+w.tiles.length; i++){
            if(w.tiles[i-w.row] == null)
                continue;
            gr[i][w.col] = w.tiles[i-w.row];
        }
    }

    else{
        for(int i = w.col; i < w.col+w.tiles.length; i++){
            if(w.tiles[i-w.col] == null)
                continue;
            gr[w.row][i] = w.tiles[i-w.col];
        }

    }

}

public int tryPlaceWord(Word w){
int score = 0;

    if(boardLegal(w)){

        ArrayList<Word> words = getWords(w);
        for(int i = 0; i < words.size(); i++){
            if(!dictionaryLegal(words.get(i)))
                return 0;

        }

        for (Word word : words){
            if(!isOnOrNear(word))
                words.remove(word);

        }


        for(Word word : words){
            placeOnBoard(word,grid);
        }


        for(Word word : words){
            score += getScore(word);
        }


        firstTurn = false;
        return score;


    }

    return 0;

}
}