package javay.test.math;

import javay.math.BigNum;
import javay.math.BigNumRound;
import javay.util.UArys;

public class TestBigNum2 {

	public static void main(String[] args) {
		System.out.println(Long.toString(0xFFFFFFFFL + 1));
		TestBigNum2Proc proc = new TestBigNum2Proc();
		// local var is good.
//		proc.testa();
//		proc.testb();
//		proc.testc();
//		proc.testd();
		proc.teste();

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
			c = UArys.add(a, 1);
		}
		long ed = System.currentTimeMillis() - st;
		UArys.printAry(c);
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
			c = UArys.add11(a, 1);
		}
		long ed = System.currentTimeMillis() - st;
		UArys.printAry(c);
		System.out.println(ed + "ms");
	}
	public void testc() {
		int x = 87654321;
		int n = 2345;
		x = 9000;
		n = 199;
		int b = 10;
		int bn = Integer.toString(n).length();
		int m = n * ((int) Math.pow(b, bn));
		int c = 0;
		for (int i = 0; i <= bn; i ++) {
			c = b * c;
			System.out.println("i=" + i + ",c=" + c + ",x=" + x + ",m=" + m);
			LBL_FOR2:
			for (int j = 1 ; j <= (b - 1); j ++) {
				int a = x - m;
				if (a < 0) {
					break LBL_FOR2;
				}
				c = c + 1;
				x = a;
			}
			m = m / b;
		}
		System.out.println("c=" + c + ",x=" + x + "," + (x / n));
	}
	public void testd() {
		int x = 87654321;
		int n = 2345;
//		x = 9000;
//		n = 199;
		int d = getD(n);
		int xd = x * d;
		int nd = n * d;
		System.out.println("d=" + d + ",xd=" + xd + ",nd=" + nd);
		int b = 10;
		int xn = Integer.toString(xd).length();
		int bn = Integer.toString(nd).length();
		System.out.println("xn=" + xn + ",bn=" + bn);
		int m = nd * ((int) Math.pow(b, xn - bn));
		int mh = Integer.parseInt(Integer.toString(m).substring(0, 1));
		int c = 0;
		int i = 0;
		int xh = Integer.parseInt(Integer.toString(xd).substring(0, 1));
		System.out.println("i=" + i + ",c=" + c + ",xd=" + xd + ",m=" + m + ",xh=" + xh + ",mh=" + mh);
		if (xh >= mh) {
			int a = xd - m;
			if (a >= 0) {
				c = c + 1;
				xd = a;
			}
		}
		m = m / 10;
		for (i = 1; i <= (xn - bn); i ++) {
			c = b * c;
			xh = Integer.parseInt(Integer.toString(xd).substring(0, 1));
			System.out.println("i=" + i + ",c=" + c + ",xd=" + xd + ",m=" + m);
			LBL_FOR2:
			for (int j = 1 ; j <= (b - 1); j ++) {
				int a = xd - m;
				if (a < 0) {
					break LBL_FOR2;
				}
				c = c + 1;
				xd = a;
			}
			m = m / b;
		}
		System.out.println("c=" + c + ",x=" + xd + "," + (x / n));
	}
	public int getD(int n) {
		String str = Integer.toString(n);
		int v;
		if (str.length() < 2) {
			v = Integer.parseInt(str);
		} else {
			String str1 = str.substring(0,  2);
			v = Integer.parseInt(str1);
		}
		
		int b = 10;
		int d;
		if (n < b) {
			d = b / (v + 1);
		} else {
			d = b * b / (v + 1);
		}
		return d;
	}
	public void teste() {
		int[] a = {
				2, 0, 0
		};
		int b = 2147483647;
		int[] c = UArys.divide(a, b);
		UArys.printAryL(c);
	}
}
