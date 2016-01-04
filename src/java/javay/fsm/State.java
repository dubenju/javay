package javay.fsm;

public interface State {
    public int getId();
    public String getLabel();

    public State getParent();
    public State createSubstate(String label);
}
