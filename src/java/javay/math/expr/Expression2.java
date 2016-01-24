package javay.math.expr;

import javay.math.BigNum;

public class Expression2 extends Expression {

  private Operator str1;
  private Expression num1;
  private Expression num2;

  /**
   * Expression2.
   * @param v1 Expression
   * @param op Operator
   */
  public Expression2(Expression v1, Operator op) {
    this.num1 = v1;
    this.str1 = op;
    this.num2 = null;
  }

  /**
   * Expression2.
   * @param v1 Expression
   * @param op Operator
   * @param v2 Expression
   */
  public Expression2(Expression v1, Operator op, Expression v2) {
    this.num1 = v1;
    this.str1 = op;
    this.num2 = v2;
  }

  /**
   * toInfixString.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toInfixString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.num1.toInfixString());
    buf.append(this.str1);
    buf.append(this.num2.toInfixString());
    return buf.toString();
  }

  /**
   * value.
   * @see javay.math.expr.Expression1#value()
   */
  @Override
  public BigNum value() {
    BigNum val1 = this.num1.value();
    BigNum val2 = this.num2.value();
    return this.str1.getOpcb().op(val1, val2);
  }

  /**
   * getStr1.
   * @return the str1
   */
  public Operator getStr1() {
    return str1;
  }

  /**
   * setStr1.
   * @param str1 the str1 to set
   */
  public void setStr1(Operator str1) {
    this.str1 = str1;
  }

  /**
   * getNum1.
   * @return the num1
   */
  public Expression getNum1() {
    return num1;
  }

  /**
   * setNum1.
   * @param num1 the num1 to set
   */
  public void setNum1(Expression num1) {
    this.num1 = num1;
  }

  /**
   * getNum2.
   * @return the num2
   */
  public Expression getNum2() {
    return num2;
  }

  /**
   * setNum2.
   * @param num2 the num2 to set
   */
  public void setNum2(Expression num2) {
    this.num2 = num2;
  }

  @Override
  public String toPrefixString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.str1);
    buf.append(" ");
    buf.append(this.num1.toPrefixString());
    buf.append(" ");
    buf.append(this.num2.toPrefixString());
    buf.append(" ");
    return buf.toString();
  }

  @Override
  public String toPostfixString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.num1.toPostfixString());
    buf.append(" ");
    buf.append(this.num2.toPostfixString());
    buf.append(" ");
    buf.append(this.str1);
    buf.append(" ");
    return buf.toString();
  }

  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.num1.toString());
    buf.append(this.str1);
    if (this.num2 != null) {
      buf.append(this.num2.toString());
    } else {
      buf.append("NULL");
    }
    return buf.toString();
  }
}
