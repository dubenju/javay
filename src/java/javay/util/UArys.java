package javay.util;

public class UArys {
	public static void printAry(int[] in) {
		System.out.print(" ");
		for (int i = 0; i< in.length; i++) {
			System.out.print(Integer.toHexString(in[i]).toUpperCase());
		}
		System.out.println(",len=" + in.length);
	}
	public static void printAry2(int[] in) {
		System.out.print(" ");
		for (int i = 0; i< in.length; i++) {
			System.out.print((in[i]&0xFFFFFFFFL) + " ");
		}
		System.out.println(",len=" + in.length);
	}
	public static void printAry3(int[] in) {
		System.out.print(" ");
		for (int i = 0; i< in.length; i++) {
			System.out.print((in[i]) + " ");
		}
		System.out.println(",len=" + in.length);
	}
}
