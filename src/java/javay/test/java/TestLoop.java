package javay.test.java;


public class TestLoop {

	public static void main(String[] args) {
		TestLoopForCnt test = new TestLoopForCnt();
//		test.testa();
//		test.testb();
//		test.testc();
		test.testd();
		test.teste();
		/*
		int count = 0;
		TestFor proc = new TestFor();
		for(int cnt = 0; cnt < 16; cnt ++) {

			proc.test1(count);
			proc.print();
			count ++;

			proc.test2(count);
			proc.print();
			count ++;

			System.out.println();
		}
		*/
	}
	
}

class TestLoopProcFor {
	private TestLoopData[] data;
	public TestLoopProcFor() {
		data = new TestLoopData[10000000];
		int idx = 0;
		int max = data.length;
		for (idx = 0; idx < max; idx ++) {
			data[idx] = new TestLoopData();
			data[idx].setBy((byte)1);
		}

//		for (TestData by : data) {
//			by = new TestData();
//			by.setBy((byte) 1);
//		} -Xmx210M -Xms4M  -verbose:gc
	}
	public void print() {
		System.out.print("data=" + data[data.length - 1] + "\t");
	}
	public void test1(int cnt) {
		long a = System.currentTimeMillis();

		int idx = 0;
		int max = data.length;
		for (idx = 0; idx < max; idx ++) {
			data[idx].setBy((byte) cnt);
		}

		System.out.print((System.currentTimeMillis() - a) + "\t");
	}
	public void test2(int cnt) {
		long a = System.currentTimeMillis();


		for ( TestLoopData by : data) {
			by.setBy((byte) cnt);
		}

		System.out.print((System.currentTimeMillis() - a)  + "\t");
	}
}
class TestLoopData {
	private byte by;

	/**
	 * @return by
	 */
	public byte getBy() {
		return by;
	}

	/**
	 * @param by
	 */
	public void setBy(byte by) {
		this.by = by;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "" + by;
	}
}

/**
 *        for 5      ; a=1+1; a = 1;
 * 100W     3ms    3ms    3ms    3ms    3ms
 * 1000W   15ms    6ms    6ms    6ms    6ms
 * 1WW    105ms   32ms   33ms   34ms   34ms
 * 10Y   1022ms  308ms  329ms  304ms  304ms
 * 100Y  9678ms 2845ms 2846ms 2828ms 2850ms
 * @author dubenju
 *
 */
class TestLoopForCnt {
	public static long MAX_CNT = 10000000000L;
	public static int MAX_CNT2 = 1000000000;
	public void testa() {
		int a = 0;
		long st = System.currentTimeMillis();
		for (long i = 0; i < MAX_CNT; i ++) {
//			for (int j = 0; j < 500; j ++) {
//				;
//			}
			a = 1;
		}
		System.out.println(":" + (System.currentTimeMillis() - st) + "ms");
	}
	public void testb() {
		int a = 0;
		long st = System.currentTimeMillis();
		for (long i = 0; i < MAX_CNT; i ++) {
//			for (int j = 0; j < 250; j ++) {
//				;
//			}
			a = 1;
		}
		System.out.println(":" + (System.currentTimeMillis() - st) + "ms");
	}
	public void testc() {
		long st = System.currentTimeMillis();
		long i = 0;
		for (;;) {
			if (i >= MAX_CNT) {
				break;
			}
			i ++;
		}
		System.out.println(":" + (System.currentTimeMillis() - st) + "ms");
	}
	
	public void testd() {
		int a = 0;
		long st = System.currentTimeMillis();
		for (long i = 0; i < MAX_CNT2; i ++) {
			a = 1;
			for (int j = 0; j < 5; j ++) {
				a ++;
			}
			
		}
		System.out.println(":" + (System.currentTimeMillis() - st) + "ms");
	}
	public void teste() {
		int a = 0;
		long st = System.currentTimeMillis();
		for (long i = 0; i < MAX_CNT2; i ++) {
			a = 1;
			for (int j = 0; j < 42; j ++) {
				a ++;
			}
			
		}
		System.out.println(":" + (System.currentTimeMillis() - st) + "ms");
	}
}