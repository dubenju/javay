package javay.game.minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * 视图
 * A view of a Mine Sweeper board.
 *
 * @author Jim Glenn
 * @version 0.1 1/16/2007
 */
public class MineSweeperView extends GameBoardView {
    /**
     * Creates a view of the given board.
     *
     * @param m the board to view
     */
    public MineSweeperView(MineSweeperBoard m) {
    	super(m);
    }

    /**
     * Returns the model associated with this view as a
     * <CODE>MineSweeperBoard</CODE>.
     *
     * @return the model associated with this view
     */

    protected MineSweeperBoard getModel()
    {
	return (MineSweeperBoard)model;
    }

    /**
     * Paints the given piece at the given position.
     *
     * @param g the graphics contect to draw on
     * @param piece the piece to draw
     * @param x the x-position to draw at
     * @param y the y-position to draw at
     * @param size the size of the square to draw in
     */
    protected void drawPiece(Graphics g, GamePiece p, int x, int y, int size)
    {
	MineSweeperBlock block = (MineSweeperBlock)p;

	if (block.isVisible())
	    {
		if (block instanceof Mine)
		    {
			drawMine(g, x, y, size, block.getColor());
		    }
		else
		    {
			int mines = ((Empty)(block)).getAdjacentMines();

			if (mines > 0)
			    {
				drawNumber(g, x, y, size, mines, block.getColor());
			    }
		    }
	    }
	else
	    {
		drawUnrevealed(g, x, y, size);
	    }
    }

    /**
     * Draws an unrevealed square at the given location.
     *
     * @param g the graphics contect to draw on
     * @param piece the piece to draw
     * @param x the x-position to draw at
     * @param y the y-position to draw at
     * @param size the size of the square to draw in
     */

    private void drawUnrevealed(Graphics g, int x, int y, int size)
    {
	// draw a dark gray border around the square

	g.setColor(Color.DARK_GRAY);

	g.drawRect(x, y, size - 1, size - 1);
	g.drawRect(x + 1, y + 1, size - 3, size - 3);
    }

    /**
     * Draws a mine at the given location.
     *
     * @param g the graphics contect to draw on
     * @param piece the piece to draw
     * @param x the x-position to draw at
     * @param y the y-position to draw at
     * @param size the size of the square to draw in
     * @param c the color to draw in
     */

    private void drawMine(Graphics g, int x, int y, int size, Color c)
    {
	g.setColor(c);

	int centerX = x + size / 2;
	int centerY = y + size / 2;

	g.fillOval(centerX - size / 4, centerY - size / 4, size / 2, size / 2);
	g.drawLine(x + 1, centerY, x + size - 2, centerY);
	g.drawLine(centerX, y + 1, centerX, y + size - 2);
	g.drawLine(x + 3, y + 3, x + size - 4, y + size - 4);
	g.drawLine(x + size - 4, y + 3, x + 3, y + size - 4);
    }

    /**
     * Draws the given number at the given location.
     * @param g the graphics contect to draw on
     * @param piece the piece to draw
     * @param x the x-position to draw at
     * @param y the y-position to draw at
     * @param size the size of the square to draw in
     * @param num the number to draw
     * @param c the color to draw in
     */

    private void drawNumber(Graphics g, int x, int y, int size, int num, Color c)
    {
	g.setColor(c);

	g.setFont(new Font("SansSerif", Font.BOLD, size - 2));
	g.drawString("" + num, x + 1, y + size - 2);
    }
}
