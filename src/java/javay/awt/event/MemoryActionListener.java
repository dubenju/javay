package javay.awt.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

		}
		if (CalcultorConts.MS.equals(s)) {
			this.panel.mmry.setText(this.mem);
		}
		if (CalcultorConts.MP.equals(s)) {
			this.panel.mmry.setText(this.mem);
		}
		if (CalcultorConts.MM.equals(s)) {
			this.panel.mmry.setText(this.mem);
		}
	}

}
