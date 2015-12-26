package javay.math.expr;

import javay.math.BigNum;

public class ExpressionV extends Expression {

	private String variableName;
	private BigNum variableValue;
	
	public ExpressionV(String varName) {
		this.variableName = varName;
	}
	public ExpressionV(String varName, BigNum varVal) {
		this.variableName = varName;
		this.variableValue = varVal;
	}
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

	/**
	 * @return the variableName
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * @param variableName the variableName to set
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	/**
	 * @return the variableValue
	 */
	public BigNum getVariableValue() {
		return variableValue;
	}

	/**
	 * @param variableValue the variableValue to set
	 */
	public void setVariableValue(BigNum variableValue) {
		this.variableValue = variableValue;
	}

}
