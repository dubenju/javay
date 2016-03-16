package javay.test.math;

import javay.math.BigNum;
import javay.math.BigNumRound;
import javay.util.UArys;

public class TestBigNum2 {

	public static void main(String[] args) {
		TestBigNum2Proc proc = new TestBigNum2Proc();
		// local var is good.
		proc.testa();
		proc.testb();

//		BigNum n16 = new BigNum("16.0");
//		BigNum c = num.divide(n16, 0, BigNumRound.DOWN);
//		System.out.println("c=" + c);
//		System.out.println(new BigNum("9876543219876543219876543210.0").toHexString());
//		System.out.println(new BigNum("9999999999.0").toHexString());
		
//		BigInteger bi = new BigInteger("9876541239876543129876543210" , 10);
//		System.out.println(bi.toString(16).toUpperCase());
//		BigNum num = new BigNum("987654123987654312987654321.0"); // 
//		BigNum2 num2 = new BigNum2(num);
//		BigNum numa = new BigNum("9.0"); //
//		BigNum2 num2a = new BigNum2(numa);
//		BigNum2 num2s = num2.add(num2a);
		
//		long e = 10;
//		for (int i = 1; i < 11; i ++) {
//			String str = "";
//			for (int j = 0; j < i; j ++) {
//				str = str + "9";
//			}
//			e = Long.parseLong(str);
//			System.out.println((e) + "=" + Long.toBinaryString(e));
//		}
//		double d = 10.0;
//		for (int i = 1; i < 11; i ++) {
//			d = Math.pow(10, i);
//			System.out.println(i + "(" + d + ")=" + Math.log(d) / Math.log(2));
//		}
//		System.out.println("" + Math.log10(4294967295.0) + "," + Math.log(4294967296.0) / Math.log(2));
//		for (int i = 0; i < 32; i ++) {
//			StringBuilder buf = new StringBuilder();
//			buf.append("0");
//			for (int j = 0 ; j <= i; j ++) {
//				buf.append("1");
//			}
//			System.out.println("2^" + (i + 1) + "=" + buf.toString() + "," + ( 2L << i )  + "," + Long.parseLong(buf.toString(), 2) + "," + Integer.MAX_VALUE);
//		}
	}

}
class TestBigNum2Proc {
	public static int MAX_CNT = 100000000;
	public void testa() {
		int[] a = new int[3];
		for (int i = 0; i < a.length; i ++) {
			a[i] = Integer.MAX_VALUE;
		}
		long st = System.currentTimeMillis();
		int[] c= null;
		for (int j = 0; j < MAX_CNT; j ++) {
			c = BigNum2.add1(a, 1);
		}
		long ed = System.currentTimeMillis() - st;
		UArys.printAry3(c);
		System.out.println(ed + "ms");
	}
	public void testb() {
		int[] a = new int[3];
		for (int i = 0; i < a.length; i ++) {
			a[i] = Integer.MAX_VALUE;
		}
		long st = System.currentTimeMillis();
		int[] c= null;
		for (int j = 0; j < MAX_CNT; j ++) {
			c = BigNum2.add11(a, 1);
		}
		long ed = System.currentTimeMillis() - st;
		UArys.printAry3(c);
		System.out.println(ed + "ms");
	}
}
