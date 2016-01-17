package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionBsce extends AbstractCondition {
    private static final Logger log = LoggerFactory.getLogger(ConditionBsce.class);
	@Override
	public boolean isGuard(String s) {
		return this.isBackSpace(s) || this.isClearEntry(s);
	}

}
