/**
 *
 */
package javay.util;

/**
 * @author dubenju@126.com
 */
public class DBGFloat {
    public static double debugFloat(float result) {
        String str = Integer.toBinaryString(Float.floatToIntBits(result));
        System.out.print(result +"的");
        return debugFloat(str);
    }

    public static double debugFloat(String str) {
        if (str.length() < 32) {
            StringBuffer sb = new StringBuffer();
            for (int a = 0; a < 32 - str.length(); a ++) {
                sb.append("0");
            }
            sb.append(str);
            str = sb.toString();
        }
        char[] chs = str.toCharArray();
        String sign = "";
        StringBuffer exponent = new StringBuffer();
        StringBuffer fraction = new StringBuffer();
        double mantissa = 1.0;
        for (int m = 0, n = 0; m < chs.length; m ++, n ++) {
            if (n == 0) {
                sign = String.valueOf(chs[m]);
            } else if (n < 9) {
                exponent.append(chs[m]);
            } else {
                fraction.append(chs[m]);
                if (chs[m] == '1') {
                    mantissa = mantissa + Math.pow(2, 8 - n);
                }
            }
        }
        int exp = Integer.valueOf(exponent.toString(), 2) - 127;
        int sg = 1;
        if (sign.equals("1")) {
            sg = -1;
        }
        if (exp == -127) {
            exp = -126;
            mantissa = 0.0;
        }
        double res = sg * Math.pow(2, exp) * mantissa;

        System.out.println("二进制存储[" + sign + "][" + exponent + "][" + fraction + "]=>" + sg + "×2的" + exp + "次方×" + mantissa + "=" + res);

        return res;
    }

    public static double debugDouble(double result) {
        String str = Long.toBinaryString(Double.doubleToLongBits(result));
        System.out.print(result +"的");
        return debugDouble(str);
    }

    public static double debugDouble(String str) {
        if (str.length() < 64) {
            StringBuffer sb = new StringBuffer();
            for (int a = 0; a < 64 - str.length(); a ++) {
                sb.append("0");
            }
            sb.append(str);
            str = sb.toString();
        }
        char[] chs = str.toCharArray();
        String sign = "";
        StringBuffer exponent = new StringBuffer();
        StringBuffer fraction = new StringBuffer();
        double mantissa = 1.0;
        for (int m = 0, n = 0; m < chs.length; m ++, n ++) {
            if (n == 0) {
                sign = String.valueOf(chs[m]);
            } else if (n < 12) {
                exponent.append(chs[m]);
            } else {
                fraction.append(chs[m]);
                if (chs[m] == '1') {
                    mantissa = mantissa + Math.pow(2, 11 - n);
                }
            }
        }
        int exp = Integer.valueOf(exponent.toString(), 2) - 1023;
        int sg = 1;
        if (sign.equals("1")) {
            sg = -1;
        }
        if (exp == -1023) {
            exp = -1022;
            mantissa = 0.0;
        }
        double res = sg * Math.pow(2, exp) * mantissa;

        System.out.println("二进制存储[" + sign + "][" + exponent + "][" + fraction + "]=>" + sg + "×2的" + exp + "次方×" + mantissa + "=" + res);

        return res;
    }
}
