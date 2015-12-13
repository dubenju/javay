package javay.math;

public class MathBn {
	public static final BigNum DEGREES_180 = new BigNum("180.0");
	/**
	 * 弧度变度数
	 * @param degrees
	 * @return
	 */
    public static BigNum toRadians(BigNum degrees) {
    	BigNum res = degrees.divide(DEGREES_180, 2, BigNumRound.HALF_EVENT);
        return  res.multiply(BigNum.PI);
    }
    
    /**
     * 度数变弧度
     * @param radians
     * @return
     */
    public static BigNum toDegrees(BigNum radians) {
    	BigNum res = radians.multiply(DEGREES_180);
        return res.divide(BigNum.PI, 0, BigNumRound.HALF_EVENT);
    }
}
