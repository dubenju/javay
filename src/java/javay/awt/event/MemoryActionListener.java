package javay.awt.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javay.math.BigNum;
import javay.swing.CalcultorConts;
import javay.swing.CalcultorPanel;

public class MemoryActionListener implements ActionListener {
	private CalcultorPanel panel;
	private String mem;
	public MemoryActionListener(CalcultorPanel panel) {
        this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		System.out.println("cmd=" + s);
		if (CalcultorConts.MC.equals(s)) {
			this.mem = "";
			this.panel.mmry.setText(this.mem);
		}
		if (CalcultorConts.MR.equals(s)) {
			this.panel.textField.setText(this.mem);
		}
		if (CalcultorConts.MS.equals(s)) {
			this.mem = this.panel.textField.getText();
			this.panel.mmry.setText(this.mem);
		}
		if (CalcultorConts.MP.equals(s)) {
			if (this.mem.length() <= 0) {
				this.mem = "0";
			}
			BigNum m = new BigNum(this.mem);
			BigNum b = new BigNum(this.panel.textField.getText());
			this.mem = m.add(b).toString();
			this.panel.mmry.setText(this.mem);
		}
		if (CalcultorConts.MM.equals(s)) {
			if (this.mem.length() <= 0) {
				this.mem = "0";
			}
			BigNum m = new BigNum(this.mem);
			BigNum b = new BigNum(this.panel.textField.getText());
			this.mem = m.subtract(b).toString();
			this.panel.mmry.setText(this.mem);
		}
	}

}
