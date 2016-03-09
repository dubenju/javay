package javay.util;

public class UInt {

  public static final int[] table = {
      1,
      2,
      4,
      8,
     16,
     32,
     64,
    128,
    256,
    512,
   1024,
   2048,
   4096,
   8192,
  16384,
  32768,
  65536,
 131072,
 262144,
 524288,
1048576,
2097152,
4194304,
8388608,
16777216,
33554432,
67108864,
134217728,
268435456,
536870912,
1073741824,
2147483647
  };

  public static final int table_last = table.length - 1;

  public static void main(String[] args) {
		int a = Integer.MAX_VALUE;
		a = 0;
		System.out.println(getLength(a));

	}

	public static int getLength(int in) {
		int start = 0, end = table_last;
		while(start <= end) {
			int mid = (start + end) >> 1;
			if (table[mid] <= in) {
				if (table[mid + 1] > in) {
					return mid + 1;
				} else if (table[mid] == in) {
					return mid;
				} else {
					start = mid + 1;
				}
			} else {
				end = mid - 1;
			}
		}
		return -1;
	}
	/**
	 * a * b
	 * @param a
	 * @param b
	 * @return
	 */
	public static long multiply(int a, int b) {

		long res = a;
		int lenb = getLength(b);
		int newb = 1 << lenb;
		System.out.println(newb);
		while () {

		}
		return res;
	}
}
