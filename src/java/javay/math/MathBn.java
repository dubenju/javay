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
    	BigNum res = degrees.multiply(BigNum.PI);
        return  res.divide(DEGREES_180, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
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

    /**
     *
     * @param radian
     * @return
     */
    public static BigNum sin(BigNum radian) {
    	System.out.println("★★★sin(" + radian + ")等于");
    	return mySin(radian, 1, false, radian, new BigNum("1.0"), radian, 1);
    }

    private static BigNum mySin(BigNum x, int n, boolean nega, BigNum numerator, BigNum denominator, BigNum y, int cnt) {
        System.out.println("cnt=" + cnt + ",x=" + x + ",n=" + n + ",nega=" + nega + ",numerator=" + numerator + ",denominator=" + denominator + ",y=" + y);
    	int m       = 2 * n;
        long k = (m + 1) * m;
        denominator = denominator.multiply(new BigNum(k));

        BigNum numeratorb   = numerator.multiply(x.pow(2));
        BigNum a    = numeratorb.divide(new BigNum(denominator), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);

        if (cnt > 11) {
        	return y;
        }
        cnt ++;
        return y.add(mySin(x, ++n, !nega, numerator, denominator, nega ? a : a.negate(), cnt));
    }
    public static BigNum cos(BigNum radian) {
    	return myCos(radian, 1, false, new BigNum("1.0"), new BigNum("1.0"), new BigNum("1.0"), 1);
    }
    private static BigNum myCos(BigNum x, int n, boolean nega, BigNum numerator, BigNum denominator, BigNum y, int cnt) {
        int m       = 2 * n;
        long k = m * (m - 1);
        denominator = denominator.multiply(new BigNum(k));
        BigNum numeratorb   = numerator.multiply(x.pow(2));
        BigNum a    = numeratorb.divide(new BigNum(denominator), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
        if (cnt > 11) {
        	return y;
        }
        cnt ++;
        return y.add(myCos(x, ++n, !nega, numerator, denominator, nega ? a : a.negate(), cnt));
    }

    /**
     *
     * @param radian
     * @return
     */
    public static BigNum tan(BigNum radian) {
    	BigNum x2     = radian.pow(2);
    	return  myTan(radian, x2, 15, new BigNum("0.0"));
    }
    private static BigNum myTan(BigNum x, BigNum x2, int n, BigNum t) {
        t = x2.divide(new BigNum(n).subtract(t), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
        n -= 2;
        if (n <= 1) {
            return x.divide(new BigNum(1).subtract(t), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
        }
        return myTan(x, x2, n, t);
    }

    /**
     *
     * @param x
     * @return
     */
    public static BigNum exp(BigNum x) {
    	return myExp(x, 1, new BigNum("1.0"), new BigNum("1.0"), new BigNum("1.0"), 1);
    }
    private static BigNum myExp(BigNum x, int n, BigNum numerator, BigNum denominator, BigNum y, int cnt) {
        denominator = denominator.multiply(new BigNum(n));
        numerator   = numerator.multiply(x);
        BigNum a    = numerator.divide(denominator, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
        if (cnt > 11) {
            return y;
        }
        cnt ++;
        return y.add(myExp(x, ++n, numerator, denominator, a, cnt));
    }

    /**
     *
     * @param x
     * @return
     */
    public static BigNum exp2(BigNum x) {
    	BigNum x2 = x.pow(2);
    	return myExp(x, x2, 30, new BigNum("0.0"));
    }
    private static BigNum myExp(BigNum x, BigNum x2, int n, BigNum t) {
        t = x2.divide((new BigNum(n).add(t)), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
        n -= 4;

        if (n < 6) {
            return (x.multiply(new BigNum(2))).divide(x.add(t).subtract(new BigNum(2)), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT).add(new BigNum(1));
        }
        return myExp(x, x2, n, t);
    }

    /**
     *
     * @param x
     * @return
     */
    public static BigNum log(BigNum x) {
    	BigNum x2 = (x.subtract(new BigNum(1))).divide((x.add(new BigNum(1))), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
    	BigNum d2 = new BigNum(2).multiply(myLog(x2, x2, new BigNum(1.0), x2, 1));
    	return d2;
    }
    private static BigNum myLog(BigNum x2, BigNum numerator, BigNum denominator, BigNum y, int cnt) {
        denominator = denominator.add(new BigNum(2));
        numerator   = numerator.multiply(x2.pow(2));
        BigNum a    = numerator.divide(denominator, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
        if (cnt > 11) {
            return y;
        }
        cnt ++;
        return y.add(myLog(x2, numerator, denominator, a, cnt));
    }

    /**
     *
     * @param x
     * @return
     */
    public static BigNum log2(BigNum x) {
    	return myLog(x.subtract(new BigNum(1)), 27, new BigNum(0.0));
    }
    private static BigNum myLog(BigNum x, int n, BigNum t) {
        int    n2 = n;
        BigNum x2 = x;
        if (n > 3) {
            if (n % 2 == 0)
                n2 = 2;
            x2 = x.multiply(new BigNum(n / 2));
        }
        t = x2.divide((new BigNum(n2).add(t)), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);

        if (n <= 2) {
            return x.divide((new BigNum(1).add(t)), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
        }
        return myLog(x, --n, t);
    }

    /**
     *
     * @param x
     * @return
     */
    public static BigNum sinh(BigNum x) {
    	return mySinh(x, 1, x, new BigNum(1.0), x, 1);
    }
    private static BigNum mySinh(BigNum x, int n, BigNum numerator, BigNum denominator, BigNum y, int cnt) {
        int m       = 2 * n;
        long k = (m + 1) * m;
        denominator = denominator.multiply(new BigNum(k));
        numerator   = numerator.multiply(x.pow(2));
        BigNum a    = numerator.divide(denominator, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
        if (cnt > 11) {
            return y;
        }
        cnt ++;
        return y.add(mySinh(x, ++n, numerator, denominator, a, cnt));
    }

    /**
     *
     * @param x
     * @return
     */
    public static BigNum cosh(BigNum x) {
    	return myCosh(x, 1, new BigNum("1.0"), new BigNum("1.0"), new BigNum("1.0"), 1);
    }
    private static BigNum myCosh(BigNum x, int n, BigNum numerator, BigNum denominator, BigNum y, int cnt) {
        int m       = 2 * n;
        long k = m * (m - 1);
        denominator = denominator.multiply(new BigNum(k));
        numerator   = numerator.multiply(x.pow(2));
        BigNum a    = numerator.divide(denominator, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
        if (cnt > 11) {
            return y;
        }
        cnt ++;
        return y.add(myCosh(x, ++n, numerator, denominator, a, cnt));
    }

    /**
     * 59.86->59.5136
     * @param d
     * @return
     */
    public static BigNum dms(BigNum d) {
    	BigNum res = null;
    	return res;
    }
    /**
     * 59.51366->59.8
     * @param s
     * @return
     */
    public static BigNum smd(BigNum s) {
    	BigNum res = null;
    	return res;
    }
}
