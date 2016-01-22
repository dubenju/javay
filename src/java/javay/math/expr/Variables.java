package javay.math.expr;

import java.util.Hashtable;

import javay.math.BigNum;

public class Variables {
    private static Hashtable<String, ExpressionV> variables = new Hashtable<String, ExpressionV>();
    static {
        variables.put("pi", new ExpressionV("pi", BigNum.PI));
        variables.put("e", new ExpressionV("e", BigNum.E));
    }
    private Variables() {
        
    }
    public static synchronized ExpressionV create(String variableName) {
        ExpressionV result = variables.get(variableName);
        if (result == null) {
            variables.put(variableName, result = new ExpressionV(variableName));
        }
        return result;
    }
    public static ExpressionV getVariable(String key) {
        return variables.get(key);
    }
    public static boolean isExist(String key) {
        return variables.containsKey(key);
    }
}
