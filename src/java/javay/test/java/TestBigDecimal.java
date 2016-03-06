package javay.test.java;

import java.math.BigDecimal;

public class TestBigDecimal {

  public static void main(String[] args) {
    BigDecimal a = new BigDecimal("15.0");
    BigDecimal b = new BigDecimal("15.00");
    BigDecimal c = new BigDecimal(15);
    BigDecimal d = new BigDecimal("15.0");

    System.out.println("(a == b)=" + (a == b));
    System.out.println("a.equals(b)=" + a.equals(b));
    System.out.println("a.compareTo(b)=" + a.compareTo(b));

    System.out.println("(a == c)=" + (a == c));
    System.out.println("a.equals(c)=" + a.equals(c));
    System.out.println("a.compareTo(c)=" + a.compareTo(c));

    System.out.println("(a == d)=" + (a == d));
    System.out.println("a.equals(d)=" + a.equals(d));
    System.out.println("a.compareTo(d)=" + a.compareTo(d));
    
    BigDecimal e = new BigDecimal("test.");
  }

}
