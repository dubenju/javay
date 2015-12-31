package javay.awt.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javay.swing.CalcultorConts;
import javay.swing.CalcultorPanel;

public class NumeralSystemActionListener implements ActionListener {
	private CalcultorPanel panel;
	public NumeralSystemActionListener(CalcultorPanel panel) {
        this.panel = panel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		System.out.println(",s=" + s);
		if (CalcultorConts.BINARY.equals(s)) {
			for (int i = 2; i < 10; i ++) {
				this.panel.numButtons[i].setEnabled(false);
			}
			this.panel.btnA.setEnabled(false);
			this.panel.btnB.setEnabled(false);
			this.panel.btnC.setEnabled(false);
			this.panel.btnD.setEnabled(false);
			this.panel.btnE.setEnabled(false);
			this.panel.btnF.setEnabled(false);

			this.panel.btnFE.setEnabled(false);
			this.panel.btnDMS.setEnabled(false);
			this.panel.btnSin.setEnabled(false);
			this.panel.btnCos.setEnabled(false);
			this.panel.btnTan.setEnabled(false);
			this.panel.btnExp.setEnabled(false);
		}
		if (CalcultorConts.OCTAL.equals(s)) {
			for (int i = 2; i < 8; i ++) {
				this.panel.numButtons[i].setEnabled(true);
			}
			for (int i = 8; i < 10; i ++) {
				this.panel.numButtons[i].setEnabled(false);
			}
			this.panel.btnA.setEnabled(false);
			this.panel.btnB.setEnabled(false);
			this.panel.btnC.setEnabled(false);
			this.panel.btnD.setEnabled(false);
			this.panel.btnE.setEnabled(false);
			this.panel.btnF.setEnabled(false);

			this.panel.btnFE.setEnabled(false);
			this.panel.btnDMS.setEnabled(false);
			this.panel.btnSin.setEnabled(false);
			this.panel.btnCos.setEnabled(false);
			this.panel.btnTan.setEnabled(false);
			this.panel.btnExp.setEnabled(false);
		}
		if (CalcultorConts.DECIMAL.equals(s)) {
			for (int i = 2; i < 10; i ++) {
				this.panel.numButtons[i].setEnabled(true);
			}
			this.panel.btnA.setEnabled(false);
			this.panel.btnB.setEnabled(false);
			this.panel.btnC.setEnabled(false);
			this.panel.btnD.setEnabled(false);
			this.panel.btnE.setEnabled(false);
			this.panel.btnF.setEnabled(false);

			this.panel.btnFE.setEnabled(true);
			this.panel.btnDMS.setEnabled(true);
			this.panel.btnSin.setEnabled(true);
			this.panel.btnCos.setEnabled(true);
			this.panel.btnTan.setEnabled(true);
			this.panel.btnExp.setEnabled(true);
		}
		if (CalcultorConts.HEXADECIMAL.equals(s)) {
			for (int i = 2; i < 10; i ++) {
				this.panel.numButtons[i].setEnabled(true);
			}
			this.panel.btnA.setEnabled(true);
			this.panel.btnB.setEnabled(true);
			this.panel.btnC.setEnabled(true);
			this.panel.btnD.setEnabled(true);
			this.panel.btnE.setEnabled(true);
			this.panel.btnF.setEnabled(true);

			this.panel.btnFE.setEnabled(false);
			this.panel.btnDMS.setEnabled(false);
			this.panel.btnSin.setEnabled(false);
			this.panel.btnCos.setEnabled(false);
			this.panel.btnTan.setEnabled(false);
			this.panel.btnExp.setEnabled(false);
		}
	}
}
