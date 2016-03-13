package javay.test.math;

import javay.math.BigNum;
import javay.math.BigNumRound;

public class TestBigNum2 {

	public static void main(String[] args) {

//		BigNum n16 = new BigNum("16.0");
//		BigNum c = num.divide(n16, 0, BigNumRound.DOWN);
//		System.out.println("c=" + c);
//		System.out.println(new BigNum("9876543219876543219876543210.0").toHexString());
//		System.out.println(new BigNum("9999999999.0").toHexString());
		
		BigInteger bi = new BigInteger("9876541239876543129876543210" , 10);
		System.out.println(bi.toString(16).toUpperCase());
		BigNum num = new BigNum("987654123987654312987654321.0"); // 
		BigNum2 num2 = new BigNum2(num);
		
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
