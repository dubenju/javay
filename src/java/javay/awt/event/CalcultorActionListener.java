/**
 *
 */
package javay.awt.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javay.math.BigNum;
import javay.swing.CalcultorConts;
import javay.swing.CalcultorPanel;

/**
 * 第一种：自身类实现ActionListener接口，作为事件监听器。
 * 第一种的监听处理部分，如果有多个，那就需要一个个去判断，从理论上说是影响程序速度的。
 *
 * 第二种：通过匿名类处理。
 * 第二种和第三种比较常用，如果程序的监听事件比较少，可以用第二种，匿名类很合适。
 *
 * 第三种：通过内部类处理。
 * 第三种符合面向对象编程（可以设置内部类只提供自身类使用，而且方便使用自身类的资源），尤其是适合多个监听事件的处理，当然也适合第二种方法情况。
 *
 * 第四种：通过外部类处理。
 * 第四种是外部类，如果多个监听事件相同，就可以选用此种方法。
 * ActionListener接口，作为事件监听器。
 * @author dubenju
 *
 */
public class CalcultorActionListener implements ActionListener {

	private CalcultorPanel panel;
	private String mem;
	private Map<String, Integer> ns = new HashMap<String, Integer>();
	CalcultorFSM fsm = new CalcultorFSM();

	/**
	 *
	 */
	public CalcultorActionListener(CalcultorPanel panel) {
        this.panel = panel;
        ns.put(CalcultorConts.BINARY, 2);
        ns.put(CalcultorConts.OCTAL, 8);
        ns.put(CalcultorConts.DECIMAL, 10);
        ns.put(CalcultorConts.HEXADECIMAL, 16);
	}

	private boolean isControl(String s) {
		return s.equals(CalcultorConts.MR) || s.equals(CalcultorConts.MC) ||
				s.equals(CalcultorConts.MS) || s.equals(CalcultorConts.MP) ||
				s.equals(CalcultorConts.MM) || s.equals(CalcultorConts.BINARY) ||
				s.equals(CalcultorConts.OCTAL) || s.equals(CalcultorConts.DECIMAL) ||
				s.equals(CalcultorConts.HEXADECIMAL) || s.equals(CalcultorConts.BACKSPACE) ||
				s.equals(CalcultorConts.CLEAR_ERROR) || s.equals(CalcultorConts.CLEAR);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		System.out.println("s=" + s);

		if (this.isControl(s)) {
			control(s);
			return ;
		}
		
		Map<String, String> context = new HashMap<String, String>();
		String value = "d"; // 度
		if (this.panel.rad.isSelected()) {
			//弧度
			value = "r";
		}
		if (this.panel.grad.isSelected()) {
			//百分度
			value = "g";
		}
		String inverse = "";
		if (this.panel.inv.isSelected()) {
			inverse = "inverse";
		}
		String hyperbolic = "";
		if (this.panel.hyp.isSelected()) {
			hyperbolic = "hyperbolic";
		}
		context.put(CalcultorConts.TRIGONOMETRIC_FUNCTION, value);
		context.put(CalcultorConts.INVERSE, inverse);
		context.put(CalcultorConts.HYPERBOLIC, hyperbolic);
		// 在状态机内对操作符进行变换
		ExprInfo expr = this.fsm.receive(s, context);

		/* *** 表达式求值 *** */
		String expression = expr.getExpr();
		this.panel.expr.setText(expression);
		String display = expr.getInbuf().toString();
		if (display.length() > 0) {
			this.panel.textField.setText(display);
		}
		/* *** 表达式求值 *** */

		/* 控制 */
		if( s.equals(CalcultorConts.DMS) ) {
			if (this.panel.inv.isSelected()) {
				this.panel.inv.setSelected(false);
			}
		}

	}

	private void control(String s) {
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
		if (CalcultorConts.BACKSPACE.equals(s)) {

		}
		if (CalcultorConts.CLEAR_ERROR.equals(s)) {

		}
		if (CalcultorConts.CLEAR.equals(s)) {

		}
	}
}
