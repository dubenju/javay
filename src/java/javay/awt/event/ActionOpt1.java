package javay.awt.event;

import javay.fsm.transition.Action;

public class ActionOpt1 implements Action<ExprInfo> {

	@Override
	public ExprInfo doAction(ExprInfo in) {
		String num = in.getInbuf().toString();
		String opt = in.getInput();
		// make expression
		String expr = in.getExpr();
		in.setExpr(expr + " " + num + opt);
		StringBuffer buf = new StringBuffer();
		buf.append(num + opt);
		in.setInbuf(buf);
		return in;
	}

}
