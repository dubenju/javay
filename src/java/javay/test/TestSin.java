package javay.test;

import javay.math.BigNum;
import javay.math.BigNumRound;
import javay.swing.CalcultorConts;

public class TestSin {

	public static void main(String[] args) {
		BigNum a = new BigNum("30.0");
		BigNum b = new BigNum("180.0");
		BigNum c = a.divide(b, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
		BigNum d = c.multiply(BigNum.PI);
		test(d.toString());

	}
    public static void test(String args) {
        double x = Double.parseDouble(args);

        // convert x to an angle between -2 PI and 2 PI
        x = x % (2 * Math.PI);

        // compute the Taylor series approximation
        double term = 1.0;      // ith term = x^i / i!
        double sum  = 0.0;      // sum of first i terms in taylor series

        for (int i = 1; term != 0.0; i++) {
            term *= (x / i);
            if (i % 4 == 1) sum += term;
            if (i % 4 == 3) sum -= term;
        }
        System.out.println(sum);
    }

}
