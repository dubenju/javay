package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javay.fsm.transition.Action;

public class ActionInit implements Action<ExprInfo> {
    private static final Logger log = LoggerFactory.getLogger(ActionInit.class);
	@Override
	public ExprInfo doAction(ExprInfo in, Object params) {
		System.out.print(this.getClass().getName());
		in.setNum1("");
		in.setNum2("");
		in.setOpt("");
		in.setExpr("0");
		StringBuffer inbuf = new StringBuffer();
		in.setInbuf(inbuf);
		return in;
	}

}
