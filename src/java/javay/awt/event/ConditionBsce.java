package javay.awt.event;

public class ConditionBsce extends AbstractCondition {

  @Override
  public boolean isGuard(String sin) {
    return this.isBackSpace(sin) || this.isClearEntry(sin);
  }

}
