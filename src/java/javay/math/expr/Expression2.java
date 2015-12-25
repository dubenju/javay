package javay.math.expr;

import javay.math.BigNum;

public class Expression2 extends Expression {

	private Operator str1;
	private Expression num1;
	private Expression num2;
	public Expression2(Expression v1, Operator o) {
		this.num1 = v1;
		this.str1 = o;
		this.num2 = null;
	}
	public Expression2(Expression v1, Operator o, Expression v2) {
		this.num1 = v1;
		this.str1 = o;
		this.num2 = v2;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.num1.toString());
		buf.append(this.str1);
		buf.append(this.num2);
		return buf.toString();
	}
	/**
	 * @see javay.math.expr.Expression1#value()
	 */
	@Override
	public BigNum value() {
		BigNum val1 = this.num1.value();
		BigNum val2 = this.num2.value();
		return this.str1.getOpcb().op(val1, val2);
	}
	/**
	 * @return the str1
	 */
	public Operator getStr1() {
		return str1;
	}
	/**
	 * @param str1 the str1 to set
	 */
	public void setStr1(Operator str1) {
		this.str1 = str1;
	}
	/**
	 * @return the num1
	 */
	public Expression getNum1() {
		return num1;
	}
	/**
	 * @param num1 the num1 to set
	 */
	public void setNum1(Expression num1) {
		this.num1 = num1;
	}
	/**
	 * @return the num2
	 */
	public Expression getNum2() {
		return num2;
	}
	/**
	 * @param num2 the num2 to set
	 */
	public void setNum2(Expression num2) {
		this.num2 = num2;
	}
}
