package javay.math;

import javay.test.math.BigInteger;

public class Karatsuba {

	public static void main(String[] args) {

	}
	public void multiply() {
        int xlen = x.mag.length;
        int ylen = y.mag.length;

        // The number of ints in each half of the number.
        int half = (Math.max(xlen, ylen)+1) / 2;

        // xl and yl are the lower halves of x and y respectively,
        // xh and yh are the upper halves.
        BigInteger xl = x.getLower(half);
        BigInteger xh = x.getUpper(half);
        BigInteger yl = y.getLower(half);
        BigInteger yh = y.getUpper(half);

        BigInteger p1 = xh.multiply(yh);  // p1 = xh*yh
        BigInteger p2 = xl.multiply(yl);  // p2 = xl*yl

        // p3=(xh+xl)*(yh+yl)
        BigInteger p3 = xh.add(xl).multiply(yh.add(yl));

        // result = p1 * 2^(32*2*half) + (p3 - p1 - p2) * 2^(32*half) + p2
        BigInteger result = p1.shiftLeft(32*half).add(p3.subtract(p1).subtract(p2)).shiftLeft(32*half).add(p2);

        if (x.signum != y.signum) {
            return result.negate();
        } else {
            return result;
        }
	}

}
