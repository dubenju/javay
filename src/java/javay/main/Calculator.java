package javay.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Image;

import javax.swing.ImageIcon;

import javay.swing.CalcultorFrame;

/**
 * Calculator.
 * @author dubenju
 *
 */
public class Calculator {
  public static final Image LOG = new ImageIcon(
      CalcultorFrame.class.getResource("/select.png"))
      .getImage();
  private static final Logger log = LoggerFactory.getLogger(Calculator.class);

  /**
   * main.
   * @param args String[]
   */
  public static void main(String[] args) {
    log.debug("----- begin -----");
    // -Dlogback.configurationFile=conf/logback.xml
    createAndShowGui();
    log.debug("-----   end -----");
  }

  /**
   * createAndShowGui.
   */
  public static void createAndShowGui() {
    // create application frame
    CalcultorFrame frame = new CalcultorFrame();
    frame.setIconImage(LOG);
    //show frame
    frame.setBounds(600, 50, 600, 480);

    frame.setLocationByPlatform(true);
    frame.setLocationRelativeTo(null);

    frame.setFocusable(true);
    frame.requestFocusInWindow();

    frame.setResizable(false);
    frame.setVisible(true);
  }
}
