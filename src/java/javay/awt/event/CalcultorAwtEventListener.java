package javay.awt.event;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

import javay.swing.CalcultorPanel;

public class CalcultorAwtEventListener implements AWTEventListener {

  private CalcultorPanel panel;

  public CalcultorAwtEventListener(CalcultorPanel panel) {
    this.panel = panel;
  }

  @Override
  public void eventDispatched(AWTEvent event) {
    if (event.getClass() == KeyEvent.class) {
      KeyEvent ke = ((KeyEvent) event);
      if (ke.getID() == KeyEvent.KEY_PRESSED) {
        int kc = ke.getKeyCode();

        System.out.println("KeyCode=" + kc);
        switch (kc) {
          case KeyEvent.VK_NUMPAD0:
          case KeyEvent.VK_NUMPAD1:
          case KeyEvent.VK_NUMPAD2:
          case KeyEvent.VK_NUMPAD3:
          case KeyEvent.VK_NUMPAD4:
          case KeyEvent.VK_NUMPAD5:
          case KeyEvent.VK_NUMPAD6:
          case KeyEvent.VK_NUMPAD7:
          case KeyEvent.VK_NUMPAD8:
          case KeyEvent.VK_NUMPAD9:
          case KeyEvent.VK_0:
          case KeyEvent.VK_1:
          case KeyEvent.VK_2:
          case KeyEvent.VK_3:
          case KeyEvent.VK_4:
          case KeyEvent.VK_5:
          case KeyEvent.VK_6:
          case KeyEvent.VK_7:
          case KeyEvent.VK_8:
          case KeyEvent.VK_9: {
            String str = this.panel.textField.getText();
            if (str.equals("0") || str.equals("0.0")) {
              str = "";
            }
            str = str + "" + (kc - '0');
            this.panel.textField.setText(str);
            break ;
          }
          case KeyEvent.VK_A:
          case KeyEvent.VK_B:
          case KeyEvent.VK_C:
          case KeyEvent.VK_D:
          case KeyEvent.VK_E:
          case KeyEvent.VK_F: {
            break ;
          }
          case KeyEvent.VK_PERIOD: {
            // .
            String str = this.panel.textField.getText();
            if (str.indexOf(".") < 0) {
              if (str.equals("0")) {
                str = "0.0";
              } else {
                str = str + ".";
              }
              this.panel.textField.setText(str);
            }
            break ;
          }
          case KeyEvent.VK_PLUS: {
            // +
            break ;
          }
          case KeyEvent.VK_SUBTRACT: {
            // -
            break ;
          }
          case KeyEvent.VK_MULTIPLY: {
            // *
            break ;
          }
          case KeyEvent.VK_DIVIDE: {
            // /
            break ;
          }
          case KeyEvent.VK_ENTER :
          case KeyEvent.VK_EQUALS: {
            // =
            break ;
          }
          case KeyEvent.VK_LEFT_PARENTHESIS: {
            // (
            break ;
          }
          case KeyEvent.VK_RIGHT_PARENTHESIS: {
            // )
            break ;
          }
          default:
            break;
        }
      }
    }
  }
}
