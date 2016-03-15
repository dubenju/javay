package javay.test.java;

import java.util.Arrays;

public class TestLong {

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        System.out.println(0x7FFFFFFF);
        System.out.println(0x7FFFFFFFFFFFFFFFL);
        System.out.println();

        for (int i = 2; i <= 36; i ++) {
            System.out.println(toString(Long.MAX_VALUE, i));
        }
        System.out.println();
        System.out.println(toLong("100000000000000000000000000000000000000000000000000000000000000",2));
        System.out.println(toLong("1000000000000000000000000000000000000000", 3));
        System.out.println(toLong("10000000000000000000000000000000", 4));
        System.out.println(toLong("1000000000000000000000000000", 5));
        System.out.println(toLong("1000000000000000000000000", 6));
        System.out.println(toLong("10000000000000000000000", 7));
        System.out.println(toLong("100000000000000000000", 8));
        System.out.println(toLong("10000000000000000000", 9));
        System.out.println(toLong("1000000000000000000", 10));
        System.out.println(toLong("1000000000000000000", 11));
        System.out.println(toLong("100000000000000000", 12));
        System.out.println(toLong("100000000000000000", 13));
        System.out.println(toLong("10000000000000000", 14));
        System.out.println(toLong("10000000000000000", 15));
        System.out.println(toLong("1000000000000000", 16));
        System.out.println(toLong("1000000000000000", 17));
        System.out.println(toLong("1000000000000000", 18));
        System.out.println(toLong("100000000000000", 19));
        System.out.println(toLong("100000000000000", 20));
        System.out.println(toLong("100000000000000", 21));
        System.out.println(toLong("100000000000000", 22));
        System.out.println(toLong("10000000000000", 23));
        System.out.println(toLong("10000000000000", 24));
        System.out.println(toLong("10000000000000", 25));
        System.out.println(toLong("10000000000000", 26));
        System.out.println(toLong("10000000000000", 27));
        System.out.println(toLong("10000000000000", 28));
        System.out.println(toLong("1000000000000", 29));
        System.out.println(toLong("1000000000000", 30));
        System.out.println(toLong("1000000000000", 31));
        System.out.println(toLong("1000000000000", 32));
        System.out.println(toLong("1000000000000", 33));
        System.out.println(toLong("1000000000000", 34));
        System.out.println(toLong("1000000000000", 35));
        System.out.println(toLong("1000000000000", 36));
        System.out.println();

        System.out.println(0x4000000000000000L);
        System.out.println(0x383d9170b85ff80bL);
        System.out.println(0x4000000000000000L);
        System.out.println(0x6765c793fa10079dL);
        System.out.println(0x41c21cb8e1000000L);
        System.out.println(0x3642798750226111L);
        System.out.println(0x1000000000000000L);
        System.out.println(0x12bf307ae81ffd59L);
        System.out.println( 0xde0b6b3a7640000L); // 10
        System.out.println(0x4d28cb56c33fa539L);
        System.out.println(0x1eca170c00000000L);
        System.out.println(0x780c7372621bd74dL);
        System.out.println(0x1e39a5057d810000L);
        System.out.println(0x5b27ac993df97701L);
        System.out.println(0x1000000000000000L);
        System.out.println(0x27b95e997e21d9f1L);
        System.out.println(0x5da0e1e53c5c8000L);
        System.out.println( 0xb16a458ef403f19L);
        System.out.println(0x16bcc41e90000000L);
        System.out.println(0x2d04b7fdd9c0ef49L);
        System.out.println(0x5658597bcaa24000L);
        System.out.println( 0x6feb266931a75b7L);
        System.out.println( 0xc29e98000000000L);
        System.out.println(0x14adf4b7320334b9L);
        System.out.println(0x226ed36478bfa000L);
        System.out.println(0x383d9170b85ff80bL);
        System.out.println(0x5a3c23e39c000000L);
        System.out.println( 0x4e900abb53e6b71L);
        System.out.println( 0x7600ec618141000L);
        System.out.println( 0xaee5720ee830681L);
        System.out.println(0x1000000000000000L);
        System.out.println(0x172588ad4f5f0981L);
        System.out.println(0x211e44f7d02c1000L);
        System.out.println(0x2ee56725f06e5c71L);
        System.out.println(0x41c21cb8e1000000L);
        System.out.println();
    }
    private static char[] TBL_CH= {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    public static String toString(long in, int radix) {
        long m = in;
        long a = 0;
        int b = 0;
        StringBuilder buf = new StringBuilder();
        do {
            a = m / radix;
            b = (int) (m - a * radix);
            buf.insert(0, TBL_CH[b]);
            m = a;
        } while(a > 0);
        buf.append(",len=" + buf.length() + "," + radix + "进制数));");
        return buf.toString();
    }
    public static long toLong(String str, int radix) {
        long res = 0;
        char[] dats = str.toCharArray();
        for(char ch : dats) {
            int m = Arrays.binarySearch(TBL_CH, ch);
            res = res * radix + m;
        }
        return res;
    }
}
