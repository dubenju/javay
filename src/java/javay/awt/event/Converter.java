package javay.awt.event;

import java.util.HashMap;
import java.util.Map;

import javay.math.expr.ExprConts;
import javay.swing.CalcultorConts;

public class Converter {
	private static final Map<String, String> conv = new HashMap<String, String>();
	static {
		conv.put(CalcultorConts.ADD, ExprConts.ADD);
		conv.put(CalcultorConts.SUBTRACT, ExprConts.SUB);
		conv.put(CalcultorConts.MULTIPLY, ExprConts.MUL);
		conv.put(CalcultorConts.DIVIDE, ExprConts.DIV);
		conv.put(CalcultorConts.MOD, ExprConts.MOD);
		conv.put(CalcultorConts.XY, ExprConts.POW);
		conv.put(CalcultorConts.EXP, ExprConts.EXP);
		conv.put(CalcultorConts.N, ExprConts.FAC);
		conv.put(CalcultorConts.EQUAL, ExprConts.EQU);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
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
