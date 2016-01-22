package javay.awt.event;

public class ConditionInit extends AbstractCondition {
    @Override
    public boolean isGuard(String s) {
        return this.isClear(s);
    }

}
