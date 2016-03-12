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
		for (int i = 0; i < ids.length; i ++) {
			
		}
		
	}
	public BigNum2 add(BigNum2 in) {
		return null;
	}
	public String toString() {
		return "";
	}
}
