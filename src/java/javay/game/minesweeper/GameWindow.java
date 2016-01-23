package javay.game.minesweeper;

import java.awt.FlowLayout;

import javax.swing.JFrame;

public class GameWindow extends JFrame
{
  public GameWindow()
  {
  getContentPane().setLayout(new FlowLayout());

  GameBoardModel model = new GameBoardModel(8, 8);
  GameBoardView view = new GameBoardView(model);
  GameController control = new GameController(view, model);
  view.setController(control);
  model.setView(view);

  getContentPane().add(view);

  setSize(400, 400);
  setVisible(true);

  setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  public static void main(String[] args)
  {
  new GameWindow();
  }
}
