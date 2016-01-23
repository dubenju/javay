package javay.game.minesweeper;

import java.awt.Color;

/**
 * A colored game piece.
 *
 * @author Jim Glenn
 * @version 0.1 9/23/2004
 */

public class GamePiece
{
  /**
   * The color of this piece.
   */

  private Color color;

  /**
   * Constants for colors.
   */

  public static final Color RED = Color.red;
  public static final Color BLACK = Color.black;
  public static final Color BLUE = Color.blue;
  public static final Color WHITE = Color.white;
  public static final Color GREEN = Color.green;
  public static final Color YELLOW = Color.yellow;
  public static final Color ORANGE = Color.orange;
  public static final Color PURPLE = Color.magenta;
  public static final Color BROWN = Color.yellow.darker();
  public static final Color PINK = new Color(255, 192, 192);

  /**
   * Creates a black game piece.
   */

  public GamePiece()
  {
  this(BLACK);
  }

  /**
   * Creates a new piece of the given color.
   *
   * @param c the color of the new piece
   */

  public GamePiece(Color c)
  {
  color = c;
  }

  /**
   * Returns the color of this piece.
   *
   * @return the color of this piece
   */

  public Color getColor()
  {
  return color;
  }

  public boolean isLegalMove(GameBoardModel board, int fromR, int fromC, int toR, int toC)
  {
  return true;
  }

  public void updateBoard(GameBoardModel board, int fromR, int fromC, int toR, int toC)
  {
  }
}
