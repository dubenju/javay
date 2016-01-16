package javay.awt.event;

public class ExprInfo {
	/** 表达式用 */
	private String expr;
	private String num1;
	private String opt;
	private String num2;
	private String input;
	/** 当前输入表示用 */
	private StringBuffer inbuf;
	public ExprInfo() {
		this.expr = "";
		this.num1 = "";
		this.opt = "";
		this.num2 = "";
		this.input = "";
		this.inbuf = new StringBuffer();
	}
	/**
	 * 表达式
	 * @return the expr 表达式
	 */
	public String getExpr() {
		return expr;
	}
	/**
	 * 表达式
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
		buf.append("当前输入:" +this.input);
		buf.append(",");
		buf.append("输入缓存:" + this.inbuf.toString());
		buf.append(",");
		buf.append("表达式:" +this.expr);
		buf.append(",");
		buf.append("操作数1:" +this.num1);
		buf.append(",");
		buf.append("操作符:" +this.opt);
		buf.append(",");
		buf.append("操作数2:" +this.num2);
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
	public void append(String s) {
		this.inbuf.append(s);
		// this.expr = this.expr + s;
	}
	/**
	 * 当前输入表示
	 * @return the inbuf 当前输入表示
	 */
	public StringBuffer getInbuf() {
		return inbuf;
	}
	/**
	 * 当前输入表示
	 * @param inbuf the inbuf to set
	 */
	public void setInbuf(StringBuffer inbuf) {
		this.inbuf = inbuf;
	}
}
