package javay.test.java;

public class TestMod2 {

	public static void main(String[] args) {
		TestMod2Proc proc = new TestMod2Proc();
		proc.testa();
		proc.testb();
	}

}
class TestMod2Proc {
	
	public static int MAX_CNT = 100000000;
	public void testa() {
		int a = 25 , b = 3;
		long st = System.currentTimeMillis();
		int c = a;
		for (int i = 0; i < MAX_CNT; i ++) {
			a = c % b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int mod" + ed + "ms");
	}
	public void testb() {
		int a = 25 , b = 3;
		long st = System.currentTimeMillis();
		int c = a;
		int d = 0;
		for (int i = 0; i < MAX_CNT; i ++) {
			d = (c / b) * b;
			a = c - d;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int mod" + ed + "ms");
	}
}
