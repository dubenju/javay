package javay.test.math;

 import java.math.BigDecimal;

import javay.math.BigNum;

public class TestBigNum {
	public static int MAX_CNT = 1000000;
	public static int MAX_CNT2 = 1000;
	public static String STR = "10000000000000000000000000.0000000000000001";
	public static String STR2 = "10000010000000000000000000000000.0000000001000001";
//	public static String STR = "10000000000000000000000000.01";
	public void testAdda() {
		BigDecimal a = new BigDecimal(STR);
		BigDecimal b = new BigDecimal(STR);
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			b = b.add(a);
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(b + ":" + ed + "ms");
		a = null;
		b = null;
	}
	public void testSubtracta() {
		BigDecimal a = new BigDecimal(STR);
		BigDecimal b = new BigDecimal(STR2);
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			b = b.subtract(a);
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(b + ":" + ed + "ms");
		a = null;
		b
		= null;
	}
	public void testMula() {
		BigDecimal a = new BigDecimal(STR);
		BigDecimal b = new BigDecimal(STR);
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT2; i ++) {
			b = b.multiply(a);
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(b + ":" + ed + "ms");
		a = null;
		b = null;
	}

	
	public void testAddb() {
		BigNum a = new BigNum(STR);
		BigNum b = new BigNum(STR);
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			b = b.add(a);
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(b + ":" + ed + "ms");
		a = null;
		b = null;
	}

	public void testSubtractb() {
		BigNum a = new BigNum(STR);
		BigNum b = new BigNum(STR2);
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			b = b.subtract(a);
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(b + ":" + ed + "ms");
		a = null;
		b = null;
	}
	public void testMulb() {
		BigNum a = new BigNum(STR);
		BigNum b = new BigNum(STR);
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT2; i ++) {
			b = b.multiply(a);
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(b + ":" + ed + "ms");
		a = null;
		b = null;
	}
	public static void main(String[] args) {
		System.gc();
		System.out.println("Fre Memory:" + ( Runtime.getRuntime().freeMemory() / (1024 * 1024) ) + "MB");
		System.out.println("Tot Memory:" + ( Runtime.getRuntime().totalMemory() / (1024 * 1024) ) + "MB");
		System.out.println("Max Memory:" + ( Runtime.getRuntime().maxMemory() / (1024 * 1024 * 1024) ) + "GB");
		TestBigNum proc = new TestBigNum();
//		proc.testAdda();
//		proc.testAddb();
//
//		proc.testSubtracta();
//		proc.testSubtractb();
		
		proc.testMula();
		proc.testMulb();
		proc = null;
		System.gc();
	}
}
