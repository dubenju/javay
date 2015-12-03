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
		frame.setBounds(600, 50, 640, 480);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocation(600, 50);
	}

}
