/**
 *
 */
package javay.swing;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javay.awt.event.CalcultorAWTEventListener;

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
	public CalcultorFrame() {
        JMenuBar  menuBar      = new JMenuBar();
        JMenu     menuFile     = new JMenu("文件");
        JMenuItem menuFileExit = new JMenuItem("退出", KeyEvent.VK_X);

        // Add action listener.for the menu button
        menuFileExit.addActionListener (
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CalcultorFrame.this.windowClosed();
                }
            }
        );
        JDialog aboutDialog=new JDialog(); 
        aboutDialog.setTitle("关于"); 

        Container con=aboutDialog.getContentPane();
        Icon icon=new ImageIcon("army.jpg"); 
        JLabel aboutLabel=new JLabel("<html><b><font size=5>"+"<center>Swing!"+"<br>",icon,JLabel.CENTER); 
        con.add(aboutLabel,BorderLayout.CENTER); 
        aboutDialog.setSize(450,225); 
        aboutDialog.setLocation(300,300); 
        aboutDialog.addWindowListener(new WindowAdapter() {
            @SuppressWarnings("unused")
			public void WindowClosing(WindowEvent e){ 
                dispose();
           } 
        }); 

        menuFile.add(menuFileExit);
        menuBar.add(menuFile);

        JMenu menuEdit = new JMenu("编辑");
        JMenuItem menuEditCopy = new JMenuItem("复制");
        JMenuItem menuEditPaste = new JMenuItem("粘贴");
        menuEdit.add(menuEditCopy);
        menuEdit.add(menuEditPaste);
        menuBar.add(menuEdit);

        JMenu menuView = new JMenu("视图");
        JMenuItem options = new JMenuItem("选项",KeyEvent.VK_O);
        menuView.add(options);
        menuBar.add(menuView);

        JMenu menuHelp = new JMenu("帮助");
        JMenuItem checkUpdate = new JMenuItem("检查更新",KeyEvent.VK_U);
        JMenuItem aboutMenuItem=new JMenuItem("关于..",KeyEvent.VK_A);
        aboutMenuItem.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent e){ 
             aboutDialog.setVisible(true); 
            } 
           });
        menuHelp.add(checkUpdate);
        menuHelp.add(aboutMenuItem);
        menuBar.add(menuHelp);

        setTitle("计算器");
        setJMenuBar(menuBar);

        // Add window listener.
        this.addWindowListener (
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    CalcultorFrame.this.windowClosed();
                }
            }
        );

        CalcultorPanel panel = new CalcultorPanel();
        getContentPane().add(panel, BorderLayout.CENTER);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // 注册应用程序全局键盘事件, 所有的键盘事件都会被此事件监听器处理.  
        toolkit.addAWTEventListener( new CalcultorAWTEventListener(panel), AWTEvent.KEY_EVENT_MASK);
	}

	/**
     * Shutdown procedure when run as an application.
     */
    protected void windowClosed() {
    	// Exit application.
        System.exit(0);
    }
}
