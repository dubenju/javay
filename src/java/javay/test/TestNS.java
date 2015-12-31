package javay.test;

import javay.math.BigNum;

public class TestNS {

	public static void main(String[] args) {
		BigNum n117 = new BigNum("117.0");
		System.out.println("117=" + n117.toBinaryString() + "," + n117.toOctalString() +"," + n117.toHexString());
//		System.out.println("0.125=" + new BigNum("0.125").toBinaryString());
//		System.out.println("117.125=" + new BigNum("117.125").toBinaryString());
	}

}
