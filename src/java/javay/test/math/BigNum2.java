package javay.test.math;

import javay.math.BigNum;

public class BigNum2 {
	private int[] dats;
	public BigNum2(BigNum in) {
		int[] ids = in.datas;
		int len = ids.length;
		int leno = len / 8;
		if (len % 8 != 0) {
			leno ++;
		}
		dats = new int[leno];
		long tmp = 0;
		for (int i = 0, o = leno - 1; i < ids.length; i ++) {
			tmp = tmp * 10 + ( ids[i] & 0xFFFFFFFF);
//			System.out.println("tmp=" + tmp);
			dats[o] = (int)(tmp & 0xFFFFFFFF);
			dats[o - 1] = (int)((tmp >>> 32)& 0xFFFFFFFF);
//			System.out.println("dats[o]=" + dats[o]);
//			System.out.println("dats[o - 1]=" + dats[o - 1]);
		}
		printAry(dats);
		
	}
	public static void printAry(int[] in) {
		System.out.print(" ");
		for (int i = 0; i< in.length; i++) {
			System.out.print(Integer.toHexString(in[i]).toUpperCase());
		}
		System.out.println(",len=" + in.length);
	}
	public BigNum2 add(BigNum2 in) {
		return null;
	}
	public String toString() {
		return "";
	}
}
