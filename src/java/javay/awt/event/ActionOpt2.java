package javay.awt.event;

import javay.fsm.transition.Action;
import javay.swing.CalcultorConts;

public class ActionOpt2 implements Action {

	@Override
	public String doAction(String in) {
		StringBuffer buf = new StringBuffer();
		buf.append(CalcultorConts.LEFT);
		buf.append(in);
		buf.append(CalcultorConts.RIGHT);
		return buf.toString();
	}

}
