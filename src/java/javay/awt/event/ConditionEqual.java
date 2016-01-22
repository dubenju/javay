package javay.awt.event;

public class ConditionEqual extends AbstractCondition {
    @Override
    public boolean isGuard(String s) {
        return this.isEqual(s);
    }

}
