package javay.test.java;

public class TestArrayCopy {

	public static void main(String[] args) {
		TestArrayCopyProc proc = new TestArrayCopyProc();
		proc.testa();
	}

}
class TestArrayCopyProc {
    public static int MAX_CNT = 100000000;
    public void testa() {
        int[] a = new int[100];
        int[] b = new int[100];
        long st = System.currentTimeMillis();
        for (int j = 0; j < MAX_CNT; j ++) {
          for (int i = 0; i < a.length; i ++) {
            a[i] = 3;
          }
        }
        System.out.println((System.currentTimeMillis() - st) + "ms");

        st = System.currentTimeMillis();
        for (int j = 0; j < MAX_CNT; j ++) {
          System.arraycopy(a, 0, b, 0, a.length);
        }
        System.out.println((System.currentTimeMillis() - st) + "ms");
    }
}