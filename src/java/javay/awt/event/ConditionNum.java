package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionNum extends AbstractCondition {
    private static final Logger log = LoggerFactory.getLogger(ConditionNum.class);
	@Override
	public boolean isGuard(String s) {
		return this.isDigit(s);
	}

}
