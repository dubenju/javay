package javay.test.java;

public class TestDiv {

	public static void main(String[] args) {
		TestDivProc proc = new TestDivProc();
		proc.testa();
		proc.testb();
	}

}
class TestDivProc {
	
	public static int MAX_CNT = 100000000;
	public void testa() {
		int a = 250 , b = 25;
		long st = System.currentTimeMillis();
		int c = a;
		for (int i = 0; i < MAX_CNT; i ++) {
			a = c / b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int div" + ed + "ms");
	}
	public void testb() {
		int a = 250 , b = 25;
		long st = System.currentTimeMillis();
		int c = a;
		int d = a;
		for (int i = 0; i < MAX_CNT; i ++) {
			c = d;
			a = 0;
			while (c >= b) {
			 c -= b;
			 a ++;
			}
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int div" + ed + "ms");
	}
}
