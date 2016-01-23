package javay.awt.event;

public class ConditionOpt1 extends AbstractCondition {

  @Override
  public boolean isGuard(String sin) {
    return this.isOperator1(sin);
  }
}
