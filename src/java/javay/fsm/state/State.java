package javay.fsm.state;

public interface State {
    public int getId();
    public String getLabel();
    public String getValue();
    public void setValue(String value);

//    public State getParent();
//    public State createSubstate(String label);
}
