package javay.math.expr;

import javay.math.BigNum;

public class Expression2 extends Expression {

	private String str1;
	private Expression num1;
	private Expression num2;

	public Expression2(Expression v1, String o, Expression v2) {
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
		buf.append(this.str1);
		buf.append(super.toString());
		return buf.toString();
	}
	/**
	 * @see javay.math.expr.Expression1#value()
	 */
	@Override
	public BigNum value() {
		BigNum val1 = this.num1.value();
		BigNum val2 = this.num2.value();
		return val1;
	}
}
