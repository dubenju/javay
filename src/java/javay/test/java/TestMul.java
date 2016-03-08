package javay.test.java;

public class TestMul {

	public static void main(String[] args) {
		TestMulProc proc = new TestMulProc();
		proc.testa();
		proc.testb();
	}

}
class TestMulProc {
	
	public static int MAX_CNT = 100000000;
	public void testa() {
		int a = 25 , b = 25;
		long st = System.currentTimeMillis();
		int c = a;
		for (int i = 0; i < MAX_CNT; i ++) {
			a = c * b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int mul" + ed + "ms");
	}
	public void testb() {
		int a = 25 , b = 25;
		long st = System.currentTimeMillis();
		int c = a;
		for (int i = 0; i < MAX_CNT; i ++) {
			a = 0;
			for (int j = 0; j < b; j ++) {
			  a += c;
			}
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int mul" + ed + "ms");
	}
}
