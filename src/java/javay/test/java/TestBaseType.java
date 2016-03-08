package javay.test.java;

public class TestBaseType {

	public static void main(String[] args) {
		TestBaseTypeProc proc = new TestBaseTypeProc();
		proc.testb1();
		proc.testc1();
		proc.tests1();
		proc.testi1();
		proc.testl1();

		proc.testb2();
		proc.testc2();
		proc.tests2();
		proc.testi2();
		proc.testl2();
		
		proc.testb3();
		proc.testc3();
		proc.tests3();
		proc.testi3();
		proc.testl3();
		
		proc.testb4();
		proc.testc4();
		proc.tests4();
		proc.testi4();
		proc.testl4();
		
		proc.testb5();
		proc.testc5();
		proc.tests5();
		proc.testi5();
		proc.testl5();
	}

}
class TestBaseTypeProc {
	public static int MAX_CNT = 100000000;
	public void testb1() {
		byte a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a += b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "byte add" + ed + "ms");
	}
	public void testb2() {
		byte a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a -= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "byte sub" + ed + "ms");
	}
	public void testb3() {
		byte a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a *= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "byte mul" + ed + "ms");
	}
	public void testb4() {
		byte a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a /= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "byte div" + ed + "ms");
	}
	public void testb5() {
		byte a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a %= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "byte mod" + ed + "ms");
	}
	
	public void tests1() {
		short a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a += b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "short add" + ed + "ms");
	}
	public void tests2() {
		short a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a -= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "short sub" + ed + "ms");
	}
	public void tests3() {
		short a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a *= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "short mul" + ed + "ms");
	}
	public void tests4() {
		short a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a /= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "short div" + ed + "ms");
	}
	public void tests5() {
		short a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a %= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "shot mod" + ed + "ms");
	}
	
	public void testc1() {
		char a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a += b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "char add" + ed + "ms");
	}
	public void testc2() {
		char a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a -= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "char sub" + ed + "ms");
	}
	public void testc3() {
		char a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a *= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "char mul" + ed + "ms");
	}
	public void testc4() {
		char a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a /= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "char div" + ed + "ms");
	}
	public void testc5() {
		char a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a %= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "char mod" + ed + "ms");
	}
	
	public void testi1() {
		int a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a += b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int add" + ed + "ms");
	}
	public void testi2() {
		int a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a -= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int sub" + ed + "ms");
	}
	public void testi3() {
		int a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a *= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int mul" + ed + "ms");
	}
	public void testi4() {
		int a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a /= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int div" + ed + "ms");
	}
	public void testi5() {
		int a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a %= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int mod" + ed + "ms");
	}
	
	public void testl1() {
		long a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a += b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "long add" + ed + "ms");
	}
	public void testl2() {
		long a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a -= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "long sub" + ed + "ms");
	}
	public void testl3() {
		long a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a *= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "long mul" + ed + "ms");
	}
	public void testl4() {
		long a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a /= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "long div" + ed + "ms");
	}
	public void testl5() {
		long a = 1, b = 2;
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			a %= b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "long mod" + ed + "ms");
	}
}
