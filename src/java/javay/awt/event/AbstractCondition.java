package javay.awt.event;

import javay.fsm.transition.Condition;
import javay.math.expr.ExprConts;
import javay.swing.CalcultorConts;

public abstract class AbstractCondition implements Condition {

    /**
     *
     * @param scmd cmd
     * @return check result
     */
  public boolean isDigit( String scmd) {
    return scmd.equals(CalcultorConts.ZERO) || scmd.equals(CalcultorConts.ONE)
            || scmd.equals(CalcultorConts.TWO) || scmd.equals(CalcultorConts.THREE)
            || scmd.equals(CalcultorConts.FOUR) || scmd.equals(CalcultorConts.FIVE)
            || scmd.equals(CalcultorConts.SIX) || scmd.equals(CalcultorConts.SEVEN)
            || scmd.equals(CalcultorConts.EIGHT) || scmd.equals(CalcultorConts.NINE)
            || scmd.equals(CalcultorConts.TEN) || scmd.equals(CalcultorConts.ELEVEN)
            || scmd.equals(CalcultorConts.TWELVE) || scmd.equals(CalcultorConts.THRITEEN)
            || scmd.equals(CalcultorConts.FOURTEEN) || scmd.equals(CalcultorConts.FIFTEEN)
            || scmd.equals(CalcultorConts.DOT);
    }

    /**
     *
     * @param scmd cmd
     * @return check resul
     */
    public boolean isOperator1(String scmd) {
        return scmd.equals(CalcultorConts.DIVIDE1)  || scmd.equals(ExprConts.FAC)
            || scmd.equals(CalcultorConts.LOG) || scmd.equals(CalcultorConts.LN)
            || scmd.equals(CalcultorConts.X2) || scmd.equals(CalcultorConts.X3)
            || scmd.equals(CalcultorConts.EXP) || scmd.equals(CalcultorConts.SIN)
            || scmd.equals(CalcultorConts.COS) || scmd.equals(CalcultorConts.TAN)
            || scmd.equals(CalcultorConts.DMS) || scmd.equals(CalcultorConts.N)
            || scmd.equals(CalcultorConts.INT) || scmd.equals(CalcultorConts.POS_MINUS)
            || scmd.equals(CalcultorConts.NOT)
            || scmd.equals(CalcultorConts.LEFT) || scmd.equals(CalcultorConts.RIGHT); // TODO:()
    }
    /**
     *
     * @param scmd cmd
     * @return check resul
     */
    public boolean isOperator2(String scmd) {
        return scmd.equals(CalcultorConts.ADD) || scmd.equals(CalcultorConts.SUBTRACT)
            || scmd.equals(CalcultorConts.MULTIPLY) || scmd.equals(CalcultorConts.DIVIDE)
            || scmd.equals(CalcultorConts.MOD) || scmd.equals(CalcultorConts.XY)
            || scmd.equals(CalcultorConts.LSH) || scmd.equals(CalcultorConts.RSH)
            || scmd.equals(CalcultorConts.AND) || scmd.equals(CalcultorConts.OR)
            || scmd.equals(CalcultorConts.XOR);
    }

    /**
     *
     * @param scmd cmd
     * @return check resul
     */
    public boolean isEqual(String scmd) {
        return scmd.equals(CalcultorConts.EQUAL);
    }
    /**
     *
     * @param scmd cmd
     * @return check resul
     */
    public boolean isBackSpace(String scmd) {
        return scmd.equals(CalcultorConts.BACKSPACE);
    }
    /**
     *
     * @param scmd cmd
     * @return check resul
     */
    public boolean isClearEntry(String scmd) {
        return scmd.equals(CalcultorConts.CLEAR_ERROR);
    }
    /**
     *
     * @param scmd cmd
     * @return check resul
     */
    public boolean isClear(String scmd) {
        return scmd.equals(CalcultorConts.CLEAR);
    }
}
