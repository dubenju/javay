package javay.math;

import javay.swing.CalcultorConts;

public class MathBn {
	public static final BigNum DEGREES_180 = new BigNum("180.0");
	/**
	 * 弧度变度数
	 * @param degrees
	 * @return
	 */
    public static BigNum toRadians(BigNum degrees) {
    	BigNum res = degrees.divide(DEGREES_180, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
        return  res.multiply(BigNum.PI);
    }
    
    /**
     * 度数变弧度
     * @param radians
     * @return
     */
    public static BigNum toDegrees(BigNum radians) {
    	BigNum res = radians.multiply(DEGREES_180);
        return res.divide(BigNum.PI, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
    }
    public static BigNum sin(BigNum radian) {
    	int cnt = 1;
    	return mySin(radian, 1, false, radian, 1.0, radian, cnt);
    }
    
    private static BigNum mySin(BigNum x, int n, boolean nega, BigNum numerator, double denominator, BigNum y, int cnt) {
        int m       = 2 * n;
        denominator = denominator * (m + 1) * m;
        BigNum numeratorb   = numerator.multiply(x.pow(2));
        BigNum a    = numeratorb.divide(new BigNum(denominator), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);

//        BigNum mp = new BigNum("0.00000000001");
//        if (a.compareTo(mp) <= 0) {
        double b = a.toDouble();
        if (b <= 0.0000000001) {
        	// 达到精度终止递归
            return y;
        }
        if (cnt > 11) {
        	return y;
        }
        cnt ++;
        return y.add(mySin(x, ++n, !nega, numerator, denominator, nega ? a : a.negate(), cnt));
    }
    private static double myCos(double x, int n, boolean nega, double numerator, double denominator, double y) {
        int m       = 2 * n;
        denominator = denominator * m * (m - 1);
        numerator   = numerator   * x * x;
        double a    = numerator / denominator;
        // 十分な精度になったら処理を抜ける
        if (a <= 0.00000000001) 
            return y;
        else
            return y + myCos(x, ++n, !nega, numerator, denominator, nega ? a : -a);
    }
    private static double myTan(double x, double x2, int n, double t) {
        t = x2 / (n - t);
        n -= 2;  
        if (n <= 1) 
            return x / (1 - t);
        else
            return myTan(x, x2, n, t);
    }
    private static double myExp(double x, int n, double numerator, double denominator, double y) {
        denominator = denominator * n;
        numerator   = numerator   * x;
        double a    = numerator / denominator;
        // 十分な精度になったら処理を抜ける
        if (Math.abs(a) <= 0.00000000001)
            return y;
        else
            return y + myExp(x, ++n, numerator, denominator, a);
    }
    private static double myExp(double x, double x2, int n, double t) {
        t = x2 / (n + t);
        n -= 4;  

        if (n < 6)
            return 1 + ((2 * x) / (2 - x + t));
        else
            return myExp(x, x2, n, t);
    }
    private static double myLog(double x2, double numerator, double denominator, double y) {
        denominator = denominator + 2;
        numerator   = numerator   * x2 * x2;
        double a    = numerator / denominator;
        // 十分な精度になったら処理を抜ける
        if (Math.abs(a) <= 0.00000000001)
            return y;
        else
            return y + myLog(x2, numerator, denominator, a);
    }
    private static double myLog(double x, int n, double t) {
        int    n2 = n;
        double x2 = x;
        if (n > 3) {
            if (n % 2 == 0)
                n2 = 2;
            x2 = x * (n / 2);
        }
        t = x2 / (n2 + t);

        if (n <= 2)
            return x / (1 + t);
        else
            return myLog(x, --n, t);
    }
    private static double mySinh(double x, int n, double numerator, double denominator, double y) {
        int m       = 2 * n;
        denominator = denominator * (m + 1) * m;
        numerator   = numerator   * x * x;
        double a    = numerator / denominator;
        // 十分な精度になったら処理を抜ける
        if (Math.abs(a) <= 0.00000000001) 
            return y;
        else
            return y + mySinh(x, ++n, numerator, denominator, a);
    }
    private static double myCosh(double x, int n, double numerator, double denominator, double y) {
        int m       = 2 * n;
        denominator = denominator * m * (m - 1);
        numerator   = numerator   * x * x;
        double a    = numerator / denominator;
        // 十分な精度になったら処理を抜ける
        if (Math.abs(a) <= 0.00000000001) 
            return y;
        else
            return y + myCosh(x, ++n, numerator, denominator, a);
    }
}
