package javay.game.minesweeper;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * 控制
 * A controller for a board game.  This controller handles clicks and drags
 * on the board.  Because the display of the board is handled by the view,
 * there is a mechanism through which the controller and view communicate:
 * the view forwards mouse events to the controller, which must ask
 * the view to translate the pixel coordinates to board coordinates.  In
 * the case of a drag, the controller must also tell the view to display
 * the status of the drag.
 *
 * @author Jim Glenn
 * @version 0.1 1/16/2007 mostly factored out of v0.1 of GameBoardView
 */
public class GameController implements MouseListener, MouseMotionListener {
    /**
     * A flag that is set (<CODE>true</CODE>) iff we are in the middle
     * of a drag event.
     */

    private boolean dragging;

    /**
     * Values that keep track of drag events before they are dropped.
     */

    private int dragStartRow;
    private int dragStartCol;
    private int dragCurrentRow;
    private int dragCurrentCol;

    /**
     * The view and model this controller is associated with.
     */

    private GameBoardView view;
    private GameBoardModel model;

    /**
     * Creates a new controller associated with the given model and view.
     *
     * @param v the view the new controller will be associated with
     * @param m the model the new controller will be associated with
     */

    public GameController(GameBoardView v, GameBoardModel m)
    {
    view = v;
    model = m;

    // how can we be in the middle of a drag if we just made this?

    dragging = false;
    }

    /**
     * Processes mouse clicks.
     *
     * @param e a mouse event representing a click
     */
    public void mouseClicked(MouseEvent e) {
        dragging = false;

        int clickRow = view.translateY(e.getY());
        int clickCol = view.translateX(e.getX());

        // check the model to see if the move is legal, and if it is then
        // make the move
        if (model.isLegalMove(clickRow, clickCol)) {
            model.makeMove(clickRow, clickCol);
        }
    }

    /**
     * Processes mouse releases.  Mouse releases are only handled if
     * they signal the end of a drag operation, not if they signal the
     * end of a click.
     *
     * @param e a mouse event representing a release
     */

    public void mouseReleased(MouseEvent e)
    {
    if (dragging)
        {
        int dragEndRow = view.translateY(e.getY());
        int dragEndCol = view.translateX(e.getX());

        if (model.isLegalMove(dragStartRow, dragStartCol, dragEndRow, dragEndCol))
            model.makeMove(dragStartRow, dragStartCol, dragEndRow, dragEndCol);
        }

    dragging = false;
    }

    /**
     * Handles the beginning and middle of drag operations.  The
     * end of a drag is handled by the object that receives the final
     * mouse release event.
     *
     * @param e a mouse event representing a drag
     */

    public void mouseDragged(MouseEvent e)
    {
    if (!dragging)
        {
        dragStartRow = view.translateY(e.getY());
        dragStartCol = view.translateX(e.getX());
        dragCurrentRow = dragStartRow;
        dragCurrentCol = dragStartCol;

        dragging = true;
        }
    else
        {
        int newCurrentRow = view.translateY(e.getY());
        int newCurrentCol = view.translateX(e.getX());

        if (newCurrentRow != dragCurrentRow
            || newCurrentCol != dragCurrentCol)
            {
            dragCurrentRow = newCurrentRow;
            dragCurrentCol = newCurrentCol;

            view.repaint();
            }
        }
    }

    /**
     * Handles mouse moved events.  This implementation callously ignores
     * such events.
     */

    public void mouseMoved(MouseEvent e)
    {
    }

    /**
     * Handles mouse entered events.  Stop bothering me with all of these
     * events!  I don't care about them!
     */

    public void mouseEntered(MouseEvent e)
    {
    }

    /**
     * Handles (really, ignores) mouse exited events.
     */

    public void mouseExited(MouseEvent e)
    {
    }

    /**
     * Multiple inheritance would really come in handy here.
     */

    public void mousePressed(MouseEvent e)
    {
    }

    /**
     * Updates the view associated with this controller to show the
     * status of the current drag operation.  This implementation checks
     * the model to determine if the drag represents a legal move and,
     * if the move is legal, forwards the display request to the view
     * via its <CODE>drawDraggedPiece</CODE> method.
     *
     * @param g the graphics context to draw in
     */

    public void displayDragStatus(Graphics g)
    {
    if (dragging &&
        (dragStartRow != dragCurrentRow || dragStartCol != dragCurrentCol)
        && model.isLegalMove(dragStartRow, dragStartCol, dragCurrentRow, dragCurrentCol))
        {
        view.drawDraggedPiece(g, dragStartRow, dragStartCol, dragCurrentRow, dragCurrentCol);
        }
    }
}
