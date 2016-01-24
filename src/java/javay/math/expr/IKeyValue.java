package javay.math.expr;

/**
 * IKeyValue.
 * @author dubenju
 *
 */

public interface IKeyValue<T> extends IBean {
  public String getKey();

  public T    getValue();

  public void setKey(String ke);

  public void setValue(T va);
}
