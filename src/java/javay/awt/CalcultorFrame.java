/**
 *
 */
package javay.awt;

import java.awt.BorderLayout;
//import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

import javay.swing.CalcultorPanel;

/**
 * @author dubenju
 *
 */
public class CalcultorFrame extends Frame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @throws HeadlessException
	 */
	public CalcultorFrame() throws HeadlessException {
        MenuBar  menuBar      = new MenuBar();
        Menu     menuFile     = new Menu();
        MenuItem menuFileExit = new MenuItem();

        menuFile.setLabel("File");
        menuFileExit.setLabel("Exit");

        // Add action listener.for the menu button
        menuFileExit.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CalcultorFrame.this.windowClosed();
                }
            }
        );
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);

        setTitle("计算器");
        setMenuBar(menuBar);
        // setSize(new Dimension(400, 400));

        // Add window listener.
        this.addWindowListener (
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    CalcultorFrame.this.windowClosed();
                }
            }
        );

        JPanel panel=new CalcultorPanel();
        add(panel, BorderLayout.CENTER);
	}

	/**
     * Shutdown procedure when run as an application.
     */
    protected void windowClosed() {
    	// Exit application.
        System.exit(0);
    }
}
