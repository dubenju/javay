package javay.game.othello;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * WindowAdapter implements the WindowLister interface
 * We extends WindowAdapter to reduce the line numer of code
 * @author DBJ
 *
 */
public class CloseWindow extends WindowAdapter implements ActionListener {
  private Window target;
  private boolean exit;

  public CloseWindow(Window target, boolean exit) {
    this.target = target;
    this.exit = exit;
  }
  public CloseWindow(Window target) {
    this.target = target;
  }

  public void windowClosing(WindowEvent e) {
    target.dispose();
    if (exit) {
      System.exit(0);
    }
  }

  public void actionPerformed(ActionEvent e) {
    target.dispose();
    if (exit) {
      System.exit(0);
    }
  }
}
