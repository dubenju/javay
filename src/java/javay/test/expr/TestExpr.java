package javay.test.expr;

import javay.math.expr.ExprParser;

public class TestExpr {

	public static void main(String[] args) {
		String str = " 1 + 2 * 3 + 4 / 2";
		ExprParser.parse(str);
	}

}
