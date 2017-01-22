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

import javay.awt.event.CalcultorAwtEventListener;
import javay.awt.event.SoundAwtEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoundFrame extends JFrame {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(CalcultorFrame.class);
    public SoundFrame() {
	    log.debug("----- begin -----");
	    final JMenuBar  menuBar    = new JMenuBar();
	    final JMenu   menuFile   = new JMenu("文件");
	    JMenuItem menuFileExit = new JMenuItem("退出", KeyEvent.VK_X);

	    // Add action listener.for the menu button
	    menuFileExit.addActionListener(
	        new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	          log.info("----- 文件 > 退出");
	          SoundFrame.this.windowClosed();
	        }
	      }
	    );
	    JDialog aboutDialog = new JDialog();
	    aboutDialog.setTitle("关于");

	    Container con = aboutDialog.getContentPane();
	    Icon icon = new ImageIcon("army.jpg");
	    JLabel aboutLabel = new JLabel("<html><b><font size=5><center>Swing!<br>",icon,JLabel.CENTER);
	    con.add(aboutLabel,BorderLayout.CENTER);
	    aboutDialog.setSize(450,225);
	    aboutDialog.setLocation(300,300);
	    aboutDialog.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent ev) {
	        dispose();
	      }
	    });

	    JSettingDlg settingDlg = new JSettingDlg(this);
	    menuFile.add(menuFileExit);
	    menuBar.add(menuFile);

	    JMenu menuHelp = new JMenu("帮助");
	    JMenuItem helpHelp = new JMenuItem("帮助",KeyEvent.VK_H);
	    JMenuItem checkUpdate = new JMenuItem("检查更新",KeyEvent.VK_U);
	    JMenuItem aboutMenuItem = new JMenuItem("关于..",KeyEvent.VK_A);
	    aboutMenuItem.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ev) {
	        aboutDialog.setVisible(true);
	      }
	    });
	    menuHelp.add(helpHelp);
	    menuHelp.add(checkUpdate);
	    menuHelp.add(aboutMenuItem);
	    menuBar.add(menuHelp);

	    setTitle("音乐播放器");
	    setJMenuBar(menuBar);

	    // Add window listener.
	    this.addWindowListener(
	        new WindowAdapter() {
	        public void windowClosing(WindowEvent ev) {
	            SoundFrame.this.windowClosed();
	        }
	      }
	    );

	    SoundPanel panel = new SoundPanel();
	    getContentPane().add(panel, BorderLayout.CENTER);

	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    // 注册应用程序全局键盘事件, 所有的键盘事件都会被此事件监听器处理.
	    toolkit.addAWTEventListener( new SoundAwtEventListener(panel), AWTEvent.KEY_EVENT_MASK);
	    log.debug("-----   end -----");
    }

    /**
     * Shutdown procedure when run as an application.
     */
    protected void windowClosed() {
	// Exit application.
	System.exit(0);
    }
}
