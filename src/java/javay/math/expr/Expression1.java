package javay.math.expr;

import javay.math.BigNum;

public class Expression1 extends Expression {

	private String operator;
	private Expression number;

	public Expression1(String o, Expression v) {
		this.operator = o;
		this.number = v;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.operator);
		buf.append(super.toString());
		return buf.toString();
	}
	/**
	 * @see javay.math.expr.ExpressionN#value()
	 */
	@Override
	public BigNum value() {
		BigNum val = this.number.value();
		return val;
	}
}
