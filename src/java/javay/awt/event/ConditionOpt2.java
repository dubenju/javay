package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionOpt2 extends AbstractCondition {
    private static final Logger log = LoggerFactory.getLogger(ConditionOpt2.class);
	@Override
	public boolean isGuard(String s) {
		return this.isOperator2(s);
	}
}
