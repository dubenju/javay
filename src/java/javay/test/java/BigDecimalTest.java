package javay.test.java;

import java.math.BigDecimal;
import java.math.RoundingMode;

import sun.misc.FloatingDecimal;

public class BigDecimalTest {

  public static void main(String[] args) {
    BigDecimal test = new BigDecimal("0.0.0");
    BigDecimal b30 = new BigDecimal("30.0");
    double d30 = 30.0d;
//    BigDecimal b180 = new BigDecimal("180.0");
//    double d180 = 180.0d;
    BigDecimal bpi = new BigDecimal("3.1415926535897932");
    bpi.setScale(15, RoundingMode.HALF_EVEN);
    double dpi = 3.1415926535897932d;

    BigDecimal ba = b30.multiply(bpi);
    System.out.println("ba=" + ba.toString());
    double da = d30 * dpi;

    double bad = ba.doubleValue();
    if (bad != da) {
      System.out.println(bad + "bd<>dd" + da);
    } else {
      System.out.println(bad + "bd==dd" + da);
    }

    double bad2 = FloatingDecimal.parseDouble(ba.toString());
    if (bad2 != da) {
      System.out.println(bad2 + "bf<>dd" + da);
    } else {
      System.out.println(bad2 + "bf==dd" + da);
    }
    double da2 = Math.round(da * 100000000000000d) / 100000000000000.0d;
    if (bad2 != da2) {
      System.out.println(bad2 + "bd<>dd" + da2);
    } else {
      System.out.println(bad2 + "bd==dd" + da2);
    }
    double dbl = toDouble(ba, 14);
    if (dbl != da) {
      System.out.println(dbl + "bd<>dd" + da);
    } else {
      System.out.println(dbl + "bd==dd" + da);
    }
  }

  public static double toDouble(BigDecimal bd, int scale) {
    double res = 0.0;
    String str = bd.toString();
    int idx_pot = str.indexOf('.');
    if (idx_pot > 0) {
    } else {
      idx_pot = 0;
    }
    byte[] bys = str.getBytes();

    int start_pos = idx_pot + scale;
    if (start_pos >= bys.length) {
      start_pos = bys.length - 1;
    }
    double a = 1.0;
    int c = 0;
    for(int i = start_pos; i >= 0; i --) {
      byte by = bys[i];
      if ((char) by == '.') {
        a = 1.0;
        c = -1;
      } else {
        a = Math.pow(10.0, idx_pot - i + c);
        double b = a * (by - '0');
        res = res + b;
      }
    }
    return res;
  }
}
