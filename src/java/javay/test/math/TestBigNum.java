package javay.test.math;

import java.math.BigDecimal;

import javay.math.BigNum;

public class TestBigNum {
	public static int MAX_CNT = 1000000;
	public static String STR = "10000000000000000000000000.0000000000000001";
	public void testAdda() {
		BigDecimal a = new BigDecimal(STR);
		BigDecimal b = new BigDecimal("0.0");
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			b = b.add(a);
		}
		System.out.println(b + ":" + (System.currentTimeMillis() - st));
	}
	public void testSubtracta() {
		BigDecimal a = new BigDecimal(STR);
		BigDecimal b = new BigDecimal("0.0");
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			b = b.subtract(a);
		}
		System.out.println(b + ":" + (System.currentTimeMillis() - st));
	}
	public void testAddb() {
		BigNum a = new BigNum(STR);
		BigNum b = new BigNum("0.0");
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			b = b.add(a);
		}
		System.out.println(b + ":" + (System.currentTimeMillis() - st));
	}
	public void testAddc() {
		BigNum a = new BigNum(STR);
		BigNum b = new BigNum("0.0");
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			b = b.addxx(a);
		}
		System.out.println(b + ":" + (System.currentTimeMillis() - st));
	}
	public void testSubtractb() {
		BigNum a = new BigNum(STR);
		BigNum b = new BigNum("0.0");
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			b = b.subtract(a);
		}
		System.out.println(b + ":" + (System.currentTimeMillis() - st));
	}
	public static void main(String[] args) {
		TestBigNum proc = new TestBigNum();
		proc.testAdda();
		proc.testAddb();
		proc.testAddc();
//		proc.testSubtracta();
//		proc.testSubtractb();
	}

}
