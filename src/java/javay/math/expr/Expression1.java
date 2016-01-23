package javay.math.expr;

import javay.math.BigNum;

public class Expression1 extends Expression {

  private Operator operator;
  private Expression number;

  public Expression1(Operator o, Expression v) {
    this.operator = o;
    this.number = v;
  }
  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toInfixString() {
    StringBuffer buf = new StringBuffer();
    if (this.operator.getDirection() == 1) {
      buf.append(" ");
      buf.append(this.operator);
      buf.append(" ");
    }
    buf.append(this.number.toInfixString());
    if (this.operator.getDirection() == -1) {
      buf.append(" ");
      buf.append(this.operator);
      buf.append(" ");
    }
    return buf.toString();
  }
  /**
   * @see javay.math.expr.ExpressionN#value()
   */
  @Override
  public BigNum value() {
    BigNum val = this.number.value();
    return this.operator.getOpcb().op(val, null);
  }
  /**
   * @return the operator
   */
  public Operator getOperator() {
    return operator;
  }
  /**
   * @param operator the operator to set
   */
  public void setOperator(Operator operator) {
    this.operator = operator;
  }
  /**
   * @return the number
   */
  public Expression getNumber() {
    return number;
  }
  /**
   * @param number the number to set
   */
  public void setNumber(Expression number) {
    this.number = number;
  }
  @Override
  public String toPrefixString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.operator);
    buf.append(" ");
    buf.append(this.number.toPrefixString());
    buf.append(" ");
    return buf.toString();
  }
  @Override
  public String toPostfixString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.number.toPostfixString());
    buf.append(" ");
    buf.append(this.operator);
    buf.append(" ");
    return buf.toString();
  }
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    if (this.operator.getDirection() == 1) {
      buf.append(" ");
      buf.append(this.operator);
      buf.append(" ");
    }
    if (this.number != null) {
      buf.append(this.number.toString());
    } else {
      buf.append("NULL");
    }
    if (this.operator.getDirection() == -1) {
      buf.append(" ");
      buf.append(this.operator);
      buf.append(" ");
    }
    return buf.toString();
  }
}
