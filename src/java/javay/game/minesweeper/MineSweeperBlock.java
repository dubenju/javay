package javay.game.minesweeper;

import java.awt.Color;

/**
 * A block on a Mine Sweeper board.  Blocks are either visible or not.
 *
 * @author Jim Glenn
 * @version 0.1 1/16/2007
 */

public abstract class MineSweeperBlock extends GamePiece
{
    /**
     * A flag that is true iff this block is visible.
     */

    private boolean visible;

    /**
     * Creates an unrevealed block of the given color.
     *
     * @param c a color
     */

    public MineSweeperBlock(Color c)
    {
	super(c);

	visible = false;
    }

    /**
     * Reveals this block.
     */

    public void makeVisible()
    {
	visible = true;
    }

    /**
     * Determines if this block has been revealed.
     *
     * return true iff this block has been revealed
     */

    public boolean isVisible()
    {
	return visible;
    }
}
