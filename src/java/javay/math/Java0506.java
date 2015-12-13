package javay.math;

/**
 *  log(x)
 * @author dubenju
 *
 */
public class Java0506 {
    public static void main(String []args) {
        for (int i = 1; i <= 20; i++) {
            double x  = i / 5.0;
            // 標準の対数関数
            double d1 = Math.log(x);
            // 自作の対数関数
            double x2 = (x - 1) / (x + 1);  
            double d2 = 2 * myLog(x2, x2, 1.0, x2);
            // 標準関数との差異
            System.out.println(String.format("%5.2f : %13.10f - %13.10f = %13.10f", x, d1, d2, d1 - d2));
        }
    }

    // 自作の対数関数
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
}
