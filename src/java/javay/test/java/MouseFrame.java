package javay.test.java;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MouseFrame extends JFrame{

	 public MouseFrame(){
	  setTitle("mouse tect");
	  setSize(SIDE_L,SIDE_W);
	  setLayout(null);
	  JButton btn=new JButton("Quit");
	  btn.setBounds(380, 370, 30, 30);
	  add(btn);
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