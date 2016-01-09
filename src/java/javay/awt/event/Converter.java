package javay.awt.event;

import java.util.HashMap;
import java.util.Map;

import javay.math.expr.ExprConts;
import javay.swing.CalcultorConts;

public class Converter {
	private static final Map<String, String> conv = new HashMap<String, String>();
	static {
		conv.put(CalcultorConts.ADD     , ExprConts.ADD); // +2
		conv.put(CalcultorConts.SUBTRACT, ExprConts.SUB); // -2
		conv.put(CalcultorConts.MULTIPLY, ExprConts.MUL); // *2
		conv.put(CalcultorConts.DIVIDE  , ExprConts.DIV); // /2
		conv.put(CalcultorConts.MOD     , ExprConts.MOD); // mod2
		conv.put(CalcultorConts.XY      , ExprConts.POW); // ^2

		conv.put(CalcultorConts.EXP     , ExprConts.EXP); // exp1
		conv.put(CalcultorConts.N       , ExprConts.FAC); // !1
		conv.put(CalcultorConts.EQUAL   , ExprConts.EQU); // =
		conv.put(CalcultorConts.X2      , ExprConts.POW + " 2 "); // 1
		conv.put(CalcultorConts.X3      , ExprConts.POW + " 3 "); // 1
		conv.put(CalcultorConts.DIVIDE1 , " 1 " + ExprConts.DIV); // 1
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
	}
	public static String conv(String in) {
		String res = conv.get(in);
		if (res == null) {
			res = in;
		}
		return res;
	}
}
