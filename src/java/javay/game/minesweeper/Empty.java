package javay.game.minesweeper;

import java.awt.Color;

/**
 * An empty block on a Mine Sweeper board.  Empty blocks record the number
 * of adjacent mines.
 *
 * @author Jim Glenn
 * @version 0.1 1/16/2007
 */

public class Empty extends MineSweeperBlock
{
    /**
     * The number of mines adjacent to this empty block.
     */

    private int mines;

    /**
     * The colors to draw the numbers in.  1 would be drawn in color
     * <CODE>numberColors[1]</CODE>.
     */

    private static final Color[] numberColors
	= {BLACK, BLUE, GREEN, RED, PURPLE, ORANGE, YELLOW, BROWN, WHITE, PINK};

    /**
     * Creates an unrevealed empty block adjacent to the given number of
     * mines.
     *
     * @param m the number of mines adjacent to the new empty block
     */

    public Empty(int m)
    {
	super(numberColors[m]);

	mines = m;
    }

    /**
     * Returns the number of mines adjacent to this empty block.
     *
     * @return the number of mines adjacent to this empty block
     */

    public int getAdjacentMines()
    {
	return mines;
    }
}

