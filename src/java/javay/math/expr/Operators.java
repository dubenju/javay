package javay.math.expr;

import java.util.HashMap;
import java.util.Map;

public class Operators {
    private static final Map<String, Operator> ops = new HashMap<String, Operator>();
    static{
        /*
         * 优先级
         * 元数
         * 0:左右结合，1:右结合，－1:左结合
         */
        ops.put(ExprConts.AND, new Operator(ExprConts.AND, 000, 2, 0)); // AND
        ops.put(ExprConts.OR , new Operator(ExprConts.OR , 000, 2, 0)); // OR
        ops.put(ExprConts.XOR, new Operator(ExprConts.XOR, 000, 2, 0)); // XOR

        ops.put(ExprConts.ADD, new Operator(ExprConts.ADD,  100, 2, 0)); // +
        ops.put(ExprConts.SUB, new Operator(ExprConts.SUB,  100, 2, 0)); // -

        ops.put(ExprConts.MUL, new Operator(ExprConts.MUL,  200, 2, 0)); // *
        ops.put(ExprConts.DIV, new Operator(ExprConts.DIV,  200, 2, 0)); // /
        ops.put(ExprConts.MOD, new Operator(ExprConts.MOD,  200, 2, 0)); // mod

        ops.put(ExprConts.POW, new Operator(ExprConts.POW, 300, 2, 0)); // ^

        ops.put(ExprConts.PLUS, new Operator(ExprConts.PLUS, 400, 1, 1)); // +
        ops.put(ExprConts.MINUS,new Operator(ExprConts.MINUS,400, 1, 1)); // -
        ops.put(ExprConts.PER, new Operator(ExprConts.PER, 400, 1, -1)); // %
        ops.put(ExprConts.FAC, new Operator(ExprConts.FAC, 400, 1, -1)); // !
        ops.put(ExprConts.LSH, new Operator(ExprConts.LSH, 400, 1, 1)); // LSH
        ops.put(ExprConts.RSH, new Operator(ExprConts.RSH, 400, 1, 1)); // RSH
        ops.put(ExprConts.INT, new Operator(ExprConts.INT, 400, 1, 1)); // INT
        ops.put(ExprConts.SIN, new Operator(ExprConts.SIN, 400, 1, 1)); // SIN
        ops.put(ExprConts.COS, new Operator(ExprConts.COS, 400, 1, 1)); // COS
        ops.put(ExprConts.TAN, new Operator(ExprConts.TAN, 400, 1, 1)); // TAN
        ops.put(ExprConts.EXP, new Operator(ExprConts.EXP, 400, 1, 1)); // EXP
        ops.put(ExprConts.LN , new Operator(ExprConts.LN , 400, 1, 1)); // LN
        ops.put(ExprConts.LOG, new Operator(ExprConts.LOG, 400, 1, 1)); // LOG
        ops.put(ExprConts.NOT, new Operator(ExprConts.NOT, 400, 1, 1)); // NOT

        ops.put(ExprConts.LEFT, new Operator(ExprConts.LEFT, 500, 1, 1)); // (
        ops.put(ExprConts.RIGHT, new Operator(ExprConts.RIGHT, 500, 1, -1)); // )
    }
    private Operators() {
    }
    public static Operator getOperator(String key) {
        return ops.get(key);
    }
}
