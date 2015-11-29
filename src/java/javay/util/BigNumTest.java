/**
 *
 */
package javay.util;

/**
 * @author DBJ
 *
 */
public class BigNumTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("byte" + Byte.MIN_VALUE + "...-" + Byte.MAX_VALUE); // 8
        System.out.println("short" + Short.MIN_VALUE + "...-" + Short.MAX_VALUE); // 16
        System.out.println("int" + Integer.MIN_VALUE + "...-" + Integer.MAX_VALUE); // 32
        System.out.println("long" + Long.MIN_VALUE + "..." + Long.MAX_VALUE); // 64

//        BigNum test1 = new BigNum("0");
//        System.out.println("---------------");
//        BigNum test2 = new BigNum("1");
//        System.out.println("---------------");
//        BigNum test3 = new BigNum("9");
//        System.out.println("---------------");
//        BigNum test4 = new BigNum("10");
//        for(int i = 0; i <=100; i ++) {
//            // System.out.println("---------------");
//            BigNum t = new BigNum("" + i);
//        }

//        System.out.println("---------------");
//        BigNum test4 = new BigNum("126.0257");
//        System.out.println(test4.toString());
//        System.out.println("---------------");
//        BigNum test5 = new BigNum("37");
//        System.out.println(test5.toString());
//        System.out.println("---------------");
//        BigNum test6 = new BigNum("+655.36");
//        System.out.println("---------------");
//        BigNum test7 = new BigNum("65537");
//        System.out.println("---------------");
//        BigNum test8 = test4.add(test5);
//        System.out.println(test8.toString());
//        System.out.println("---------------");
//        BigNum test9 = test4.multiply(test5);
//        System.out.println(test9.toString());
//        System.out.println("---------------");
//        System.out.println(test4.compareTo(test5));
//        System.out.println("---------------");
//        BigNum test9 = test4.divide(test5, 0, 0);
//        System.out.println(test9.toString());
//        System.out.println("---------------");
//        System.out.println(new BigNum("0.01260257").divide(test5, 0, 0));
//        System.out.println(new BigNum("0.1260257").divide(test5, 0, 0));
//        System.out.println(new BigNum("1.260257").divide(test5, 0, 0));
//        System.out.println("---------------");
//        System.out.println(new BigNum("12.60257").divide(test5, 0, 0));
//        System.out.println("---------------");
//        System.out.println(new BigNum("126.0257").divide(test5, 0, 0));
//        System.out.println(new BigNum("1260.257").divide(test5, 0, 0));
//        System.out.println(new BigNum("12602.57").divide(test5, 0, 0));
//        System.out.println(new BigNum("126025.7").divide(test5, 0, 0));
//        System.out.println(new BigNum("1260257.0").divide(test5, 0, 0));
//        System.out.println(new BigNum("12602570.0").divide(test5, 0, 0));
//
//        char[][] a = new char[2][3];
//        a[0][0] = 'a';
//        a[0][1] = 'b';
//        a[0][2] = 'c';
//        a[1][0] = 'A';
//        a[1][1] = 'B';
//        a[1][2] = 'C';
//
//        for(int y = 0; y < a[0].length; y ++) {
//            //System.out.print("y=" + y + "a=" + a.length + "a[0]=" + a[0].length);
//            for (int x = 0; x < a.length; x ++) {
//                System.out.print("a[" + x + "][" + y + "]=" + a[x][y]);
//            }
//            System.out.println();
//        }
//        System.out.println("End.");

        System.out.println(new BigNum("10").divide(new BigNum("3"), 1, 0));
    }

}
