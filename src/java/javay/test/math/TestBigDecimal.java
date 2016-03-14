package javay.test.math;

public class TestBigDecimal {

	public static void main(String[] args) {
		TestBigDecimalProc proc = new TestBigDecimalProc();
		proc.testa();
	}

}
class TestBigDecimalProc {
	public void testa() {
		BigDecimal a = new BigDecimal("1234567890123456");
		BigDecimal b = new BigDecimal("7890123456789012");
		System.out.println(a.multiply(b));
	}
}
