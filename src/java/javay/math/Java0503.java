package javay.math;

/**
 *  tan x 
 * @author dubenju
 *
 */
public class Java0503 {
    public static void main(String []args) {
        for (int degree = -90; degree <= 90; degree += 15) {
            if ((degree + 90) % 180 != 0) {
                double radian = Math.toRadians(degree);
                double x2     = radian * radian;
                // 自作の正接関数
                double d1     = myTan(radian, x2, 15, 0.0); // 15:必要な精度が得られる十分大きな奇数
                // 標準の正接関数
                double d2     = Math.tan(radian);
                // 標準関数との差異
                System.out.println(String.format("%3d : %13.10f - %13.10f = %13.10f", degree, d1, d2, d1 - d2));
            }
        }
    }

    // 自作の正接関数
    private static double myTan(double x, double x2, int n, double t) {
        t = x2 / (n - t);
        n -= 2;  
        if (n <= 1) 
            return x / (1 - t);
        else
            return myTan(x, x2, n, t);
    }
}