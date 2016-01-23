package javay.awt.event;

public class ConditionNum extends AbstractCondition {

  @Override
  public boolean isGuard(String sin) {
    return this.isDigit(sin);
  }

}
