package javay.test.java;


public class TestLoop {

	public static void main(String[] args) {
		TestForCnt test = new TestForCnt();
		test.testa();
		test.testb();
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

class TestFor {
	private TestData[] data;
	public TestFor() {
		data = new TestData[10000000];
		int idx = 0;
		int max = data.length;
		for (idx = 0; idx < max; idx ++) {
			data[idx] = new TestData();
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


		for ( TestData by : data) {
			by.setBy((byte) cnt);
		}

		System.out.print((System.currentTimeMillis() - a)  + "\t");
	}
}
class TestData {
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

class TestForCnt {
	public static int MAX_CNT = 1000000;
	public void testa() {
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			for (int j = 0; j < 500; j ++) {
				;
			}
		}
		System.out.println(":" + (System.currentTimeMillis() - st));
	}
	public void testb() {
		long st = System.currentTimeMillis();
		for (int i = 0; i < MAX_CNT; i ++) {
			for (int j = 0; j < 250; j ++) {
				;
			}
		}
		System.out.println(":" + (System.currentTimeMillis() - st));
	}
}