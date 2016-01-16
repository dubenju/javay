package javay.awt.event;

import javay.fsm.transition.Action;

public class ActionInit implements Action<ExprInfo> {

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
