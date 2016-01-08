package javay.awt.event;

import javay.fsm.transition.Action;
import javay.swing.CalcultorConts;

public class ActionOpt2 implements Action<ExprInfo> {

	@Override
	public ExprInfo doAction(ExprInfo in) {
		String s = in.getNum1();
		StringBuffer buf = new StringBuffer();
		buf.append(CalcultorConts.LEFT);
		buf.append(s);
		buf.append(CalcultorConts.RIGHT);
		in.setNum1(buf.toString());
		return in;
	}

}
