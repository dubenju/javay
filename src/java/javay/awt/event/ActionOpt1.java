package javay.awt.event;

import java.util.ArrayList;
import java.util.List;

import javay.fsm.transition.Action;
import javay.math.expr.ExprException;
import javay.math.expr.ExprParser;
import javay.math.expr.Expression;
import javay.math.expr.Operator;
import javay.math.expr.Operators;
import javay.math.expr.Token;

public class ActionOpt1 implements Action<ExprInfo> {

	@Override
	public ExprInfo doAction(ExprInfo in) {
		String num = in.getInbuf().toString();
		String opt = in.getInput();
		// TODO:
		String st = Converter.conv(opt);
		// make expression
		Operator operator = Operators.getOperator(opt);
		int dir = operator.getDirection();
		String exp1 = "";
		if (dir == 1) {
			exp1 = opt + "(" + num + ")";
		}
		if (dir == -1) {
			exp1 = num + opt;
		}
		String expr = in.getExpr();
		in.setExpr(exp1);

		/* *** 表达式求值 *** */
		String expression = exp1;
		List<Token> ts = new ArrayList<Token>();
		try {
			ts = ExprParser.parse(expression);
		} catch (ExprException e1) {
			e1.printStackTrace();
		}
		List<Token> pots = ExprParser.toPostfix(ts);
		Expression expr2 = ExprParser.toExprFromPostfix(pots);
		System.out.println("[outIN ]" + expr2.toInfixString() + "=" + expr2.value());

		StringBuffer buf = new StringBuffer();
		buf.append(expr2.value().toString());
		in.setInbuf(buf);
		return in;
	}

}
