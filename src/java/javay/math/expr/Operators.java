package javay.math.expr;

import java.util.HashMap;
import java.util.Map;

import javay.math.BigNum;
import javay.math.BigNumRound;
import javay.math.MathBn;
import javay.swing.CalcultorConts;

public class Operators {
  private static final Map<String, Operator> ops = new HashMap<String, Operator>();
  static{
    /*
     * 优先级
     * 元数
     * 0:左右结合，1:右结合，－1:左结合
     */
    ops.put(ExprConts.AND, new Operator(ExprConts.AND, 000, 2, 0, (num1, num2)->num1.and(num2))); // AND
    ops.put(ExprConts.OR , new Operator(ExprConts.OR , 000, 2, 0, (num1, num2)->num1.or(num2))); // OR
    ops.put(ExprConts.XOR, new Operator(ExprConts.XOR, 000, 2, 0, (num1, num2)->num1.xor(num2))); // XOR

    ops.put(ExprConts.ADD, new Operator(ExprConts.ADD,  100, 2, 0, (num1, num2)->num1.add(num2))); // +
    ops.put(ExprConts.SUB, new Operator(ExprConts.SUB,  100, 2, 0, (num1, num2)->num1.subtract(num2))); // -

    ops.put(ExprConts.MUL, new Operator(ExprConts.MUL,  200, 2, 0, (num1, num2)->num1.multiply(num2))); // *
    ops.put(ExprConts.DIV, new Operator(ExprConts.DIV,  200, 2, 0, (num1, num2)->num1.divide(num2, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT))); // /
    ops.put(ExprConts.MOD, new Operator(ExprConts.MOD,  200, 2, 0, (num1, num2)->num1.mod(num2))); // mod

    ops.put(ExprConts.POW, new Operator(ExprConts.POW, 300, 2, 0, (num1, num2)->num1.pow(num2))); // ^
    ops.put(ExprConts.ROOT, new Operator(ExprConts.ROOT, 300, 2, 0, (num1, num2)->MathBn.root(num1, num2))); // root

    ops.put(ExprConts.PLUS, new Operator(ExprConts.PLUS, 400, 1, 1, (num1, num2)->num1.plus())); // +
    ops.put(ExprConts.MINUS,new Operator(ExprConts.MINUS,400, 1, 1, (num1, num2)->num1.negate())); // -
    ops.put(ExprConts.NEGATE,new Operator(ExprConts.NEGATE,400, 1, 1, (num1, num2)->num1.negate())); // -
    ops.put(ExprConts.PER, new Operator(ExprConts.PER, 400, 1, -1, (num1, num2)->num1.divide(new BigNum("100.0"), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT))); // %
    ops.put(ExprConts.FAC, new Operator(ExprConts.FAC, 400, 1, -1, (num1, num2)->num1.factorial())); // !
    
    ops.put(ExprConts.LSH, new Operator(ExprConts.LSH, 400, 2, 0, (num1, num2)->num1.lsh(num2))); // LSH
    ops.put(ExprConts.RSH, new Operator(ExprConts.RSH, 400, 2, 0, (num1, num2)->num1.rsh(num2))); // RSH
    
    ops.put(ExprConts.INT, new Operator(ExprConts.INT, 400, 1, 1, (num1, num2)->num1.integral())); // INT
    ops.put(ExprConts.SIN, new Operator(ExprConts.SIN, 400, 1, 1, (num1, num2)->MathBn.sin(num1))); // SIN
    ops.put(ExprConts.COS, new Operator(ExprConts.COS, 400, 1, 1, (num1, num2)->MathBn.cos(num1))); // COS
    ops.put(ExprConts.TAN, new Operator(ExprConts.TAN, 400, 1, 1, (num1, num2)->MathBn.tan(num1))); // TAN
    ops.put(ExprConts.EXP, new Operator(ExprConts.EXP, 400, 1, 1, (num1, num2)->MathBn.exp(num1))); // EXP
    ops.put(ExprConts.LN , new Operator(ExprConts.LN , 400, 1, 1, (num1, num2)->MathBn.ln(num1))); // LN
    ops.put(ExprConts.LOG, new Operator(ExprConts.LOG, 400, 1, 1, (num1, num2)->MathBn.log(num1))); // LOG
    ops.put(ExprConts.NOT, new Operator(ExprConts.NOT, 400, 1, 1, (num1, num2)->num1.not())); // NOT
    ops.put(ExprConts.DMS, new Operator(ExprConts.DMS, 400, 1, 1, (num1, num2)->MathBn.dms(num1))); // DMS
    ops.put(ExprConts.SMD, new Operator(ExprConts.SMD, 400, 1, 1, (num1, num2)->MathBn.smd(num1))); // SMD

    ops.put(ExprConts.LEFT, new Operator(ExprConts.LEFT, 500, 1, 1, (num1, num2)->null)); // (
    ops.put(ExprConts.RIGHT, new Operator(ExprConts.RIGHT, 500, 1, -1, (num1, num2)->null)); // )

    ops.put(ExprConts.POW + " 2 ", new Operator(ExprConts.POW + " 2 ", 400, 1, -1, (num1, num2)->num1.pow(2))); // ^ 2
    ops.put(ExprConts.POW + " 3 ", new Operator(ExprConts.POW + " 3 ", 400, 1, -1, (num1, num2)->num1.pow(3))); // ^ 3
    ops.put(ExprConts.ROOT + " 2 ", new Operator(ExprConts.ROOT + " 2 ", 400, 1, -1, (num1, num2)->MathBn.root(num1, new BigNum("2")))); // root 2
    ops.put(ExprConts.ROOT + " 3 ", new Operator(ExprConts.ROOT + " 3 ", 400, 1, -1, (num1, num2)->MathBn.root(num1, new BigNum("3")))); // root 3
    ops.put(" 1 " + ExprConts.DIV, new Operator(" 1 " + ExprConts.DIV,  400, 1, 1, (num1, num2)->new BigNum("1.0").divide(num1, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT))); // 1 /
    ops.put(ExprConts.SIN + "d", new Operator(ExprConts.SIN + "d", 400, 1, 1, (num1, num2)->MathBn.sind(num1))); // sind
    ops.put(ExprConts.SIN + "r", new Operator(ExprConts.SIN + "r", 400, 1, 1, (num1, num2)->MathBn.sin(num1))); // sinr
    ops.put(ExprConts.SIN + "g", new Operator(ExprConts.SIN + "g", 400, 1, 1, (num1, num2)->MathBn.sing(num1))); // sing
    ops.put(ExprConts.COS + "d", new Operator(ExprConts.COS + "d", 400, 1, 1, (num1, num2)->MathBn.cosd(num1))); // cosd
    ops.put(ExprConts.COS + "r", new Operator(ExprConts.COS + "r", 400, 1, 1, (num1, num2)->MathBn.cos(num1))); // cosr
    ops.put(ExprConts.COS + "g", new Operator(ExprConts.COS + "g", 400, 1, 1, (num1, num2)->MathBn.cosg(num1))); // cosg
    ops.put(ExprConts.TAN + "d", new Operator(ExprConts.TAN + "d", 400, 1, 1, (num1, num2)->MathBn.tand(num1))); // tand
    ops.put(ExprConts.TAN + "r", new Operator(ExprConts.TAN + "r", 400, 1, 1, (num1, num2)->MathBn.tan(num1))); // tanr
    ops.put(ExprConts.TAN + "g", new Operator(ExprConts.TAN + "g", 400, 1, 1, (num1, num2)->MathBn.tang(num1))); // tang

    ops.put(ExprConts.ARC + ExprConts.SIN + "d", new Operator(ExprConts.ARC + ExprConts.SIN + "d", 400, 1, 1, (num1, num2)->MathBn.arcsind(num1))); // arcsind
    ops.put(ExprConts.ARC + ExprConts.SIN + "r", new Operator(ExprConts.ARC + ExprConts.SIN + "r", 400, 1, 1, (num1, num2)->MathBn.arcsin(num1))); // arcsinr
    ops.put(ExprConts.ARC + ExprConts.SIN + "g", new Operator(ExprConts.ARC + ExprConts.SIN + "g", 400, 1, 1, (num1, num2)->MathBn.arcsing(num1))); // arcsing
    ops.put(ExprConts.ARC + ExprConts.COS + "d", new Operator(ExprConts.ARC + ExprConts.COS + "d", 400, 1, 1, (num1, num2)->MathBn.arccosd(num1))); // arccosd
    ops.put(ExprConts.ARC + ExprConts.COS + "r", new Operator(ExprConts.ARC + ExprConts.COS + "r", 400, 1, 1, (num1, num2)->MathBn.arccos(num1))); // arccosr
    ops.put(ExprConts.ARC + ExprConts.COS + "g", new Operator(ExprConts.ARC + ExprConts.COS + "g", 400, 1, 1, (num1, num2)->MathBn.arccosg(num1))); // arccosg
    ops.put(ExprConts.ARC + ExprConts.TAN + "d", new Operator(ExprConts.ARC + ExprConts.TAN + "d", 400, 1, 1, (num1, num2)->MathBn.arctand(num1))); // arctand
    ops.put(ExprConts.ARC + ExprConts.TAN + "r", new Operator(ExprConts.ARC + ExprConts.TAN + "r", 400, 1, 1, (num1, num2)->MathBn.arctan(num1))); // arctanr
    ops.put(ExprConts.ARC + ExprConts.TAN + "g", new Operator(ExprConts.ARC + ExprConts.TAN + "g", 400, 1, 1, (num1, num2)->MathBn.arctang(num1))); // arctang
  }
  private Operators() {
  }
  public static Operator getOperator(String key) {
    return ops.get(key);
  }
  public static boolean isExist(String key) {
    return ops.containsKey(key);
  }
}
