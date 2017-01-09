package javay.test.expr;

import javay.regex.expr.RExprParser;

public class TestRTestExpr {

    public static void main(String[] args) throws Exception {
        String pattern = "\\.(\\d*?)(9|0)\\2{5,}(\\d{1,5})$";
        RExprParser.parse(pattern);
    }

}
