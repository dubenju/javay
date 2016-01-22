package javay.awt.event;

public class ConditionNum extends AbstractCondition {
    @Override
    public boolean isGuard(String s) {
        return this.isDigit(s);
    }

}
