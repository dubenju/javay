package javay.fsm.state;

public interface State<T> {
  public int getId();

  public String getLabel();

  public T getValue();

  public void setValue(T value);

//  public State getParent();
//  public State createSubstate(String label);
}
