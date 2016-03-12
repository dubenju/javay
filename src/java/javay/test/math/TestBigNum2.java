package javay.test.math;

public class TestBigNum2 {

	public static void main(String[] args) {
		long e = 10;
		for (int i = 1; i < 11; i ++) {
			String str = "";
			for (int j = 0; j < i; j ++) {
				str = str + "9";
			}
			e = Long.parseLong(str);
			System.out.println((e) + "=" + Long.toBinaryString(e));
		}
		double d = 10.0;
		for (int i = 1; i < 11; i ++) {
			d = Math.pow(10, i);
			System.out.println(i + "(" + d + ")=" + Math.log(d) / Math.log(2));
		}
		System.out.println("" + Math.log10(4294967295.0) + "," + Math.log(4294967296.0) / Math.log(2));
		for (int i = 0; i < 32; i ++) {
			StringBuilder buf = new StringBuilder();
			buf.append("0");
			for (int j = 0 ; j <= i; j ++) {
				buf.append("1");
			}
			System.out.println("2^" + (i + 1) + "=" + buf.toString() + "," + ( 2L << i )  + "," + Long.parseLong(buf.toString(), 2) + "," + Integer.MAX_VALUE);
		}
	}

}
