package javay.main;

import java.awt.Image;

import javax.swing.ImageIcon;

import javay.swing.SoundFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sound {
    public static final Image LOG = new ImageIcon(SoundFrame.class.getResource("/select.png")).getImage();
    private static final Logger log = LoggerFactory.getLogger(Sound.class);
    public static void main(String[] args) {
	System.out.println(System.currentTimeMillis());
	System.out.println(System.nanoTime());
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
        SoundFrame frame = new SoundFrame();
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
