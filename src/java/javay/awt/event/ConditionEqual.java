package javay.awt.event;

public class ConditionEqual extends AbstractCondition {

  @Override
  public boolean isGuard(String sin) {
    return this.isEqual(sin);
  }
}
