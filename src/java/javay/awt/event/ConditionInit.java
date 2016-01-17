package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionInit extends AbstractCondition {
    private static final Logger log = LoggerFactory.getLogger(ConditionInit.class);
	@Override
	public boolean isGuard(String s) {
		return this.isClear(s);
	}

}
