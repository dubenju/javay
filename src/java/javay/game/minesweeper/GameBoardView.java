package javay.game.minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * Swing components that display <CODE>GameBoardModel</CODE>s.
 * These components respond to mouse clicks by sending a
 * <CODE>GameBoardEvent</CODE> to their listeners.
 *
 * @author Jim Glenn
 * @version 0.2 Separates controller better
 */

public class GameBoardView extends JComponent
{
  /**
   * The game board this view displays.
   */

  protected GameBoardModel model;

  /**
   * The controller this view forwards events to.
   */

  protected GameController control;

  /**
   * Creates a new view that displays the given game board.
   *
   * @param m the game board this new view will display
   */

  public GameBoardView(GameBoardModel m)
  {
  model = m;
  control = null;

  // set preferred size so stupid layouts won't squeeze us

  setPreferredSize(new Dimension(300, 300));
  }

  /**
   * Sets the controller this view is associated with.  This controller
   * will forward mouse events to the specified controller.
   *
   * @param c a game controller
   */

  public void setController(GameController c)
  {
  control = c;

  // we should probably set something up that translates the events
  // from pixel coordinates to board coordinates before they get
  // to the controller, but we're a little lazy

  addMouseListener(c);
  addMouseMotionListener(c);
  }

  /**
   * Computes the size of each grid square in the current view.
   *
   * @return the size of each grid square in the current view.
   */

  private int getSquareSize()
  {
  int squareW = getWidth() / model.getWidth();
  int squareH = getHeight() / model.getHeight();

  return Math.min(squareW, squareH);
  }

  /**
   * Translates pixel coordinates to board coordinates.
   *
   * @param x the x coordinate of a pixel in this view
   * @return the corresponding board column
   */

  public int translateX(int x)
  {
  return x / getSquareSize();
  }

  /**
   * Translates pixel coordinates to board coordinates.
   *
   * @param y the y coordinate of a pixel in this view
   * @return the corresponding board column
   */

  public int translateY(int y)
  {
  return y / getSquareSize();
  }

  /**
   * Invoked by the model to notify this view that it should be redrawn.
   */

  public void updateView()
  {
  repaint();
  }

  /**
   * Paints the view of the model.
   *
   * @param g the graphics context to draw on
   */

  public void paint(Graphics g)
  {
  // figure out the size (pixels) of each square in the grid

  int squareSize = getSquareSize();

  // paint the board

  for (int r = 0; r < model.getHeight(); r++)
    for (int c = 0; c < model.getWidth(); c++)
    {
      Color board = model.getBoardColor(r, c);

      g.setColor((Color)board);
      g.fillRect(c * squareSize, r * squareSize,
           squareSize, squareSize);

      GamePiece piece = model.getPieceAt(r, c);
      if (piece != null)
      {
        drawPiece(g, piece, c * squareSize, r * squareSize, squareSize);
      }
    }

  if (control != null)
    control.displayDragStatus(g);
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

  protected void drawPiece(Graphics g, GamePiece piece, int x, int y, int size)
  {
  g.setColor(piece.getColor());

  g.fillOval(x + 1, y + 1, size - 2, size - 2);
  }

  /**
   * Paints the outline of the given piece at the given position.
   *
   * @param g the graphics contect to draw on
   * @param piece the piece to draw
   * @param x the x-position to draw at
   * @param y the y-position to draw at
   * @param size the size of the square to draw in
   */

  protected void drawGhost(Graphics g, GamePiece piece, int x, int y, int size)
  {
  g.setColor(piece.getColor());

  g.drawOval(x + 1, y + 1, size - 2, size - 2);
  }

  /**
   * Paints something that reflects the status of the current drag operation.
   * This is intended to give the user some visual feedback about what
   * will happen when the piece is dropped.
   *
   * @param g the graphics context to draw in
   * @param dragStartRow the row containing the piece being dragged
   * @param dragStartCol the column containing the piece being dragged
   * @param dragEndRow the row the piece is being dragged to
   * @param dragEndCol the column the piece is being dragged to
   */

  public void drawDraggedPiece(Graphics g, int dragStartRow, int dragStartCol, int dragCurrentRow, int dragCurrentCol)
  {
  GamePiece moving = model.getPieceAt(dragStartRow, dragStartCol);
  g.setColor(moving.getColor());

  int size = getSquareSize();
  int x = dragCurrentCol * size;
  int y = dragCurrentRow * size;

  drawGhost(g, moving, x, y, size);
  }
}
