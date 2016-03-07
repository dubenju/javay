package javay.test.java;

public class TestIf {

	public static void main(String[] args) {
		TestIfCnt test = new TestIfCnt();
		test.testa();
		test.testb();
		test.testc();
		test.testd();
	}

}

/**
 *      long    int
 * 10Y     2ms
 * 1KW     5ms  3ms
 * 1Y     34ms  3ms
 * 10Y   289ms  3ms
 * 100Y 2004ms 23ms
 * @author dubenju
 *
 */
class TestIfCnt {
//	public static short MAX_CNT = 10000;
	public static int MAX_CNT = 1000000000;
//	public static long MAX_CNT = 10000000L;

	public void testa() {
		long st = System.currentTimeMillis();
//		for (short i = 0; i < MAX_CNT; i ++) {
		for (int i = 0; i < MAX_CNT; i ++) {
//		for (long i = 0; i < MAX_CNT; i ++) {
		
			if (1 > -1) {
				;
			}
		}
		System.out.println(":" + (System.currentTimeMillis() - st) + "ms");
	}
	public void testb() {
		long st = System.currentTimeMillis();
//		for (short i = 0; i < MAX_CNT; i ++) {
		for (int i = 0; i < MAX_CNT; i ++) {
//		for (long i = 0; i < MAX_CNT; i ++) {
		
			if (-1 > -1) {
				;
			}
		}
		System.out.println(":" + (System.currentTimeMillis() - st) + "ms");
	}
	public void testc() {
		int a = 2;
		long st = System.currentTimeMillis();
//		for (short i = 0; i < MAX_CNT; i ++) {
		for (int i = 0; i < MAX_CNT; i ++) {
//		for (long i = 0; i < MAX_CNT; i ++) {
		
			a = (1 > -1) ? 1: 0;
		}
		System.out.println(":" + (System.currentTimeMillis() - st) + "ms");
	}
	public void testd() {
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
		for (int j = 0; j < 10; j ++) {
		
			if (1 > -1) {
				;
			}
		}
		}
		System.out.println(":" + (System.currentTimeMillis() - st) + "ms");
	}
}
