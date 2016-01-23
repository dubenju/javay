package javay.game.minesweeper;

/*

<APPLET WIDTH=400 HEIGHT=400 CODE="MineSweeperApplet.class"></APPLET>
 */

import java.awt.BorderLayout;

import javax.swing.JApplet;

public class MineSweeperApplet extends JApplet
{
  public void init()
  {
  getContentPane().setLayout(new BorderLayout());

  MineSweeperBoard model = new MineSweeperBoard();
  GameBoardView view = new MineSweeperView(model);
  GameController control = new GameController(view, model);
  view.setController(control);
  model.setView(view);

  getContentPane().add(view, BorderLayout.CENTER);
  }
}
