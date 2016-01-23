package javay.util;

import java.math.BigDecimal;

public class UDouble {

  public static double subtract(double a, double b) {
    BigDecimal ba = BigDecimal.valueOf(a);
    BigDecimal bb = BigDecimal.valueOf(b);
    return ba.subtract(bb).doubleValue();
  }

  public static double multiply(double a, double b) {
    BigDecimal ba = BigDecimal.valueOf(a);
    BigDecimal bb = BigDecimal.valueOf(b);
    return ba.multiply(bb).doubleValue();
  }

  public static double add(double a, double b) {
    BigDecimal ba = BigDecimal.valueOf(a);
    BigDecimal bb = BigDecimal.valueOf(b);
    return ba.add(bb).doubleValue();
  }

  public static double divide(double a, double b) {
    BigDecimal ba = BigDecimal.valueOf(a);
    BigDecimal bb = BigDecimal.valueOf(b);
    return (ba.divide(bb, BigDecimal.ROUND_HALF_EVEN)).doubleValue();
  }
}
