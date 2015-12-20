/**
 *
 */
package javay.main;

import javay.awt.CalcultorFrame;

/**
 * @author dubenju
 *
 */
public class Calculator {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // create application frame
        CalcultorFrame frame = new CalcultorFrame();

        //show frame
//        frame.setUndecorated(true);
//        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.setBounds(600, 50, 900, 360);
        frame.setLocation(600, 50);

        frame.setLocationByPlatform(true);
        frame.setLocationRelativeTo(null);
//        frame.pack();

        frame.setFocusable(true);
        frame.requestFocusInWindow();

        frame.setResizable(false);
        frame.setVisible(true);
//        KeyAdapter keyAdapter = new CalcultorKeyAdapter(null);
//        frame.addKeyListener(keyAdapter);

    }

}
