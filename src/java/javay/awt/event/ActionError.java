package javay.awt.event;

import javay.fsm.transition.Action;

public class ActionError implements Action<ExprInfo> {

	@Override
	public ExprInfo doAction(ExprInfo in) {
		in.setNum1("");
		in.setNum2("");
		in.setOpt("");
		in.setExpr("0");
		StringBuffer inbuf = new StringBuffer();
		inbuf.append("输入错误");
		in.setInbuf(inbuf);
		return in;
	}

}
