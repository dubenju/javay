package javay.math;

/**
 *  sin x 
 * @author dubenju
 *
 */
public class Java0501 {
    public static void main(String []args) {
        for (int degree = 0; degree <= 360; degree += 15) {
            if (degree % 30 == 0 || degree % 45 == 0) {
                double radian = Math.toRadians(degree);
                // 自作の正弦関数
                double d1     = mySin(radian, 1, false, radian, 1.0, radian);
                // 標準の正弦関数
                double d2     = Math.sin(radian);
                // 標準関数との差異
                System.out.println(String.format("%3d : %13.10f - %13.10f = %13.10f", degree, d1, d2, d1 - d2));
            }
        }
    }

    // 自作の正弦関数
    private static double mySin(double x, int n, boolean nega, double numerator, double denominator, double y) {
        int m       = 2 * n;
        denominator = denominator * (m + 1) * m;
        numerator   = numerator   * x * x;
        double a    = numerator / denominator;
        // 十分な精度になったら処理を抜ける
        if (a <= 0.00000000001) 
            return y;
        else
            return y + mySin(x, ++n, !nega, numerator, denominator, nega ? a : -a);
    }
}
