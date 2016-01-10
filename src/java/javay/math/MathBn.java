package javay.math;

import javay.swing.CalcultorConts;

/**
 * arcsinh x = x - 1/2*x^3/3 + 1*3/(2*4)*x^5/5 - …… (|x|<1) 
 * arctanh x = x + x^3/3 + x^5/5 + ……(|x|<1)
 * @author dubenju
 *
 */
public class MathBn {
	public static final BigNum DEGREES_180 = new BigNum("180.0");
	public static final BigNum GRAD_200 = new BigNum("200.0");
	/**
	 * 弧度变度数
	 * @param degrees
	 * @return
	 */
    public static BigNum toRadians(BigNum degrees) {
    	BigNum res = degrees.multiply(BigNum.PI);
        return  res.divide(DEGREES_180, 40, BigNumRound.HALF_EVENT);
    }

    /**
     * 度数变弧度
     * @param radians
     * @return
     */
    public static BigNum toDegrees(BigNum radians) {
    	BigNum res = radians.multiply(DEGREES_180);
        return res.divide(BigNum.PI, 40, BigNumRound.HALF_EVENT);
    }

    public static BigNum toRad(BigNum grad) {
    	BigNum res = grad.multiply(BigNum.PI);
    	return res.divide(GRAD_200, 40, BigNumRound.HALF_EVENT);
    }

    /**
     * sin(x) = x - x^3/3! + x^5/5! - x^7/7! +...
     * sin x = x-x^3/3!+x^5/5!-……+(-1)^(k-1)*(x^(2k-1))/(2k-1)!+……。(-∞<x<∞)
     * @param radian
     * @return
     */
    public static BigNum sin(BigNum radian) {
    	System.out.print("★★★sin(" + radian + ")等于");
    	BigNum res = new BigNum("0.0");
    	BigNum a = new BigNum("-1.0");
    	BigNum sign = new BigNum("1.0");
    	for (int i = 1; i < 40; i += 2) {
    		BigNum augend = radian.pow(i).multiply(sign);
    		BigNum fac = new BigNum(i).factorial();
    		BigNum b = augend.divide(fac, 40, BigNumRound.HALF_EVENT);
    		res = res.add(b);
    		sign = sign.multiply(a);
    	}
    	System.out.print(res);
    	return res;
    }

