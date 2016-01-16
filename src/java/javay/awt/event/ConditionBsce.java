package javay.awt.event;

public class ConditionBsce extends AbstractCondition {

	@Override
	public boolean isGuard(String s) {
		return this.isBackSpace(s) || this.isClearEntry(s);
	}

}
