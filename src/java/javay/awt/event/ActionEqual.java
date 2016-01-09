package javay.awt.event;

import java.util.ArrayList;
import java.util.List;

import javay.fsm.transition.Action;
import javay.math.expr.ExprException;
import javay.math.expr.ExprParser;
import javay.math.expr.Expression;
import javay.math.expr.Token;

public class ActionEqual implements Action<ExprInfo> {

	@Override
	public ExprInfo doAction(ExprInfo in) {
		/* *** 表达式求值 *** */
		String expression = in.getExpr();
		List<Token> ts = new ArrayList<Token>();
		try {
			ts = ExprParser.parse(expression);
		} catch (ExprException e1) {
			e1.printStackTrace();
		}
		List<Token> pots = ExprParser.toPostfix(ts);
		Expression expr2 = ExprParser.toExprFromPostfix(pots);
		System.out.println("[outIN ]" + expr2.toInfixString() + "=" + expr2.value());
		/* *** 表达式求值 *** */
		String val = expr2.value().toString();
		StringBuffer buf = new StringBuffer();
		buf.append(val);
		in.setInbuf(buf);
		in.setExpr(val); // 旧的表达式可以放到历史纪录里了。
		return in;
	}

}
