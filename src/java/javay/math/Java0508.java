package javay.math;

/**
 * sinh(x)
 * @author dubenju
 *
 */
public class Java0508 {
    public static void main(String []args) {
        for (int x = -10; x <= 10; x++) {
            // 自作の双曲線正弦関数
            double d1 = mySinh(x, 1, x, 1.0, x);
            // 標準の双曲線正弦関数
            double d2 = Math.sinh(x);
            // 標準関数との差異
            System.out.println(String.format("%3d : %17.10f - %17.10f = %13.10f", x, d1, d2, d1 - d2));
        }
    }

    // 自作の双曲線正弦関数
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
}