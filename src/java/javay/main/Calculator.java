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
        // frame.setLayout(new BorderLayout());
        frame.setBounds(600, 50, 560, 360);
        frame.setLocation(600, 50);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
