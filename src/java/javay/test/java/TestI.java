package javay.test.java;

import javay.util.UBytes;

public class TestI {

	public static void main(String[] args) {
		byte[] test = new byte[4];
		test[0] = 0x00;
		test[1] = 0x01;
		test[2] = 0x73;
		test[3] = 0x44;
		System.out.println(getValue(test));
		System.out.println(UBytes.toLong(test, 2));
	}

	public static int getValue(byte[] in) {
		int res = ( ( in[0] & 0x7F ) << 21 )
				+ ( ( in[1] & 0x7F ) << 14 )
				+ ( ( in[2] & 0x7F ) << 7 )
				+ ( ( in[3] & 0x7F ) << 0 );
		return res;
	}
}
