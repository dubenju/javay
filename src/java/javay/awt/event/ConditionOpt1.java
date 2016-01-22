package javay.awt.event;

public class ConditionOpt1 extends AbstractCondition {
    @Override
    public boolean isGuard(String s) {
        return this.isOperator1(s);
    }
}
