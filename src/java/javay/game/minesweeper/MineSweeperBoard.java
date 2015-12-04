package javay.game.minesweeper;

import java.awt.Color;
import java.util.Random;

/**
 * 扫雷
 * A Mine Sweeper board.  A Mine Sweeper board contains mines and
 * empty spaces.  The contents of a square can be revealed one at a time.
 * The player wins by revealing all the empty squares before revealing
 * a mine.
 *
 * @author Jim Glenn
 * @version 0.1 1/16/2007
 */
public class MineSweeperBoard extends GameBoardModel {
    /**
     * Default attributes of boards.
     */
    public static final int DEFAULT_SIZE = 10;
    public static final int DEFAULT_MINES = 10;

    /**
     * The number of mines on this board.
     */
    private int numMines;

    /**
     * The number of empty squares that have been revealed on this board.
     */
    private int foundEmpties;

    /**
     * Creates a new board of the default size with the default number
     * of mines.
     */
    public MineSweeperBoard() {
        this(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_MINES);
    }

    /**
     * Initializes a board of the given size with the default number of mines.
     *
     * @param width a positive integer
     * @param height a positive integer
     */
    public MineSweeperBoard(int width, int height) {
        this(width, height, DEFAULT_MINES);
    }

    /**
     * Initializes a board of the given size with the given number of mines.
     *
     * @param width a positive integer
     * @param height a positive integer
     * @param m a positive integer less than width * height
     */
    public MineSweeperBoard(int width, int height, int m) {
        super(width, height);

        numMines = m;
        foundEmpties = 0;

        setupBoard();
    }

    /**
     * Returns the color of this board at the given location.  The board
     * is gray everywhere.
     *
     * @param r a row on this board
     * @param c a column on this board
     * @return <CODE>Color.GRAY</CODE>
     */
    public Color getBoardColor(int r, int c) {
        return Color.GRAY;
    }

    /**
     * Randomly places mines on this board and counts the adjacent mines
     * at the other locations.
     */
    public void setupBoard() {
        // we ought to check here whether there are enough spaces for
        // us to place all of our mines

        Random rand = new Random();

        // for each mine, randomly pick a place to put it
        for (int m = 0; m < numMines; m++) {
            int r;
            int c;

            do
                {
                r = rand.nextInt(getHeight());
                c = rand.nextInt(getWidth());
                }
            while (getPieceAt(r, c) != null);

            pieces[r][c] = new Mine();
        }

        // for each other place, determine the number of adjacent mines

        for (int r = 0; r < getHeight(); r++)
            {
            for (int c = 0; c < getWidth(); c++)
                {
                if (getPieceAt(r, c) == null)
                    {
                    pieces[r][c] = new Empty(countAdjacentMines(r, c));
                }
            }
        }
    }

    /**
     * Counts the mines adjacent to the given location.
     *
     * @param r a row on this board
     * @param c a column on this board
     * @return the number of mines adjacent to that location
     */
    private int countAdjacentMines(int r, int c) {
        int mineCount = 0;

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if ((dr != 0 || dc != 0) && (inBounds(r + dr, c + dc)) && getPieceAt(r + dr, c + dc) instanceof Mine) {
                    mineCount++;
                }
            }
        }

        return mineCount;
    }

    /**
     * Returns false.
     */
    public boolean isLegalMove(int fromR, int toR, int fromC, int toC) {
        return false;
    }

    /**
     * Determines if the given move is legal.  It is legal to reveal
     * the contents of a location if the contents are currently hidden.
     *
     * @param r a row on this board
     * @param c a column on this board
     * @return true iff that block at that location hasn't been revealed
     */
    public boolean isLegalMove(int r, int c) {
        return (!isOver() && !getBlockAt(r, c).isVisible());
    }

    /**
     * Reveals the contents of the block at the given location.
     * The location must not be visible yet.
     *
     * @param r a row on this board
     * @param c a column on this board
     */
    public void makeMove(int r, int c) {
        MineSweeperBlock block = getBlockAt(r, c);

        if (block instanceof Mine) {
            block.makeVisible();
            endGame();
        } else {
            // reveal empty space, and if block is adjacent to 0 mines,
            // reveal adjacent spaces
            revealEmptySpace(r, c);

            // check that all empty squares have been clicked on

            if (foundEmpties + numMines == getWidth() * getHeight()) {
                revealMines();
                endGame();
            }
        }

        update();
    }

    private void revealEmptySpace(int r, int c) {
        if (inBounds(r, c) && !getBlockAt(r, c).isVisible()) {
            getBlockAt(r, c).makeVisible();
            foundEmpties++;

            if (((Empty)getBlockAt(r, c)).getAdjacentMines() == 0) {
                revealEmptySpace(r - 1, c);
                revealEmptySpace(r + 1, c);
                revealEmptySpace(r, c - 1);
                revealEmptySpace(r, c + 1);
            }
        }
    }

    /**
     * Makes all the mines on this board visible.
     */
    private void revealMines() {
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                if (getBlockAt(r, c) instanceof Mine) {
                    getBlockAt(r, c).makeVisible();
                }
            }
        }
    }

    /**
     * Returns the block at the given position on this board.
     *
     * @param r a row on this board
     * @param c a column on this board
     * @return the block at that position
     */
    protected MineSweeperBlock getBlockAt(int r, int c) {
        return (MineSweeperBlock)(getPieceAt(r, c));
    }

    /**
     * Returns a printable representation of this board.
     *
     * @return a printable represenation of this board
     */
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                if (!getBlockAt(r, c).isVisible()) {
                    result.append('.');
                } else {
                    if (getBlockAt(r, c) instanceof Mine) {
                        result.append('*');
                    } else {
                        int adjacentMines = ((Empty)getBlockAt(r, c)).getAdjacentMines();
                        if (adjacentMines > 0) {
                            result.append(String.valueOf(adjacentMines));
                        } else {
                            result.append(' ');
                        }
                    }
                }
            }
            result.append('\n');
        }

        return result.toString();
    }

    public static void main(String[] args) {
        MineSweeperBoard b = new MineSweeperBoard();
        b.makeMove(0, 0);

        System.out.println(b);
    }
}
