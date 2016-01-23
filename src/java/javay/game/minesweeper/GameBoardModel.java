package javay.game.minesweeper;

import java.awt.Color;

/**
 * A model of a 2-D grid-based game board.
 *
 * @author Jim Glenn
 * @verion 0.1 9/1/2004
 */

public class GameBoardModel
{
  /**
   * Stores what game piece is at each position.
   */
  
  protected GamePiece[][] pieces;

  /**
   * The view that should be notified of changes in this model.
   */

  protected GameBoardView view;

  /**
   * A flag that indicates whether this game is over.
   */

  private boolean gameOver;

  /**
   * Creates an empty board of the given size.
   *
   * @param w the width, in squares, of this new board
   * @param h the height, in squares, of this new board
   */

  public GameBoardModel(int w, int h)
  {
  pieces = new GamePiece[h][w];

  view = null;

  gameOver = false;
  }

  /**
   * Registers a view that will be notified of changes to this model.
   *
   * @param v a view
   */

  public void setView(GameBoardView v)
  {
  view = v;
  }

  /**
   * Returns the color of the board at the specified position.
   * This implementation colors the board in a black and red checkerboard
   * pattern.
   *
   * @param r the row
   * @param c the column
   * @return the color of the board at row <CODE>r</CODE>, column
   * <CODE>c</CODE>
   */

  public Color getBoardColor(int r, int c)
  {
  if (r % 2 == c % 2)
    return Color.RED.darker();
  else
    return Color.BLACK;
  }

  /**
   * Returns the piece at the specified position, or <CODE>null</CODE>
   * if that position is empty.
   *
   * @param r the row
   * @param c the column
   * @return the piece at row <CODE>r</CODE>, column <CODE>c</CODE>
   */

  public GamePiece getPieceAt(int r, int c)
  {
  return pieces[r][c];
  }
 
  /**
   * Returns the width of this board.
   *
   * @return the width
   */

  public int getWidth()
  {
  return pieces[0].length;
  }

  /**
   * Returns the height of this board.
   *
   * @return the height
   */

  public int getHeight()
  {
  return pieces.length;
  }

  /**
   * Determines if the given position is on this board.
   *
   * @param r the row
   * @param c the column
   * @return true iff that row and column is on the board
   */

  protected boolean inBounds(int r, int c)
  {
  return (r >= 0 && c >= 0 && r < getHeight() && c < getWidth());
  }

  /**
   * Determines if the given move is legal.  This implementation
   * disallows all single-space moves.
   *
   * @param r the row of the move
   * @param c the column of the move
   */

  public boolean isLegalMove(int r, int c)
  {
  return false;
  }

  /**
   * Determines if the given move is legal.  This implementation
   * allows any piece to move to any location on the board.
   *
   * @param fromR the starting row of the piece to be moved
   * @param fromC the starting column of the piece to be moved
   * @param toR the ending row
   * @param toC the ending column
   * @return true iff the move is legal
   */

  public boolean isLegalMove(int fromR, int fromC, int toR, int toC)
  {
  // check that the game is not over

  if (isOver())
    return false;

  // check if all spaces are on the board

  if (!inBounds(fromR, fromC) || !inBounds(toR, toC))
    return false;

  // check that there is a piece at the starting position

  GamePiece piece = getPieceAt(fromR, fromC);
  if (piece == null)
    return false;

  return true;
  }

  /**
   * Updates the board to reflect the results of the given move.
   * The move must be legal.  This implementation does nothing.
   *
   * @param r the row of the move
   * @param c the column of the move
   */

  public void makeMove(int r, int c)
  {
  update();
  }

  /**
   * Updates the board to reflect the results of the given move.
   * The move must be legal.  This implementation moves a piece
   * from the from position to the to position, removing any piece
   * that was previously at the to position.
   *
   * @param fromR the row to move from
   * @param fromC the column to move from
   * @param toR the row to move to
   * @param toC the column to move to
   */

  public void makeMove(int fromR, int fromC, int toR, int toC)
  {
  movePiece(fromR, fromC, toR, toC);

  update();
  }

  /**
   * Notifies the view associated with this model that the model had
   * changed and the view should be redrawn.
   */

  protected void update()
  {
  if (view != null)
    view.updateView();
  }

  /**
   * Moves the piece at the first position to the second position,
   * leaving the first empty.  Both positions must be valid.
   *
   * @param fromR the row to move from
   * @param fromC the column to move from
   * @param toR the row to move to
   * @param toC the column to move to
   */

  protected void movePiece(int fromR, int fromC, int toR, int toC)
  {
  pieces[toR][toC] = pieces[fromR][fromC];
  pieces[fromR][fromC] = null;
  }

  /**
   * Removes the piece at the given position, leaving that position empty.
   * The position must be valid.
   *
   * @param row the row
   * @param col the column
   */

  protected void removePiece(int row, int col)
  {
  pieces[row][col] = null;
  }

  /**
   * Tells whether this game is over.
   */

  public boolean isOver()
  {
  return gameOver;
  }

  /**
   * Ends this game.
   */

  public void endGame()
  {
  gameOver = true;
  }
}
