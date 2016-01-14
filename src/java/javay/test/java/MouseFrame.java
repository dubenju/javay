package javay.test.java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MouseFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MouseFrame(){
	    setTitle("mouse tect");
	    setSize(SIDE_L,SIDE_W);
//	    setLayout(null);

	    JPanel panel = new JPanel();
	    getContentPane().add(panel);
//	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//	    panel.setLayout(new BorderLayout(0, 0));
	    panel.setLayout(null);
	    panel.setPreferredSize(new Dimension(200, 200));
	    panel.setBorder(BorderFactory.createLineBorder(Color.red));

	    JButton btn=new JButton("Quit");
	    btn.setBounds(80, 70, 30, 30);
//	    btn.setMinimumSize(new Dimension(10, 10));
	    panel.add(btn, BorderLayout.NORTH);
	    btn.addMouseListener(new btnListener());
	}

	 private static final int SIDE_L=400;
	 private static final int SIDE_W=400;
	 
	 private class btnListener extends MouseAdapter{
	  public void mouseClicked(MouseEvent e){
	   System.exit(0);
	  }
	 }
	 public static void main(String[] args){
		 MouseFrame f = new MouseFrame();
		 f.setVisible(true);
	 }
}