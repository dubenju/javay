package javay.test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import javay.math.BigNum;
import javay.math.MathBn;

public class Sqrt {
//   private static float sqrt(float num, float e) {
//
//     float low = 0F;
//     float high = num;
//     float guess, e0;
//     int count = 0;
//
//     do {
//        guess = (low + high) / 2;
//        if (guess*guess > num) {
//            high = guess;
//            e0 = guess*guess - num;
//        } else {
//            low = guess;
//            e0 = num - guess*guess;
//        }
//
//        count++;
//        System.out.printf("Try %f, e: %f\n", guess, e0);
//     } while (e0 > e);
//
//     System.out.printf("Try %d times, result: %f\n", count, guess);
//
//     return guess;
//  }
//   private static float sqrtNewton(float num, float e) {
//
//     float guess = num / 2;
//     float e0;
//     int count = 0;
//
//     do {
//        guess = (guess + num / guess) / 2;
//        e0 = guess*guess - num;
//
//        count++;
//        System.out.printf("Try %f, e: %f\n", guess, e0);
//     } while (e0 > e);
//
//     System.out.printf("Try %d times, result: %f\n", count, guess);
//
//     return guess;
//  }
  // 利用BigDecimal做数值容器
  // 利用上面求出的牛顿法迭代式
  // 实现  对任意大数/任意精度实数进行开方
  static BigDecimal newtonMethodSqrt2 (BigDecimal a, int precision) {
        BigDecimal _2 = new BigDecimal(2);
        BigDecimal xn = a.divide(_2);   // x0的初始值
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        int loop = (int) Math.ceil(Math.log(precision) / Math.log(2));
        for (int i = loop; i >= 0; i--) {
          xn = xn.add(a.divide(xn, mc)).divide(_2, mc); // 上面求出的迭代式
        }
        return xn;
  }


  public static void main(String[] args) {
    System.out.println(newtonMethodSqrt2(new BigDecimal(2), 100));

//    float num = 2;
//    float e = 0.01F;
//    sqrt(num, e);
//    sqrtNewton(num, e);
//
//    num = 2;
//    e = 0.0001F;
//    sqrt(num, e);
//    sqrtNewton(num, e);
//
//    num = 2;
//    e = 0.00001F;
//    sqrt(num, e);
//    sqrtNewton(num, e);
    Sqrt proc = new Sqrt();
//    System.out.println(proc.sqrt(9));
//    System.out.println(MathBn.root(new BigNum("2"), new BigNum("2")));
    System.out.println(new BigNum("2").pow(new BigNum("-2")));
  }
  public int sqrt(int x) {
    if (x <= 1)
      return x;
    return sqrt(0, x, x);
  }

  private int sqrt(int start, int end, double target) {
    if (start >= end - 1)
      return start;
    int mid = (end - start) / 2 + start;
    double pow2 = (double)mid * (double)mid;
    if (pow2 > target)
      return sqrt(start, mid, target);
    else if (pow2 == target)
      return mid;
    else
      return sqrt(mid, end, target);
  }
}
