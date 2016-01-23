package javay.math.expr;

import javay.math.BigNum;

public class ExpressionN extends Expression {

  /**
   * 操作数
   */
  private String str;
  public ExpressionN(String s) {
    this.str = s;
  }
  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toInfixString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.str);
    return buf.toString();
  }
  @Override
  public BigNum value() {
    return new BigNum(this.str);
  }
  @Override
  public String toPrefixString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.str);
    return buf.toString();
  }
  @Override
  public String toPostfixString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.str);
    return buf.toString();
  }
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.str);
    return buf.toString();
  }
}
