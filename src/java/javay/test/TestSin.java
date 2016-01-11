package javay.test;

import javay.math.BigNum;
import javay.math.BigNumRound;
import javay.math.MathBn;
import javay.swing.CalcultorConts;
/**
 * sin(30) = 0.5
 * sin(45) = 2^(1/2)/2
 * sin(60) = 3~(1/2)/2
 * @author dubenju
 *
 */
public class TestSin {

	public static void main(String[] args) {
//		BigNum a = new BigNum("30.0");
//		BigNum b = new BigNum("180.0");
//		BigNum c = a.divide(b, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
//		BigNum d = c.multiply(BigNum.PI);
//		test(d.toString());
//		BigNum f = MathBn.toRadians(a);
//		BigNum e = MathBn.sin(f);
//		System.out.println(e);
//		test2();
//		test3();
//		BigNum a = new BigNum("30.0");
//		BigNum f = MathBn.toRadians(a);
//		BigNum e = MathBn.cos(f);
		//BigNum h = MathBn.exp(new BigNum("2"));
		//MathBn.pi(0);
		//MathBn.pi2();
//		BigNum a = new BigNum("59.86");
//		BigNum b = MathBn.dms(a);
//		System.out.println(b);
//		BigNum c = MathBn.smd(b);
//		System.out.println(c);
		BigNum a = new BigNum("0.5");
		BigNum b = MathBn.arcsin(a);
		BigNum c = MathBn.arccos(a);
		BigNum d = MathBn.arctan(a);
		System.out.println("a=" + a + "/nb=" + b + "/nc=" + c + "/nd=" + d);
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
    public static void test2() {
        for (double angle = 0; angle <= 4*Math.PI; angle += Math.PI/12) {
            System.out.println(degrees(angle) + "\t" + taylorSeriesSine(angle) + "\t" + Math.sin(angle));
        }
    }
    public static void test3() {
    	BigNum angle = new BigNum("0.0");
    	BigNum maxVal = new BigNum("0.25").multiply(BigNum.PI);
    	BigNum step = BigNum.PI.divide(new BigNum("12.0"), 40, BigNumRound.HALF_EVENT);
    	System.out.print("\t\tInit=" + angle + "\t,MaxValue=" + maxVal + "\t,step=" + step);
    	for (;angle.compareTo(maxVal) <= 0;) {
    		System.out.println("\t\tsin(" +MathBn.toDegrees(angle) + ")=" + MathBn.sin(angle));//taylorSeriesSine(angle));
    		angle = angle.add(step);
    	}
    }
    public static double degrees(double radians) {
        return 180 * radians/ Math.PI;
    }
    /**
     * sin(x) = x - x^3/3! + x^5/5! - x^7/7! +...
     * @param radians
     * @return
     */
    public static BigNum taylorSeriesSine(BigNum radians) {
    	BigNum res = new BigNum("0.0");
    	BigNum a = new BigNum("-1.0");
    	BigNum sign = new BigNum("1.0");
    	for (int i = 1; i < 40; i += 2) {
    		BigNum augend = radians.pow(i).multiply(sign);
    		BigNum fac = new BigNum(i).factorial();
    		BigNum b = augend.divide(fac, 40, BigNumRound.HALF_EVENT);
    		res = res.add(b);
    		sign = sign.multiply(a);
    	}
    	return res;
    }
    public static double taylorSeriesSine(double radians) {
        double sine = 0;
        int sign = 1;
        for (int i = 1; i < 40; i+=2) {
            sine += Math.pow(radians, i) * sign / factorial(i);
            sign *= -1;
        }
        return sine;
    }
    private static double factorial(int i) {
        double result = 1;
        for (int j = 2; j <= i; j++)  {
            result *= j;
        }
        return result;
    }
}
