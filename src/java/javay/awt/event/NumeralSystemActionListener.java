package javay.awt.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javay.math.BigNum;
import javay.swing.CalcultorConts;
import javay.swing.CalcultorPanel;

public class NumeralSystemActionListener implements ActionListener {
	private CalcultorPanel panel;
	private Map<String, Integer> ns = new HashMap<String, Integer>();
	public NumeralSystemActionListener(CalcultorPanel panel) {
        this.panel = panel;
        ns.put(CalcultorConts.BINARY, 2);
        ns.put(CalcultorConts.OCTAL, 8);
        ns.put(CalcultorConts.DECIMAL, 10);
        ns.put(CalcultorConts.HEXADECIMAL, 16);
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
			String val = this.panel.textField.getText();
			BigNum valn = new BigNum(val, ns.get(this.panel.textField.getNumberSystem()));
			this.panel.textField.setText(valn.toBinaryString());
			this.panel.textField.setNumberSystem(CalcultorConts.BINARY);
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
			String val = this.panel.textField.getText();
			BigNum valn = new BigNum(val, ns.get(this.panel.textField.getNumberSystem()));
			this.panel.textField.setText(valn.toOctalString());
			this.panel.textField.setNumberSystem(CalcultorConts.OCTAL);
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
			String val = this.panel.textField.getText();
			BigNum valn = new BigNum(val, ns.get(this.panel.textField.getNumberSystem()));
			this.panel.textField.setText(valn.toString());
			this.panel.textField.setNumberSystem(CalcultorConts.DECIMAL);
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
			String val = this.panel.textField.getText();
			BigNum valn = new BigNum(val, ns.get(this.panel.textField.getNumberSystem()));
			this.panel.textField.setText(valn.toHexString());
			this.panel.textField.setNumberSystem(CalcultorConts.HEXADECIMAL);
		}
	}
}
