package javay.awt.event;

public class ConditionInit extends AbstractCondition {

  @Override
  public boolean isGuard(String sin) {
    return this.isClear(sin);
  }

}
