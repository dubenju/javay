package javay.math.expr;

import javay.math.BigNum;

public class Expression1 extends ExpressionN {

	private String operator;
	private Expression number;

	public Expression1(String s) {
		super(s);
	}
	public Expression1(String o, String v) {
		super(v);
		this.operator = o;
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
	/* (non-Javadoc)
	 * @see javay.math.expr.ExpressionN#value()
	 */
	@Override
	public BigNum value() {
		BigNum val = this.number.value();
		return super.value();
	}
}
