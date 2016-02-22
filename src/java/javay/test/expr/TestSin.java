package javay.test.expr;

import javay.math.BigNum;
import javay.math.BigNumRound;
import javay.math.MathBn;

public class TestSin {

	public static void main(String[] args) {
		BigNum a8 = new BigNum("-8.0");
		BigNum a6 = new BigNum("6.0");
		BigNum c = a8.divide(a6, 40, BigNumRound.HALF_EVENT);
		System.out.println("c=" + c);

		BigNum b2 = new BigNum("2.0");
		System.out.println("sin(2.0)=" + MathBn.sin(b2));

	}

}
