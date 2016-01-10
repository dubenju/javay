package javay.awt.event;

import javay.fsm.transition.Action;

public class ActionNum1 implements Action<ExprInfo> {

	@Override
	public ExprInfo doAction(ExprInfo in, Object params) {
		String cur_in = in.getInput();

		StringBuffer buf = in.getInbuf();
		buf = new StringBuffer();
		buf.append(cur_in);
		in.setInbuf(buf);

//		String expr = "";
//		in.setExpr(expr + buf.toString());
//		in.setNum1(cur_in);
		in.setExpr("");
		return in;
	}

}
