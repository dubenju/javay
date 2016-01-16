package javay.awt.event;

import java.util.Map;

import javay.fsm.transition.Action;
import javay.swing.CalcultorConts;

public class ActionBsce implements Action<ExprInfo> {

	@Override
	public ExprInfo doAction(ExprInfo in, Object params) {
		@SuppressWarnings("unchecked")
		Map<String, String> context = (Map<String, String>) params;
		String s = context.get(CalcultorConts.INPUT);
		if (s.equals(CalcultorConts.BACKSPACE)) {
			
		}
		if (s.equals(CalcultorConts.CLEAR_ERROR)) {
			
		}
		return in;
	}

}
