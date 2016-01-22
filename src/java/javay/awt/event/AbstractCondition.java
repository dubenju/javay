package javay.awt.event;

import javay.fsm.transition.Condition;
import javay.math.expr.ExprConts;
import javay.swing.CalcultorConts;

public abstract class AbstractCondition implements Condition {
//    // CalcultorConts的定数使用
//    private static final Map<String, String> digit = new HashMap<String, String>();
//    private static final Map<String, String> opt1  = new HashMap<String, String>();
//    private static final Map<String, String> opt2  = new HashMap<String, String>();
//    private static final Map<String, String> equal = new HashMap<String, String>();
//    static {
//        opt2.put(ExprConts.ADD, ExprConts.ADD); // +
//        opt2.put(ExprConts.SUB, ExprConts.SUB); // -
//        opt2.put(ExprConts.MUL, ExprConts.MUL); // *
//        opt2.put(ExprConts.DIV, ExprConts.DIV); // /
//        opt2.put(ExprConts.MOD, ExprConts.MOD); // mod
//        opt2.put(ExprConts.POW, ExprConts.POW); // ^
//
//        equal.put(ExprConts.EQU, ExprConts.EQU); // =
//    }
    public boolean isDigit( String s ) {
        return s.equals(CalcultorConts.ZERO) || s.equals(CalcultorConts.ONE) ||
                s.equals(CalcultorConts.TWO) || s.equals(CalcultorConts.THREE) ||
                s.equals(CalcultorConts.FOUR) || s.equals(CalcultorConts.FIVE) ||
                s.equals(CalcultorConts.SIX) || s.equals(CalcultorConts.SEVEN) ||
                s.equals(CalcultorConts.EIGHT) || s.equals(CalcultorConts.NINE) ||
                s.equals(CalcultorConts.TEN) || s.equals(CalcultorConts.ELEVEN) ||
                s.equals(CalcultorConts.TWELVE) || s.equals(CalcultorConts.THRITEEN) ||
                s.equals(CalcultorConts.FOURTEEN) || s.equals(CalcultorConts.FIFTEEN) ||
                s.equals(CalcultorConts.DOT);
    }
    public boolean isOperator1(String s) {
        return s.equals(CalcultorConts.DIVIDE1)  || s.equals(ExprConts.FAC) ||
                s.equals(CalcultorConts.LOG) || s.equals(CalcultorConts.LN) ||
                s.equals(CalcultorConts.X2) || s.equals(CalcultorConts.X3) ||
                s.equals(CalcultorConts.EXP) || s.equals(CalcultorConts.SIN) ||
                s.equals(CalcultorConts.COS) || s.equals(CalcultorConts.TAN) ||
                s.equals(CalcultorConts.DMS) || s.equals(CalcultorConts.N) ||
                s.equals(CalcultorConts.INT) || s.equals(CalcultorConts.POS_MINUS) ||
                s.equals(CalcultorConts.NOT) ||
                s.equals(CalcultorConts.LEFT) || s.equals(CalcultorConts.RIGHT); // TODO:()
    }
    public boolean isOperator2(String s) {
        return s.equals(CalcultorConts.ADD) || s.equals(CalcultorConts.SUBTRACT) ||
                s.equals(CalcultorConts.MULTIPLY) || s.equals(CalcultorConts.DIVIDE) ||
                s.equals(CalcultorConts.MOD) || s.equals(CalcultorConts.XY) ||
                s.equals(CalcultorConts.LSH) || s.equals(CalcultorConts.RSH) ||
                s.equals(CalcultorConts.AND) || s.equals(CalcultorConts.OR) ||
                s.equals(CalcultorConts.XOR);
    }
    public boolean isEqual(String s) {
        return s.equals(CalcultorConts.EQUAL);
    }
    public boolean isBackSpace(String s) {
        return s.equals(CalcultorConts.BACKSPACE);
    }
    public boolean isClearEntry(String s) {
        return s.equals(CalcultorConts.CLEAR_ERROR);
    }
    public boolean isClear(String s) {
        return s.equals(CalcultorConts.CLEAR);
    }
}
