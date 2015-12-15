package javay.main;

import java.math.BigDecimal;

import sun.misc.FloatingDecimal;

public class BigDecimalTest {

	public static void main(String[] args) {
		BigDecimal b30 = new BigDecimal("30.0");
		double d30 = 30.0d;
		BigDecimal b180 = new BigDecimal("180.0");
		double d180 = 180.0d;
		BigDecimal bpi = new BigDecimal("3.1415926535897932");
		double dpi = 3.1415926535897932d;
		
		BigDecimal ba = b30.multiply(bpi);
		double da = d30 * dpi;

		double bad = ba.doubleValue();
		if (bad != da) {
			System.out.println(bad + "<>" + da);
		} else {
			System.out.println(bad + "==" + da);
		}
		
		double bad2 = FloatingDecimal.parseDouble(ba.toString());
		if (bad2 != da) {
			System.out.println(bad2 + "<>" + da);
		} else {
			System.out.println(bad2 + "==" + da);
		}
		double bad22 = Math.round(bad2);
		if (bad22 != da) {
			System.out.println(bad22 + "<>" + da);
		} else {
			System.out.println(bad22 + "==" + da);
		}
	}

}
