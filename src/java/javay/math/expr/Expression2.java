package javay.math.expr;

import javay.math.BigNum;

public class Expression2 extends Expression1 {

	private String str1;
	private Expression num1;
	private Expression num2;
	public Expression2(String s) {
		super(s);
	}
	public Expression2(String s1, String o, String s2) {
		super(o, s2);
		this.str1 = s1;
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
		return super.value();
	}
}
