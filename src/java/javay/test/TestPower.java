package javay.test;

public class TestPower {

  public static void main(String[] args) {
    double a = 2.3;
    double c = Math.pow(a, a);
    System.out.println("a^a=" + c);

    double b = 2.5;
    double d = Math.pow(a, 2);
    System.out.println("a^2=" + d);
    
    b = Math.pow(a, 0.3);
    System.out.println("a^0.3=" + b);
    double f = Math.sqrt(a);
    System.out.println(f);

    double e = b * d;
    System.out.println(e);
    
    
  }

}
