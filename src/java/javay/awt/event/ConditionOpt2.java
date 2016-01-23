package javay.awt.event;

public class ConditionOpt2 extends AbstractCondition {

  @Override
  public boolean isGuard(String sin) {
    return this.isOperator2(sin);
  }
}
