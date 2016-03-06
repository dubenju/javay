package javay.test.java;

public class TestIf {

	public static void main(String[] args) {
		TestIfCnt test = new TestIfCnt();
		test.testa();
		test.testb();
	}

}

class TestIfCnt {
	public static int MAX_CNT = 1000000000;
	public void testa() {
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			if (1 > -1) {
				;
			}
		}
		System.out.println(":" + (System.currentTimeMillis() - st));
	}
	public void testb() {
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			if (-1 > -1) {
				;
			}
		}
		System.out.println(":" + (System.currentTimeMillis() - st));
	}
}