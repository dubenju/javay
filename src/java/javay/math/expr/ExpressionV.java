package javay.math.expr;

import javay.math.BigNum;

public class ExpressionV extends Expression {

	private String variableName;
	private BigNum variableValue;
	@Override
	public BigNum value() {
		return this.variableValue;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.variableName);
		buf.append(":");
		buf.append(this.variableValue);
		return buf.toString();
	}

	@Override
	public String toPrefixString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.variableName);
		buf.append(":");
		buf.append(this.variableValue);
		return buf.toString();
	}

	@Override
	public String toInfixString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.variableName);
		buf.append(":");
		buf.append(this.variableValue);
		return buf.toString();
	}

	@Override
	public String toPostfixString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.variableName);
		buf.append(":");
		buf.append(this.variableValue);
		return buf.toString();
	}

}
