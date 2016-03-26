package javay.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import javay.awt.ViewWavPanel;
import javay.main.TestCity.GoThread;

public class TestWav {
	  private GoThread t = null;

	  private Runnable run = null; // 更新组件的线程
	  private Component component = null;
	  private ViewWavPanel panel = null;
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	TestWav fg = new TestWav();
	        }
	    });
	}
	  public TestWav() {
		    final JFrame frame = new JFrame();
		    panel = new ViewWavPanel();
		    Container contentPane = frame.getContentPane();
		    contentPane.setLayout(new BorderLayout());
		    contentPane.add(panel, BorderLayout.CENTER);
		    frame.setLocation(0, 0);
		    frame.setTitle("图测试");
		    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		    frame.pack();
		    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setVisible(true);
	  }
}
