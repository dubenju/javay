package javay.fsm.state;

import javay.fsm.state.State;

public class StateInitial<T> implements State<T> {
  private int id = 0;
  private String label = "初始状态";
  private T value = null;

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public String getLabel() {
    return this.label;
  }

  /**
   * getValue.
   * @return the value
   */
  public T getValue() {
    return value;
  }

  /**
   * getValue.
   * @param value the value to set
   */
  public void setValue(T value) {
    this.value = value;
  }

  /**
   * toString.
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.id);
    buf.append(":");
    buf.append(this.label);
    buf.append("[");
    buf.append(this.value);
    buf.append("]");
    return buf.toString();
  }

}
