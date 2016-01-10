package javay.awt.event;

import java.util.HashMap;
import java.util.Map;

import javay.math.expr.ExprConts;
import javay.swing.CalcultorConts;

public class Converter {
	private static final Map<String, String> conv = new HashMap<String, String>();
	private static final Map<String, String> conv2 = new HashMap<String, String>();
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
		conv.put(CalcultorConts.INT, ExprConts.INT);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
//		conv.put(CalcultorConts.MOD, ExprConts.MOD);
		conv2.put(ExprConts.SIN + "d", ExprConts.SIN + "d");
		conv2.put(ExprConts.SIN + "r", ExprConts.SIN + "r");
		conv2.put(ExprConts.SIN + "g", ExprConts.SIN + "g");
		conv2.put(ExprConts.COS + "d", ExprConts.COS + "d");
		conv2.put(ExprConts.COS + "r", ExprConts.COS + "r");
		conv2.put(ExprConts.COS + "g", ExprConts.COS + "g");
		conv2.put(ExprConts.TAN + "d", ExprConts.TAN + "d");
		conv2.put(ExprConts.TAN + "r", ExprConts.TAN + "r");
		conv2.put(ExprConts.TAN + "g", ExprConts.TAN + "g");
		conv2.put(ExprConts.DMS + "inverse", ExprConts.SMD);
	}
	public static String conv(String in, Object params) {
		String res = conv.get(in);
		Map<String, String> context = (Map<String, String>) params;
		String s = context.get(CalcultorConts.TRIGONOMETRIC_FUNCTION);
		String s2 = context.get(CalcultorConts.INVERSE);

		if (res == null) {
			res = conv2.get(in + s);;
		}
		if (res == null) {
			res = conv2.get(in + s2);;
		}
		if (res == null) {
			res = in;
		}
		return res;
	}
}
