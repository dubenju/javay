package javay.awt.event;

import javay.fsm.transition.Condition;
import javay.swing.CalcultorConts;

public abstract class AbstractCondition implements Condition {
	public boolean isDigit( String s ) {
		return s.equals(CalcultorConts.ZERO) || s.equals(CalcultorConts.ONE) ||
				s.equals(CalcultorConts.TWO) || s.equals(CalcultorConts.THREE) ||
				s.equals(CalcultorConts.FOUR) || s.equals(CalcultorConts.FIVE) ||
				s.equals(CalcultorConts.SIX) || s.equals(CalcultorConts.SEVEN) ||
				s.equals(CalcultorConts.EIGHT) || s.equals(CalcultorConts.NINE) ||
				s.equals(CalcultorConts.TEN) || s.equals(CalcultorConts.ELEVEN) ||
				s.equals(CalcultorConts.TWELVE) || s.equals(CalcultorConts.THRITEEN) ||
				s.equals(CalcultorConts.FOURTEEN) || s.equals(CalcultorConts.FIFTEEN) ||
				s.equals(CalcultorConts.DOT); // TODO:..
	}
	public boolean isOperator1(String s) {
		return s.equals(CalcultorConts.DIVIDE1)  || s.equals(CalcultorConts.N) ||
				s.equals(CalcultorConts.LOG) || s.equals(CalcultorConts.LN) ||
				s.equals(CalcultorConts.X2) || s.equals(CalcultorConts.X3) ||
				s.equals(CalcultorConts.EXP) || s.equals(CalcultorConts.SIN) ||
				s.equals(CalcultorConts.COS) || s.equals(CalcultorConts.TAN) ||
				s.equals(CalcultorConts.DMS) || 
				s.equals(CalcultorConts.LEFT) || s.equals(CalcultorConts.RIGHT); // TODO:()
	}
	public boolean isOperator2(String s) {
		return s.equals(CalcultorConts.ADD) || s.equals(CalcultorConts.SUBTRACT) ||
				s.equals(CalcultorConts.MULTIPLY) || s.equals(CalcultorConts.DIVIDE) ||
				s.equals(CalcultorConts.MOD) || s.equals(CalcultorConts.XY);
	}
	public boolean isEqual(String s) {
		return s.equals(CalcultorConts.EQUAL);
	}
}
