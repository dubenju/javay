package javay.test.expr;

import java.util.List;

import javay.math.expr.ExprParser;
import javay.math.expr.Expression;
import javay.math.expr.Token;

public class TestExpr {

	public static void main(String[] args) throws Exception {
		//String str = " 1 + 2 * 3 + 4 / 2+sin(30)--5+(-1)*33.3%+3^2+5!+cos(30) +tan(30) + ln(5) + log(30)+4^2+4^3+exp(100)+1/3";
		String str = "1 + 2 * 3 + 4 / 2";
		str = "-5%";
		str ="2+(8+4^2)*3";
		List<Token> ts = ExprParser.parse(str);
		Expression expr = ExprParser.toExpr(ts);
		System.out.println("[outPRE]" + expr.toPrefixString() + "=" + expr.value());
		System.out.println("[outIN ]" + expr.toInfixString() + "=" + expr.value());
		System.out.println("[outPOS]" + expr.toPostfixString() + "=" + expr.value());
		System.out.println("[out   ]" + expr.toString() + "=" + expr.value());
	}

}
