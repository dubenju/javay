package javay.test;

import javay.math.BigNum;
import javay.math.MathBn;

/**
 * http://blog.csdn.net/mike190267481/article/details/7404702
 * @author dubenju
 *
 */
public class TestLog {

  public static double mylog(double a) {
    int N = 15;
    int k,nk;
    double x,xx,y;
    x = (a - 1) / ( a + 1 );
    xx = x*x;
    nk = 2 * N + 1;
    y = 1.0 / nk;
    for (k = N ; k > 0; k --) {
      nk = nk - 2;
      y = 1.0 / nk + xx * y;
    }
    return 2.0 * x * y;
  }
  public static void main(String[] args) {
//    double b;
//    b = mylog(2);
//    System.out.println("b=" + b);
//
//    System.out.println(Math.log(2.0));
//
//    double a = 999;
//    double c = Math.log10(a);
//    System.out.println(c);
//
//    a = 9.99;
//    c = Math.log10(a);
//    System.out.println(c + ",c-2=" + (c - 2.0));
//
//    a = 0.0999;
//    c = Math.log10(a);
//    System.out.println(c);

    BigNum a = new BigNum("0.0000000000000000000000000001");
    System.out.println(MathBn.log(a));
    System.out.println(MathBn.ln(a));
    a = new BigNum("1.2217");
    System.out.println(MathBn.log(a));
    System.out.println(MathBn.ln(a));
  }
}