    public static BigNum sind(BigNum num) {
    	BigNum r = MathBn.toRadians(num);
    	return sin(r);
    }
    public static BigNum sing(BigNum num) {
    	BigNum r = MathBn.toRad(num);
    	return sin(r);
    }
    /**
     * cos(x) = 1 - x^2/2! + x^4/4! - x^6/6! + x^8/8! - ...
     * cos x = 1-x^2/2!+x^4/4!-……+(-1)k*(x^(2k))/(2k)!+…… (-∞<x<∞) 
     * @param radian
     * @return
     */
    public static BigNum cos(BigNum radian) {
    	System.out.print("★★★cos(" + radian + ")等于");
    	BigNum res = new BigNum("1.0");
    	BigNum a = new BigNum("-1.0");
    	BigNum sign = new BigNum("-1.0");
    	for (int i = 2; i < 40; i += 2) {
    		BigNum augend = radian.pow(i).multiply(sign);
    		BigNum fac = new BigNum(i).factorial();
    		BigNum b = augend.divide(fac, 40, BigNumRound.HALF_EVENT);
    		res = res.add(b);
    		sign = sign.multiply(a);
    	}
    	System.out.print(res);
    	return res;
    }
    public static BigNum cosd(BigNum num) {
    	BigNum r = MathBn.toRadians(num);
    	return cos(r);
    }
    public static BigNum cosg(BigNum num) {
    	BigNum r = MathBn.toRad(num);
    	return cos(r);
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
    public static BigNum tand(BigNum num) {
    	BigNum r = MathBn.toRadians(num);
    	return cos(r);
    }
    public static BigNum tang(BigNum num) {
    	BigNum r = MathBn.toRad(num);
    	return cos(r);
    }
    /**
     * e^x=1 + x^1/1! + x^2/2! + x^3/3! + x^4/4! + x^5/5! + ...
     * e^x = 1+x+x^2/2!+x^3/3!+……+x^n/n!+…… 
     * @param x
     * @return
     */
    public static BigNum exp(BigNum x) {
    	System.out.print("★★★exp(" + x + ")等于");
    	BigNum res = new BigNum("1.0");
    	BigNum a = new BigNum("1.0");
    	BigNum sign = new BigNum("1.0");
    	for (int i = 1; i < 40; i += 1) {
    		BigNum augend = x.pow(i).multiply(sign);
    		BigNum fac = new BigNum(i).factorial();
    		BigNum b = augend.divide(fac, 40, BigNumRound.HALF_EVENT);
    		res = res.add(b);
    		sign = sign.multiply(a);
    	}
    	System.out.print(res);
    	return res;
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
     * log10N
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
     * logeN
     * ln(1+x)=x-x^2/2+x^3/3-……+(-1)^(k-1)*(x^k)/k(|x|<1) 
     * @return
     */
    public static BigNum ln() {
    	/*
    	 * 問題描述:
　　如題
　　泰勒展開本人覺得不是很可能，因爲當x<1後ln(x)的值隨顯得減小迅速減小趨向負無窮，這是每個展開點只能近似很小很小的一個範圍內的函數值。如果我要求任意點的ln(x)值那麽泰勒展開就很不現實，因爲展開點的選擇就成問題。
參考答案:
　　利用：ln(x)=2arctanh((x-1)/(x+1))
　　再用：arctanh(y)= y + y^3/3 + y^5/5 + ... (y≤1)
　　由于：ln(x)=y+ln(x/e^y)，(y 是任意實數)，這樣就可以通過選擇適當的 y 值使 x/e^y 盡量接近1 
    	 */
    	return null;
    }

    /**
     * sinh x = x+x^3/3!+x^5/5!+……+(-1)^(k-1)*(x^2k-1)/(2k-1)!+…… (-∞<x<∞) 
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
     * cosh x = 1+x^2/2!+x^4/4!+……+(-1)k*(x^2k)/(2k)!+……(-∞<x<∞) 
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
     * 	pi / 4 = 1 - 1/3 + 1/ 5 - 1/7 + ...
     * @return
     */
    public static BigNum pi(int n) {
    	BigNum one = new BigNum("1.0");
    	BigNum four = new BigNum("4.0");
    	BigNum res = new BigNum("1.0");
    	BigNum a = new BigNum("-1.0");
    	BigNum sign = new BigNum("-1.0");
    	for (int i = 3; i < 40; i += 2) {
    		res = res.add(one.multiply(sign).divide(new BigNum(i), 40, BigNumRound.HALF_EVENT));
    		sign = sign.multiply(a);
    	}
    	res = res.multiply(four);
    	System.out.print(res);
    	return res;
    }
    /**
     * pi/4 = 4 * arctan(1/5) - arctan(1/239)
     * @return
     */
    public static BigNum pi2() {
    	BigNum one = new BigNum("1.0");
    	BigNum four = new BigNum("4.0");
    	BigNum five = new BigNum("5.0");
    	BigNum n239 = new BigNum("239.0");
    	BigNum a = one.divide(five, 40, BigNumRound.HALF_EVENT);
    	BigNum b = arctan(a);
    	BigNum c = four.multiply(b);
    	BigNum d = one.divide(n239, 40, BigNumRound.HALF_EVENT);
    	BigNum e = arctan(d);
    	BigNum f = c.subtract(e);
    	BigNum res = f.multiply(four);
    	System.out.println("pi=" + res);
    	return res;
    }
    /**
     * arcsin x = x+(1/2)*x^3/3+(3/8)*x^5/5 + ……(2k)!/((4^k)*((k!)^2))*x^(2k+1)/(2k+1)(|x|<1) 
     * @return
     */
    public static BigNum arcsin() {
    	return null;
    }
    /**
     * arccos x = π/2 - ( x + (1/2)*x^3/3 + (3/8)*x^5/5 + …… ) (|x|<1) 
     * @return
     */
    public static BigNum arccos() {
    	return null;
    }
    /**
     * arctan(x) = x - x^3/3 + x^5/5 - x^7/7 + ...(|x|<1)
     * arctan x = x - x^3/3 + x^5/5 -……(x≤1) 
     * @param radian
     * @return
     */
    public static BigNum arctan(BigNum radian) {
    	System.out.print("★★★arctan(" + radian + ")等于");
    	BigNum res = new BigNum("0.0");
    	BigNum a = new BigNum("-1.0");
    	BigNum sign = new BigNum("1.0");
    	for (int i = 1; i < 40; i += 2) {
    		BigNum augend = radian.pow(i).multiply(sign);
    		BigNum fac = new BigNum(i);
    		BigNum b = augend.divide(fac, 40, BigNumRound.HALF_EVENT);
    		res = res.add(b);
    		sign = sign.multiply(a);
    	}
    	System.out.print(res);
    	return res;
    }

    /**
     * 59.86->59.5136
     * 0.86*60=51.6
     * 0.6*60=36
     * @param d
     * @return
     */
    public static BigNum dms(BigNum de) {
    	BigNum n60 = new BigNum("60.0");
    	BigNum n100 = new BigNum("100.0");
    	BigNum n10000 = new BigNum("10000.0");
    	BigNum res = de.integral(); // 度
    	BigNum a = de.subtract(res);
    	BigNum b = a.multiply(n60);
    	BigNum c = b.integral(); // 分
    	BigNum d = b.subtract(c);
    	BigNum e = d.multiply(n60);
    	BigNum f = e.integral(); // 秒
    	res = res.add(c.divide(n100, 40, BigNumRound.HALF_EVENT));
    	res = res.add(f.divide(n10000, 40, BigNumRound.HALF_EVENT));
    	return res;
    }
    /**
     * 59.5136->59.86
     * 36 / 60 = 0.6
     * 51.6 / 60 = 0.86
     * @param s
     * @return
     */
    public static BigNum smd(BigNum s) {
    	BigNum n60 = new BigNum("60.0");
    	BigNum n100 = new BigNum("100.0");
//    	BigNum n10000 = new BigNum("10000.0");
    	BigNum res = s.integral(); // 度
    	BigNum a = s.subtract(res);
    	BigNum b = a.multiply(n100);
    	BigNum c = b.integral(); // 分
    	BigNum d = b.subtract(c);
    	BigNum e = d.multiply(n100);
    	//BigNum f = e.integral(); // 秒
    	BigNum f = e.divide(n60, 40, BigNumRound.HALF_EVENT);
    	c = c.add(f);
    	res = res.add(c.divide(n60, 40, BigNumRound.HALF_EVENT));
    	return res;
    }
}
