package javay.math;

import javay.swing.CalcultorConts;

import java.util.List;

/**
 * MathBn.
 * arcsinh x = x - 1/2*x^3/3 + 1*3/(2*4)*x^5/5 - …… (|x|<1)
 * arctanh x = x + x^3/3 + x^5/5 + ……(|x|<1)
 * @author dubenju
 *
 */
public class MathBn {
  public static final BigNum DEGREES_180 = new BigNum("180.0");
  public static final BigNum GRAD_200 = new BigNum("200.0");

  /**
   * 度数变弧度.
   * @param degrees BigNum
   * @return BigNum
   */
  public static BigNum toRadians(BigNum degrees) {
    BigNum res = degrees.multiply(BigNum.PI);
    return  res.divide(DEGREES_180, 40, BigNumRound.HALF_EVENT);
  }

  /**
   * 弧度变度数.
   * @param radians BigNum
   * @return BigNum
   */
  public static BigNum toDegrees(BigNum radians) {
    BigNum res = radians.multiply(DEGREES_180);
    return res.divide(BigNum.PI, 40, BigNumRound.HALF_EVENT);
  }

  /**
   * 百分度变弧度.
   * @param grad BigNum
   * @return BigNum
   */
  public static BigNum toRad(BigNum grad) {
    BigNum res = grad.multiply(BigNum.PI);
    return res.divide(GRAD_200, 40, BigNumRound.HALF_EVENT);
  }

  /**
   * 弧度变百分度.
   * @param rad BigNum
   * @return BigNum
   */
  public static BigNum toGrad(BigNum rad) {
    BigNum res = rad.multiply(GRAD_200);
    return res.divide(BigNum.PI, 40, BigNumRound.HALF_EVENT);
  }

  /**
   * sin(x) = x - x^3/3! + x^5/5! - x^7/7! +...
   * sin x = x-x^3/3!+x^5/5!-……+(-1)^(k-1)*(x^(2k-1))/(2k-1)!+……。(-∞ < x < ∞)
   * @param radian BigNum
   * @return BigNum
   */
  public static BigNum sin(BigNum radian) {
    System.out.print("★★★sin(" + radian + ")等于");
    BigNum res = new BigNum("0.0");
    BigNum an = new BigNum("-1.0");
    BigNum sign = new BigNum("1.0");

    for (int i = 1; i < 40; i += 2) {
      System.out.println("@sin:i=" + i);
      BigNum augend = radian.pow(i).multiply(sign);
      System.out.println("@sin:augend=" + augend);
      BigNum fac = new BigNum(i).factorial();
      System.out.println("@sin:fac=" + fac);
      BigNum bn = augend.divide(fac, 40, BigNumRound.HALF_EVENT);
      System.out.println("@sin:bn=augend/fac=" + bn);
      res = res.add(bn);
      System.out.println("@sin:res=" + res);
      sign = sign.multiply(an);
      System.out.println("@sin:sign=" + sign);
      System.out.println();
    }
    System.out.print(res);
    return res;
  }

  public static BigNum sind(BigNum num) {
    BigNum rn = MathBn.toRadians(num);
    return sin(rn);
  }

  public static BigNum sing(BigNum num) {
    BigNum rn = MathBn.toRad(num);
    return sin(rn);
  }

  /**
   * cos(x) = 1 - x^2/2! + x^4/4! - x^6/6! + x^8/8! - ...
   * cos x = 1-x^2/2!+x^4/4!-……+(-1)k*(x^(2k))/(2k)!+…… (-∞ < x < ∞)
   * @param radian BigNum
   * @return BigNum
   */
  public static BigNum cos(BigNum radian) {
    System.out.print("★★★cos(" + radian + ")等于");
    BigNum res = new BigNum("1.0");
    BigNum an = new BigNum("-1.0");
    BigNum sign = new BigNum("-1.0");
    for (int i = 2; i < 40; i += 2) {
      BigNum augend = radian.pow(i).multiply(sign);
      BigNum fac = new BigNum(i).factorial();
      BigNum bn = augend.divide(fac, 40, BigNumRound.HALF_EVENT);
      res = res.add(bn);
      sign = sign.multiply(an);
    }
    System.out.print(res);
    return res;
  }

