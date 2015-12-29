package javay.test.java;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javay.swing.JVariableTextField;

public class JFrameSample {

	public static void main(String[] args) {
		//Integer cnt = new Integer("0");
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 300, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JPanel p = new JPanel();

	    JVariableTextField text = new JVariableTextField("Test", 10);
	    text.setFont(new Font("Dialog", Font.PLAIN, 12));
	    text.getMaxWidth();
	    
	    JButton buttonCut = new JButton("CUT");
	    buttonCut.addActionListener(
	      new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	          text.cut();
	        }
	      }
	    );

	    JButton buttonCopy = new JButton("COPY");
	    buttonCopy.addActionListener(
	      new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	          text.copy();
	        }
	      }
	    );

	    JButton buttonPaste = new JButton("PASTE");
	    buttonPaste.addActionListener(
	      new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	          text.paste();
	        }
	      }
	    );

	    JButton buttonAdd = new JButton("ADD");
	    buttonAdd.addActionListener(
	      new ActionListener(){
	        public void actionPerformed(ActionEvent event){
	          text.setText(text.getText() + "1");
	          //cnt = cnt + 1;;
	        }
	      }
	    );

	    p.add(text);
	    p.add(buttonCut);
	    p.add(buttonCopy);
	    p.add(buttonPaste);
	    p.add(buttonAdd);

	    Container contentPane = frame.getContentPane();
	    contentPane.add(p, BorderLayout.CENTER);
		frame.setVisible(true);
	}

}
