package javay.mail;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class MyButtonListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		JOptionPane.showMessageDialog(source, source.getText() + " button has been pressed");
	}
}