package javay.test.java;

import javay.test.ClaA;
import javay.util.DBGFloat;

class C{

   StringBuffer str1 = new StringBuffer("A");
   StringBuffer str2 = new StringBuffer("B");

   public void prin(){
    operate(str1,str2);
    System.out.println(str1+" "+str2);
   }

   public void operate(StringBuffer x,StringBuffer y){
    x.append(y);
    System.out.println("x "+x);
    System.out.println("y "+y);
    y=x;
    System.out.println("y "+y);
   }
  }
public class Testa {
  public static <T> void test(T[] array, T b) {
    for (int i = 0; i < array.length; i++) {
      array[i] = b;
//      if (b instanceof Float) {
//        array[i] = (T) new Integer(((Float) b).intValue());
//      }
    }
  }

  public static void main(String[] args) {
//     C c = new C();
//      c.prin();
    test();
    test2();
    test3();
    test4();

    ClaA[] a = new ClaA[3];
//    Object obj = "test";
    test(a, null);
    Float fn = null;
    if (fn instanceof Float) {
      System.out.println("Float");
    } else {
      System.out.println("none");
    }
  }

  private static void test() {
    float[] fls = {
      0.50f, 0.50f, 0.5f,   1f,  1f,
      0.5f , 0.5f , 0.5f, 0.0f,  0.5f,
      0.5f , 2.0f , 2.0f, 1.0f, 2.00f,
      1.00f, 1.00f, 0.1f, 0.1f, 0.1f,
      0.1f ,  0.1f, 0.1f, 0.1f, 0.1f,
      0.1f};

    float result = 0f;
    DBGFloat.debugFloat(result);
    for (float f : fls) {
      result += f;
      DBGFloat.debugFloat(result);
    }
    System.out.println(result);
  }
  private static void test2() {
    int a = Integer.valueOf("01000001011111100110011001101010", 2);
    DBGFloat.debugFloat(Integer.toBinaryString(a - 1));
    DBGFloat.debugFloat(Integer.toBinaryString(a));
    DBGFloat.debugFloat(Integer.toBinaryString(a + 1));
  }
  private static void test3() {
    float a1 = 15.0f;
    float a2 =  0.1f;
    float a3 = a1 + a2;
    DBGFloat.debugFloat(a1);
    DBGFloat.debugFloat(a2);
    DBGFloat.debugFloat(a3);
  }
  private static void test4() {
    double a1 = 15.0d;
    double a2 =  0.1d;
    double a3 = a1 + a2;
    DBGFloat.debugDouble(a1);
    DBGFloat.debugDouble(a2);
    DBGFloat.debugDouble(a3);
    DBGFloat.debugDouble(15.3);
  }
}
