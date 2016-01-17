package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionEqual extends AbstractCondition {
    private static final Logger log = LoggerFactory.getLogger(ConditionEqual.class);
	@Override
	public boolean isGuard(String s) {
		return this.isEqual(s);
	}

}
