package javay.test.expr;

import java.util.List;

import javay.math.BigNum;
import javay.math.expr.ExprParser;
import javay.math.expr.Expression;
import javay.math.expr.ExpressionV;
import javay.math.expr.Token;
import javay.math.expr.Variables;

public class TestExpr {

	public static void main(String[] args) throws Exception {
		//String str = " 1 + 2 * 3 + 4 / 2+sin(30)--5+(-1)*33.3%+3^2+5!+cos(30) +tan(30) + ln(5) + log(30)+4^2+4^3+exp(100)+1/3";
		String str = "1 + 2 * 3 + 4 / 2";
		str = "-5%";
		str ="2+(8+4^2)*3";
		str = "sin(30)";
		str = "1 + x * 3 + 4 / x";
		str = "1 x 3 * + 4 x / +";
		ExpressionV x = Variables.create("x");
		x.setVariableValue(new BigNum("2.0"));
		System.out.println(x);
		
		List<Token> ts = ExprParser.parse(str);
		//Expression expr = ExprParser.toExprFromInfix(ts, 0);
		
		Expression expr = ExprParser.toExprFromPostfix(ts);
		System.out.println("[outPRE]" + expr.toPrefixString() + "=" + expr.value());
		System.out.println("[outIN ]" + expr.toInfixString() + "=" + expr.value());
		System.out.println("[outPOS]" + expr.toPostfixString() + "=" + expr.value());
		System.out.println("[out   ]" + expr.toString() + "=" + expr.value());
	}

}
