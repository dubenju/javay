package javay.game.othello;

import java.awt.MenuItem;

import javay.game.AbstractGameApp;

public class Othello extends AbstractGameApp {
  private OX oxBoard;
  private MenuItem black, white;

  private Othello() {
    super("Othello");

    oxBoard = new OX(this);
    add(oxBoard); // Container

  }

  public static void main(String argv[]) {
    new Othello();
  }
  public void test() {
    oxBoard.setWhitePlayer(1);
    white.setLabel("ˇ电脑执白");
  }
}
