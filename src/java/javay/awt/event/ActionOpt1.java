package javay.awt.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javay.fsm.transition.Action;
import javay.math.expr.ExprException;
import javay.math.expr.ExprParser;
import javay.math.expr.Expression;
import javay.math.expr.Operator;
import javay.math.expr.Operators;
import javay.math.expr.Token;

public class ActionOpt1 implements Action<ExprInfo> {

	@Override
	public ExprInfo doAction(ExprInfo in, Object params) {
		System.out.print(this.getClass().getName());
		@SuppressWarnings("unchecked")
		Map<String, String> context = (Map<String, String>) params;
		String fromStateId = context.get("from");
		
		String num = in.getInbuf().toString();
		String opt = in.getInput();
		// 一元操作符的全角向半角变换
		String st = Converter.conv(opt, params);
		System.out.println(opt + "=>" + st);
		// 在这里对x^2,x^3,1/x有所考虑
		Operator operator = Operators.getOperator(st);
		// make expression
		int dir = operator.getDirection();
		String exp1 = "";
		if (dir == 1) {
			exp1 = st + "(" + num + ")";
		}
		if (dir == -1) {
			exp1 = "(" + num + ")" + st;
		}
		// 表达式
		String expr = in.getExpr();
		String opt2 = in.getOpt();
		if (opt2.equals("") == false) {
			in.setExpr(expr + opt2 + "(" + exp1 + ")");
		} else {
			in.setExpr("(" + exp1 + ")");
		}
		in.setOpt("");

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
