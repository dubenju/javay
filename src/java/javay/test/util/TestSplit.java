package javay.test.util;

import javay.util.UArys;

public class TestSplit {

    public static void main(String[] args) {
        byte[] a = new byte[10];
        a[0] = 1;
        a[1] = 1;
        a[2] = 2; // 0, 2
        a[3] = 1;
        a[4] = 3;
        a[5] = 2; // 3, 2
        a[6] = 4;
        a[7] = 2; // 6, 1
        a[8] = 2; // 8, 0
        a[9] = 5; // 9. 1

        byte[] b = new byte[2];
        b[0] = 2;
        b[1] = 2;


        byte[][] c = UArys.split(a, b);
        System.out.println("test");
    }

}
