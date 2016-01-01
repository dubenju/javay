package javay.test;

import javay.math.BigNum;

public class TestNS {

	public static void main(String[] args) {
		BigNum n117 = new BigNum("117.0");
		System.out.println("117=" + n117.toBinaryString() + "," + n117.toOctalString() +"," + n117.toHexString());
//		System.out.println("0.125=" + new BigNum("0.125").toBinaryString());
//		System.out.println("117.125=" + new BigNum("117.125").toBinaryString());
		BigNum n117117 = new BigNum("117.117", 10);
		System.out.println(n117117.toBinaryString());
		BigNum n1171172 = new BigNum("+1110101.0001110111110011101101100100010110100001", 2);
		System.out.println("----------------------");
		BigNum n158= new BigNum("15.8", 16);
	}

}
