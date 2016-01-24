package javay.math.expr;

import javay.math.BigNumCallBack;

public class Operator {

  private String operator;
  private int  priority; // 在逆波兰式的时候，这是不需要考虑的。
  private int  arity;
  private int  direction; // unary,在逆波兰式的时候这是不需要考虑的。
  private BigNumCallBack opcb;

  /**
   * Operator.
   * @param op String
   * @param pri int
   * @param cnt int
   * @param dr int
   * @param callback BigNumCallBack
   */
  public Operator(String op, int pri, int cnt, int dr, BigNumCallBack callback) {
    this.operator = op;
    this.priority = pri;
    this.arity = cnt;
    this.direction = dr;
    this.opcb = callback;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.operator);
    return buf.toString();
  }

  /**
   * getOperator.
   * @return the operator
   */
  public String getOperator() {
    return operator;
  }

  /**
   * setOperator.
   * @param operator the operator to set
   */
  public void setOperator(String operator) {
    this.operator = operator;
  }

  /**
   * 优先级.
   * @return the priority
   */
  public int getPriority() {
    return priority;
  }

  /**
   * 优先级.
   * @param priority the priority to set
   */
  public void setPriority(int priority) {
    this.priority = priority;
  }

  /**
   * getArity.
   * @return the arity
   */
  public int getArity() {
    return arity;
  }

  /**
   * setArity.
   * @param arity the arity to set
   */
  public void setArity(int arity) {
    this.arity = arity;
  }

  /**
   * getDirection.
   * @return the direction
   */
  public int getDirection() {
    return direction;
  }

  /**
   * setDirection.
   * @param direction the direction to set
   */
  public void setDirection(int direction) {
    this.direction = direction;
  }

  /**
   * getOpcb.
   * @return the opcb
   */
  public BigNumCallBack getOpcb() {
    return opcb;
  }

  /**
   * setOpcb.
   * @param opcb the opcb to set
   */
  public void setOpcb(BigNumCallBack opcb) {
    this.opcb = opcb;
  }
}
