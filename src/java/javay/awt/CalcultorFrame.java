/**
 *
 */
package javay.awt;

import java.awt.AWTEvent;
import java.awt.HeadlessException;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import javay.awt.event.CalcultorAWTEventListener;
import javay.swing.CalcultorPanel;

/**
 * @author dubenju
 *
 */
public class CalcultorFrame extends JFrame {

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

        CalcultorPanel panel = new CalcultorPanel();
//        add(panel, BorderLayout.CENTER);
        getContentPane().add(panel);
//
//        KeyAdapter keyAdapter = new CalcultorKeyAdapter(panel);
//        addKeyListener(keyAdapter);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // 注册应用程序全局键盘事件, 所有的键盘事件都会被此事件监听器处理.  
        toolkit.addAWTEventListener( new CalcultorAWTEventListener(panel), AWTEvent.KEY_EVENT_MASK);  

//        panel.setFocusable(true);
//        panel.requestFocusInWindow();
	}

	/**
     * Shutdown procedure when run as an application.
     */
    protected void windowClosed() {
    	// Exit application.
        System.exit(0);
    }
}
