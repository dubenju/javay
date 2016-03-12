package javay.test;

import javay.math.BigNum;
import javay.test.math.BigDecimal;

public class TestAdd {

	public static void main(String[] args) {
		TestAddProc proc = new TestAddProc();
		proc.testa();
		proc.testb();
		proc.testc();

	}

}
class TestAddProc {
	public void testa() {
		int a = 0;
		long st = System.currentTimeMillis();
		for (int i = 1; i <= 400; i ++) {
			a = a + i;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + ",times:" + ed + "ms");
	}
	public void testb() {
		BigDecimal a = new BigDecimal("0.0");
		long st = System.currentTimeMillis();
		for (int i = 1; i <= 400; i ++) {
			a = a.add(new BigDecimal(i));
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + ",times:" + ed + "ms");
	}
	public void testc() {
		BigNum a = new BigNum("0");
//		System.out.println("10=" + a);
		long st = System.currentTimeMillis();
		for (int i = 1; i <= 400; i ++) {
			a = a.add(new BigNum("" + i));
//			System.out.println(a);
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + ",times:" + ed + "ms");
	}
}