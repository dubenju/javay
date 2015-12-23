package javay.math.expr;

public class Operator {

    private String operator;
    private int    priority;
    private int    arity;
    private int    direction; // unary
    
    public Operator(String op, int pri, int cnt, int d) {
        this.operator = op;
        this.priority = pri;
        this.arity = cnt;
        this.direction = d;
    }
}
