package javay.test.expr;

import java.util.List;

import javay.math.expr.ExprParser;
import javay.math.expr.Token;

public class TestExpr {

	public static void main(String[] args) throws Exception {
		//String str = " 1 + 2 * 3 + 4 / 2+sin(30)--5+(-1)*33.3%+3^2+5!+cos(30) +tan(30) + ln(5) + log(30)+4^2+4^3+exp(100)+1/3";
		String str = "1 + 2 * 3 + 4 / 2";
		List<Token> ts = ExprParser.parse(str);
		ExprParser.toExpr(ts, 0, 0);
	}

}
