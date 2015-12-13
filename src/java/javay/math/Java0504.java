package javay.math;

/**
 * e^x
 * @author dubenju
 *
 */
public class Java0504 {
    public static void main(String []args) {
        for (int i = -10; i <= 10; i++) {
            double x  = i / 4.0;
            // 標準の指数関数
            double d1 = Math.exp(x);
            // 自作の指数関数
            double d2 = myExp(x, 1, 1.0, 1.0, 1.0);
            // 標準関数との差異
            System.out.println(String.format("%5.2f : %13.10f - %13.10f = %13.10f", x, d1, d2, d1 - d2));
        }
    }

    // 自作の指数関数
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
}