  public static BigNum cosd(BigNum num) {
    BigNum rn = MathBn.toRadians(num);
    return cos(rn);
  }

  public static BigNum cosg(BigNum num) {
    BigNum rn = MathBn.toRad(num);
    return cos(rn);
  }

  /**
   * tan.
   * @param radian BigNum
   * @return BigNum
   */
  public static BigNum tan(BigNum radian) {
    BigNum x2   = radian.pow(2);
    return myTan(radian, x2, 15, new BigNum("0.0"));
  }

  private static BigNum myTan(BigNum x1, BigNum x2, int nn, BigNum tn) {
    tn = x2.divide(new BigNum(nn).subtract(tn), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
    nn -= 2;
    if (nn <= 1) {
      return x1.divide(new BigNum(1).subtract(tn), CalcultorConts.DECIMAL_LEN,
          BigNumRound.HALF_EVENT);
    }
    return myTan(x1, x2, nn, tn);
  }

  public static BigNum tand(BigNum num) {
    BigNum rn = MathBn.toRadians(num);
    return cos(rn);
  }

  public static BigNum tang(BigNum num) {
    BigNum rn = MathBn.toRad(num);
    return cos(rn);
  }

  /**
   * exp.
   * e^x=1 + x^1/1! + x^2/2! + x^3/3! + x^4/4! + x^5/5! + ...
   * e^x = 1+x+x^2/2!+x^3/3!+……+x^n/n!+……
   * @param xn BigNum
   * @return BigNum.
   */
  public static BigNum exp(BigNum xn) {
    System.out.print("★★★exp(" + xn + ")等于");
    BigNum res = new BigNum("1.0");
    BigNum an = new BigNum("1.0");
    BigNum sign = new BigNum("1.0");
    for (int i = 1; i < 40; i += 1) {
      BigNum augend = xn.pow(i).multiply(sign);
      BigNum fac = new BigNum(i).factorial();
      BigNum bn = augend.divide(fac, 40, BigNumRound.HALF_EVENT);
      res = res.add(bn);
      sign = sign.multiply(an);
    }
    System.out.print(res);
    return res;
  }

  /**
   * exp2.
   * @param xn BigNum
   * @return BigNum
   */
  public static BigNum exp2(BigNum xn) {
    BigNum x2 = xn.pow(2);
    return myExp(xn, x2, 30, new BigNum("0.0"));
  }

  private static BigNum myExp(BigNum xn, BigNum x2, int nn, BigNum tn) {
    tn = x2.divide((new BigNum(nn).add(tn)), CalcultorConts.DECIMAL_LEN, 
        BigNumRound.HALF_EVENT);
    nn -= 4;

    if (nn < 6) {
      return (xn.multiply(new BigNum(2))).divide(
          xn.add(tn).subtract(new BigNum(2)), CalcultorConts.DECIMAL_LEN, 
          BigNumRound.HALF_EVENT).add(new BigNum(1));
    }
    return myExp(xn, x2, nn, tn);
  }

  /**
   * log10N.
   * @param x BigNum
   * @return BigNum
   */
  public static BigNum log(BigNum num) {
    return ln(num).divide(BigNum.LN10, 40, BigNumRound.HALF_EVENT);
  }

  /**
   * logx.
   * @param xn BigNum
   * @return BigNum
   */
  public static BigNum logx(BigNum xn) {
    BigNum x2 = (xn.subtract(new BigNum(1))).divide((xn.add(new BigNum(1))), 
        CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
    BigNum d2 = new BigNum("2.0").multiply(myLog(x2, x2, new BigNum(1.0), x2, 1));
    return d2;
  }

  private static BigNum myLog(BigNum x2, BigNum numerator, BigNum denominator, 
      BigNum yn, int cnt) {
    denominator = denominator.add(new BigNum(2));
    numerator   = numerator.multiply(x2.pow(2));
    BigNum an  = numerator.divide(denominator, CalcultorConts.DECIMAL_LEN, 
        BigNumRound.HALF_EVENT);
    if (cnt > 11) {
      return yn;
    }
    cnt ++;
    return yn.add(myLog(x2, numerator, denominator, an, cnt));
  }

  private static BigNum myLog(BigNum xn, int nn, BigNum tn) {
    int  n2 = nn;
    BigNum x2 = xn;
    if (nn > 3) {
      if (nn % 2 == 0) {
        n2 = 2;
      }
      x2 = xn.multiply(new BigNum(nn / 2));
    }
    tn = x2.divide((new BigNum(n2).add(tn)), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);

    if (nn <= 2) {
      return xn.divide((new BigNum(1).add(tn)), CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
    }
    return myLog(xn, --nn, tn);
  }

  /**
   * log2.
   * @param xn BigNum
   * @return BigNum
   */
  public static BigNum log2(BigNum xn) {
    return myLog(xn.subtract(new BigNum(1)), 27, new BigNum(0.0));
  }

  /**
   * logeN.
   * ln(1+x)=x-x^2/2+x^3/3-……+(-1)^(k-1)*(x^k)/k(|x|<1)
   * @return BigNum
   */
  public static BigNum ln(BigNum num) {
    /*
     * 問題描述:
     * 　　如題
     * 　　泰勒展開本人覺得不是很可能，因爲當x<1後ln(x)的值隨顯得減小迅速減小趨向負無窮，
     * 這是每個展開點只能近似很小很小的一個範圍內的函數值。
     * 如果我要求任意點的ln(x)值那麽泰勒展開就很不現實，因爲展開點的選擇就成問題。
     * 參考答案:
     * 　　利用：ln(x)=2arctanh((x-1)/(x+1))
     * 　　再用：arctanh(y)= y + y^3/3 + y^5/5 + ... (y≤1)
     * 　　由于：ln(x)=y+ln(x/e^y)，(y 是任意實數)，這樣就可以通過選擇適當的 y 值使 x/e^y 盡量接近1
     */
    BigNum xn = num;
    final BigNum one = new BigNum("1.0");
    if (xn.compareTo(BigNum.ZERO) <= 0) {
      throw new ArithmeticException("Must be positive");
    }
    int kn = 0;
    int ln = 0;
    for (; xn.compareTo(new BigNum("1.0")) > 0; kn++) {
      xn = xn.divide(new BigNum("10.0"),40, BigNumRound.HALF_EVENT);
    }
    for (; xn.compareTo(new BigNum("0.1")) <= 0; kn--) {
      xn = xn.multiply(new BigNum("10"));    // ( 0.1, 1 ]
    }
    for (; xn.compareTo(new BigNum("0.9047")) < 0; ln--) {
      xn = xn.multiply(new BigNum("1.2217")); // [ 0.9047, 1.10527199 )
    }
    BigNum an = new BigNum(kn).multiply(BigNum.LN10);
    BigNum bn = new BigNum(ln).multiply(BigNum.LNR);
    BigNum res = an.add(bn);
    res = res.add(logarithm((xn.subtract(one)).divide(xn.add(one), 40, BigNumRound.HALF_EVENT)));
    return res;
  }

  private static BigNum logarithm(BigNum yn) { // y in ( -0.05-, 0.05+ ), return ln((1+y)/(1-y))
    BigNum vn = new BigNum("1.0");
    BigNum y2 = yn.pow(2);
    BigNum tn = y2;
    BigNum zn = tn.divide(new BigNum("3.0"), 40, BigNumRound.HALF_EVENT);
    for (int i = 3; zn.compareTo(BigNum.ZERO) != 0; i += 2) {
      vn = vn.add(zn);
      tn = tn.multiply(y2);
      zn = tn.divide(new BigNum(i), 40, BigNumRound.HALF_EVENT);
    }
    return new BigNum("2.0").multiply(vn.multiply(yn));
  }

  /**
   * sinhx.
   * sinh x = x+x^3/3!+x^5/5!+……+(-1)^(k-1)*(x^2k-1)/(2k-1)!+…… (-∞< x <∞)
   * @param x BigNum
   * @return BigNum
   */
  public static BigNum sinhx(BigNum xn) {
    return mySinh(xn, 1, xn, new BigNum(1.0), xn, 1);
  }

  private static BigNum mySinh(BigNum xn, int nn, BigNum numerator, 
      BigNum denominator, BigNum yn, int cnt) {
    int mn     = 2 * nn;
    long kn = (mn + 1) * mn;
    denominator = denominator.multiply(new BigNum(kn));
    numerator   = numerator.multiply(xn.pow(2));
    BigNum an  = numerator.divide(denominator, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
    if (cnt > 11) {
      return yn;
    }
    cnt ++;
    return yn.add(mySinh(xn, ++nn, numerator, denominator, an, cnt));
  }

  /**
   * coshx.
   * cosh x = 1+x^2/2!+x^4/4!+……+(-1)k*(x^2k)/(2k)!+……(-∞< x <∞)
   * @param xn BigNum
   * @return BigNum
   */
  public static BigNum coshx(BigNum xn) {
    return myCosh(xn, 1, new BigNum("1.0"), new BigNum("1.0"), new BigNum("1.0"), 1);
  }

  private static BigNum myCosh(BigNum xn, int nn, BigNum numerator, 
      BigNum denominator, BigNum yn, int cnt) {
    int mn     = 2 * nn;
    long kn = mn * (mn - 1);
    denominator = denominator.multiply(new BigNum(kn));
    numerator   = numerator.multiply(xn.pow(2));
    BigNum an  = numerator.divide(denominator, CalcultorConts.DECIMAL_LEN,
        BigNumRound.HALF_EVENT);
    if (cnt > 11) {
      return yn;
    }
    cnt ++;
    return yn.add(myCosh(xn, ++nn, numerator, denominator, an, cnt));
  }

  /**
   *   pi / 4 = 1 - 1/3 + 1/ 5 - 1/7 + ...
   * @return BigNum
   */
  public static BigNum pi(int nn) {
    BigNum one = new BigNum("1.0");
    BigNum four = new BigNum("4.0");
    BigNum res = new BigNum("1.0");
    BigNum an = new BigNum("-1.0");
    BigNum sign = new BigNum("-1.0");
    for (int i = 3; i < 40; i += 2) {
      res = res.add(one.multiply(sign).divide(new BigNum(i), 40, BigNumRound.HALF_EVENT));
      sign = sign.multiply(an);
    }
    res = res.multiply(four);
    System.out.print(res);
    return res;
  }

  /**
   * pi2.
   * pi/4 = 4 * arctan(1/5) - arctan(1/239)
   * @return BigNum
   */
  public static BigNum pi2() {
    BigNum one = new BigNum("1.0");
    BigNum four = new BigNum("4.0");
    BigNum five = new BigNum("5.0");
    BigNum n239 = new BigNum("239.0");
    BigNum an = one.divide(five, 40, BigNumRound.HALF_EVENT);
    BigNum bn = arctan(an);
    BigNum cn = four.multiply(bn);
    BigNum dn = one.divide(n239, 40, BigNumRound.HALF_EVENT);
    BigNum en = arctan(dn);
    BigNum fn = cn.subtract(en);
    BigNum res = fn.multiply(four);
    System.out.println("pi=" + res);
    return res;
  }

  /**
   * arcsin.
   * arcsin x = x+(1/2)*x^3/3+(3/8)*x^5/5 + ……(2k)!*x^(2k+1) / ((4^k)*((k!)^2))*(2k+1) (|x|<1)
   * @return BigNum
   */
  public static BigNum arcsin(BigNum num) {
    System.out.print("★★★arcsin(" + num + ")等于");
    BigNum res = new BigNum("0.0");
    BigNum one = new BigNum("1.0");
    BigNum two = new BigNum("2.0");
    BigNum four = new BigNum("4.0");
    for (int i = 0; i < 40; i ++) {
      BigNum bi = new BigNum(i);
      BigNum bi2 = bi.multiply(two);
      BigNum bi21 = bi2.add(one);
      BigNum fac = bi.factorial();
      BigNum fac2 = bi2.factorial();
      BigNum mn = fac2.multiply(num.pow(bi21));
      BigNum nn = bi21.multiply(four.pow(bi).multiply(fac.pow(2)));
      BigNum bn = mn.divide(nn, 40, BigNumRound.HALF_EVENT);
      res = res.add(bn);
    }
    System.out.print(res);
    return res;
  }

  public static BigNum arcsind(BigNum num) {
    BigNum res = arcsin(num);
    return MathBn.toDegrees(res);
  }

  public static BigNum arcsing(BigNum num) {
    BigNum res = arcsin(num);
    return MathBn.toGrad(res);
  }

  /**
   * arccos.
   * arccos x = π/2 - ( x + (1/2)*x^3/3 + (3/8)*x^5/5 + …… ) (|x|<1)
   * @return BigNum
   */
  public static BigNum arccos(BigNum num) {
    BigNum two = new BigNum("2.0");
    BigNum an = BigNum.PI.divide(two, 40, BigNumRound.HALF_EVENT);
    return an.subtract(arcsin(num));
  }

  public static BigNum arccosd(BigNum num) {
    BigNum res = arccos(num);
    return MathBn.toDegrees(res);
  }

  public static BigNum arccosg(BigNum num) {
    BigNum res = arccos(num);
    return MathBn.toGrad(res);
  }

  /**
   * arctan(x) = x - x^3/3 + x^5/5 - x^7/7 + ...(|x|<1)
   * arctan x  = x - x^3/3 + x^5/5 -……(x≤1)
   * @param num BigNum
   * @return BigNum
   */
  public static BigNum arctan(BigNum num) {
    System.out.print("★★★arctan(" + num + ")等于");
    BigNum res = new BigNum("0.0");
    BigNum an = new BigNum("-1.0");
    BigNum sign = new BigNum("1.0");
    for (int i = 1; i < 40; i += 2) {
      BigNum augend = num.pow(i).multiply(sign);
      BigNum fac = new BigNum(i);
      BigNum bn = augend.divide(fac, 40, BigNumRound.HALF_EVENT);
      res = res.add(bn);
      sign = sign.multiply(an);
    }
    System.out.print(res);
    return res;
  }

  public static BigNum arctand(BigNum num) {
    BigNum res = arctan(num);
    return MathBn.toDegrees(res);
  }

  public static BigNum arctang(BigNum num) {
    BigNum res = arctan(num);
    return MathBn.toGrad(res);
  }

  /**
   * sinh.
   * sh z=(e^z-e^(-z))/2
   * x^(2n+1) / (2n + 1)!
   * @param num BigNum
   * @return BigNum
   */
  public static BigNum sinh(BigNum num) {
    System.out.print("★★★sinh(" + num + ")等于");
    BigNum res = new BigNum("0.0");
    BigNum one = new BigNum("1.0");
    BigNum two = new BigNum("2.0");
    for (int i = 1; i < 40; i ++) {
      BigNum bi = new BigNum(i);
      BigNum bi2 = bi.multiply(two);
      BigNum bi21 = bi2.add(one);
      BigNum fac21 = bi21.factorial();
      BigNum mn = num.pow(bi21);
      BigNum bn = mn.divide(fac21, 40, BigNumRound.HALF_EVENT);
      res = res.add(bn);
    }
    System.out.print(res);
    return res;
  }

  /**
   * cosh.
   * ch z=(e^z+e^(-z))/2
   * x^(2n) / (2n)!
   * @param num BigNum
   * @return BigNum
   */
  public static BigNum cosh(BigNum num) {
    System.out.print("★★★cosh(" + num + ")等于");
    BigNum res = new BigNum("0.0");
    BigNum two = new BigNum("2.0");
    for (int i = 1; i < 40; i ++) {
      BigNum bi = new BigNum(i);
      BigNum bi2 = bi.multiply(two);
      BigNum fac2 = bi2.factorial();
      BigNum mn = num.pow(bi2);
      BigNum bn = mn.divide(fac2, 40, BigNumRound.HALF_EVENT);
      res = res.add(bn);
    }
    System.out.print(res);
    return res;
  }

  /**
   * tanh.
   * th z=sh z/ch z=(e^z-e^(-z))/(e^z+e^(-z))
   * @param num BigNum
   * @return BigNum
   */
  public static BigNum tanh(BigNum num) {
    System.out.print("★★★tanh(" + num + ")等于");
    BigNum res = new BigNum("0.0");
    res = sinh(num).divide(cosh(num), 40, BigNumRound.HALF_EVENT);
    System.out.print(res);
    return res;
  }

  /**
   * arcsinh.
   * arcsh(x)=ln[x+sqrt(x^2+1）]
   * arcsinh x = x-(1/2)*x^3/3+(3/8)*x^5/5 + ……(2k)!*x^(2k+1) / ((4^k)*((k!)^2))*(2k+1) (|x|<1)
   * @return BigNum
   */
  public static BigNum arcsinh(BigNum num) {
    System.out.print("★★★arcsin(" + num + ")等于");
    BigNum res = new BigNum("0.0");
    BigNum one = new BigNum("1.0");
    BigNum two = new BigNum("2.0");
    BigNum four = new BigNum("4.0");
    BigNum an = new BigNum("-1.0");
    BigNum sign = new BigNum("1.0");
    for (int i = 0; i < 40; i ++) {
      BigNum bi = new BigNum(i);
      BigNum bi2 = bi.multiply(two);
      BigNum bi21 = bi2.add(one);
      BigNum fac = bi.factorial();
      BigNum fac2 = bi2.factorial();
      BigNum mn = sign.multiply(fac2.multiply(num.pow(bi21)));
      BigNum nn = bi21.multiply(four.pow(bi).multiply(fac.pow(2)));
      BigNum bn = mn.divide(nn, 40, BigNumRound.HALF_EVENT);
      res = res.add(bn);
      sign = sign.multiply(an);
    }
    System.out.print(res);
    return res;
  }

  /**
   * arccosh.
   * arcch(x)=ln[x+sqrt(x^2-1）]
   * @param num BigNum
   * @return BigNum
   */
  public static BigNum arccosh(BigNum num) {
    BigNum two = new BigNum("2.0");
    BigNum one = new BigNum("1.0");
    BigNum an = num.add(MathBn.root(num.pow(two).subtract(one), two));
    return ln(an);
  }

  /**
   * arctanh.
   * arcth(x)=ln[sqrt（1-x^2）/（1-x)]=ln[（1+x)/（1-x)]/2
   * x^(2n+1) / (2n + 1)
   * @param num BigNum
   * @return BigNum
   */
  public static BigNum arctanh(BigNum num) {
    System.out.print("★★★arctanh(" + num + ")等于");
    BigNum res = new BigNum("0.0");
    BigNum one = new BigNum("1.0");
    BigNum two = new BigNum("2.0");
    for (int i = 1; i < 40; i ++) {
      BigNum bi = new BigNum(i);
      BigNum bi2 = bi.multiply(two);
      BigNum bi21 = bi2.add(one);
      BigNum mn = num.pow(bi21);
      BigNum bn = mn.divide(bi21, 40, BigNumRound.HALF_EVENT);
      res = res.add(bn);
    }
    System.out.print(res);
    return res;
  }

  /**
   * 59.86->59.5136
   * 0.86*60=51.6
   * 0.6*60=36
   * @param de BigNum
   * @return BigNum
   */
  public static BigNum dms(BigNum de) {
    BigNum n60 = new BigNum("60.0");
    BigNum n100 = new BigNum("100.0");
    BigNum n10000 = new BigNum("10000.0");
    BigNum res = de.integral(); // 度
    BigNum an = de.subtract(res);
    BigNum bn = an.multiply(n60);
    BigNum cn = bn.integral(); // 分
    BigNum dn = bn.subtract(cn);
    BigNum en = dn.multiply(n60);
    BigNum fn = en.integral(); // 秒
    res = res.add(cn.divide(n100, 40, BigNumRound.HALF_EVENT));
    res = res.add(fn.divide(n10000, 40, BigNumRound.HALF_EVENT));
    return res;
  }

  /**
   * smd.
   * 59.5136->59.86
   * 36 / 60 = 0.6
   * 51.6 / 60 = 0.86
   * @param sn BigNum
   * @return BigNum
   */
  public static BigNum smd(BigNum sn) {
    BigNum n60 = new BigNum("60.0");
    BigNum n100 = new BigNum("100.0");
    BigNum res = sn.integral(); // 度
    BigNum an = sn.subtract(res);
    BigNum bn = an.multiply(n100);
    BigNum cn = bn.integral(); // 分
    BigNum dn = bn.subtract(cn);
    BigNum en = dn.multiply(n100);
    //BigNum f = e.integral(); // 秒
    BigNum fn = en.divide(n60, 40, BigNumRound.HALF_EVENT);
    cn = cn.add(fn);
    res = res.add(cn.divide(n60, 40, BigNumRound.HALF_EVENT));
    return res;
  }

  /**
   * 牛顿迭代法 求n次方根.
   * @param num BigNum
   * @param nn BigNum
   * @return BigNum
   */
  public static BigNum root(BigNum num, BigNum nn) {
    BigNum one = new BigNum("1.0");
    BigNum cc = new BigNum("0.0000000000000000000000000000001");
    BigNum n1 = nn.subtract(one);
    BigNum x0 = num;
    BigNum x1 = n1.multiply(x0).divide(nn, 40, BigNumRound.HALF_EVENT);
    x1 = x1.add(num.divide(x0.pow(n1).multiply(nn), 40, BigNumRound.HALF_EVENT));
    BigNum chk = x1.subtract(x0);
    while (chk.abs().compareTo(cc) > 0) {
      x0 = x1;
      x1 = n1.multiply(x0).divide(nn, 40, BigNumRound.HALF_EVENT);
      x1 = x1.add(num.divide(x0.pow(n1).multiply(nn), 40, BigNumRound.HALF_EVENT));
      chk = x1.subtract(x0);
    }
    return x1;
  }

  /**
   * sum.
   * @param in List
   * @returnBigNum
   */
  public static BigNum sum(List<BigNum> in) {
    BigNum res = new BigNum("0.0");
    for (BigNum num : in) {
      res = res.add(num);
    }
    return res;
  }

  /**
   * sos.
   * @param in List
   * @return BigNum
   */
  public static BigNum sos(List<BigNum> in) {
    BigNum res = new BigNum("0.0");
    for (BigNum num : in) {
      res = res.add(num.pow(2));
    }
    return res;
  }

  /**
   * ave.
   * @param in List
   * @return BigNum
   */
  public static BigNum ave(List<BigNum> in) {
    BigNum num = sum(in);
    BigNum nn = new BigNum(in.size());
    return num.divide(nn, 40, BigNumRound.HALF_EVENT);
  }

  /**
   * rms.
   * @param in List
   * @return BigNum
   */
  public static BigNum rms(List<BigNum> in) {
    BigNum num = sos(in);
    BigNum nn = new BigNum(in.size());
    System.out.print(num);
    BigNum an = num.divide(nn, 40, BigNumRound.HALF_EVENT);
    System.out.println("," + an);
    BigNum res = root(an, new BigNum("2.0"));
    return res;
  }

  /**
   * ssd.
   * @param in List
   * @return BigNum
   */
  public static BigNum ssd(List<BigNum> in) {
    BigNum un = ave(in);
    BigNum num = new BigNum("0.0");
    for (BigNum nu : in) {
      num = num.add(nu.subtract(un).pow(2));
    }
    BigNum nn = new BigNum(in.size() - 1);
    System.out.print(num);
    BigNum an = num.divide(nn, 40, BigNumRound.HALF_EVENT);
    System.out.println("," + an);
    BigNum res = root(an, new BigNum("2.0"));
    return res;
  }

  /**
   * 总体标准差.
   * @param in List
   * @return BigNum
   */
  public static BigNum psd(List<BigNum> in) {
    BigNum un = ave(in);
    BigNum num = new BigNum("0.0");
    for (BigNum nu : in) {
      num = num.add(nu.subtract(un).pow(2));
    }
    BigNum nn = new BigNum(in.size());
    System.out.print(num);
    BigNum an = num.divide(nn, 40, BigNumRound.HALF_EVENT);
    System.out.println("," + an);
    BigNum res = root(an, new BigNum("2.0"));
    return res;
  }
}
