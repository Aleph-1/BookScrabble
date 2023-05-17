package Model.MileStone3.test;


import java.util.Arrays;
import java.util.Objects;

public class Word {

         Tile tiles[];
         int row,col;
         boolean vertical;

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return row == word.row && col == word.col && vertical == word.vertical && Arrays.equals(tiles, word.tiles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(row, col, vertical);
        result = 31 * result + Arrays.hashCode(tiles);
        return result;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVertical() {
        return vertical;
    }

    Word(Tile[] tiles, int row, int col, boolean vert) {

        if (tiles == null) {
            this.row = row;
            this.col = col;
            vertical = vert;
        }

        else {
            this.tiles = new Tile[tiles.length];
            for (int i = 0; i < tiles.length; i++) {
                this.tiles[i] = tiles[i];
            }
            this.row = row;
            this.col = col;
            vertical = vert;

        }
    }

	
}
