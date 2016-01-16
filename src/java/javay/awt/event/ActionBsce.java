package javay.awt.event;

import java.util.Map;

import javay.fsm.transition.Action;
import javay.swing.CalcultorConts;

public class ActionBsce implements Action<ExprInfo> {

	@Override
	public ExprInfo doAction(ExprInfo in, Object params) {
		System.out.print(this.getClass().getName());
		@SuppressWarnings("unchecked")
		Map<String, String> context = (Map<String, String>) params;
		String s = context.get(CalcultorConts.INPUT);
		if (s.equals(CalcultorConts.BACKSPACE)) {
			String str = in.getInbuf().toString();
			if (str.length() > 0) {
				String tmp = str.substring(0, str.length() - 1);
				in.setInbuf(new StringBuffer().append(tmp));
			}
		}
		if (s.equals(CalcultorConts.CLEAR_ERROR)) {
			in.setInbuf(new StringBuffer());
		}
		return in;
	}
}
