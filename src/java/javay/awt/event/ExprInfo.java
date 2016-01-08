package javay.awt.event;

public class ExprInfo {
	private String expr;
	private String num1;
	private String opt;
	private String num2;
	private String input;
	public ExprInfo() {
		this.expr = "";
		this.num1 = "";
		this.opt = "";
		this.num2 = "";
		this.input = "";
	}
	/**
	 * @return the expr
	 */
	public String getExpr() {
		return expr;
	}
	/**
	 * @param expr the expr to set
	 */
	public void setExpr(String expr) {
		this.expr = expr;
	}
	/**
	 * @return the num1
	 */
	public String getNum1() {
		return num1;
	}
	/**
	 * @param num1 the num1 to set
	 */
	public void setNum1(String num1) {
		this.num1 = num1;
	}
	/**
	 * @return the opt
	 */
	public String getOpt() {
		return opt;
	}
	/**
	 * @param opt the opt to set
	 */
	public void setOpt(String opt) {
		this.opt = opt;
	}
	/**
	 * @return the num2
	 */
	public String getNum2() {
		return num2;
	}
	/**
	 * @param num2 the num2 to set
	 */
	public void setNum2(String num2) {
		this.num2 = num2;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.input);
		buf.append(this.expr);
		return buf.toString();
	}
	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}
	/**
	 * @param input the input to set
	 */
	public void setInput(String input) {
		this.input = input;
	}
	public void addInput(String input) {
		this.input = this.input + input;
	}
}
