package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionOpt1 extends AbstractCondition {
    private static final Logger log = LoggerFactory.getLogger(ConditionOpt1.class);
	@Override
	public boolean isGuard(String s) {
		return this.isOperator1(s);
	}
}
