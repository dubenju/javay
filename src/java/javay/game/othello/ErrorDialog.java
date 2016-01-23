package javay.game.othello;

import java.awt.Button;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class ErrorDialog extends JDialog {
  public ErrorDialog(JFrame parent, String all[]) {
    this(parent, all, null);
  }

  public ErrorDialog(JFrame parent, String all[], String msg) {
    super(parent, "", true);
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < all.length; i ++) {
      sb.append(all[i]);
      sb.append('\n');
    }
    if (msg != null) {
      sb.append(msg);
    }
    setup(parent, sb.toString());
  }

  public ErrorDialog(JFrame parent, String message) {
    super(parent, "", true);
    setup(parent, message);
  }

  private void setup(JFrame parent, String message) {
    this.getContentPane().setLayout(new GridBagLayout());
    int row = 0, col = 0, i, width = 0;
    Font font = new Font("Serif", Font.PLAIN, 16);
    char c = ' ';
    for (i = 0; i < message.length(); i ++) {
      c = message.charAt(i);
      if (c == '\n') {
         row ++;
         if (width > col) {
           col = width;
         }
         width = 0;
      } else if (c == '\t') {
        width += 7 - width % 7;
      } else {
        if (c > 0x00FF) {
          width += 2;
        } else {
          width ++;
        }
      }
    }
    if (c != '\n') {
       row ++;
       if (width > col) {
         col = width;
       }
    }
    col ++;

    row = row > 24 ? 24 : row;
    if (row < 5) {
      row = 5;
    }
    if (col < 20) {
      col = 20;
    }
    TextArea tx = new TextArea(message, row, col);
    tx.setEditable(false);
    tx.setFont(font);
    AddConstraint.addConstraint(this.getContentPane(), tx, 0, 0, 1, 1,
      GridBagConstraints.BOTH,
      GridBagConstraints.NORTHWEST,
      1, 1, 0, 0, 0, 0);
    Button b = new Button("确定");
    b.setFont(font);
    AddConstraint.addConstraint(this.getContentPane(), b, 0, 1, 1, 1,
      GridBagConstraints.HORIZONTAL,
      GridBagConstraints.CENTER,
      1, 0, 0, 0, 0, 0);
    CloseWindow cw = new CloseWindow(this);
    this.addWindowListener(cw);
    b.addActionListener(cw);
    pack();
    setVisible(true);
  }
}
