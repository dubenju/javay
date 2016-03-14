package javay.math;

import java.util.Arrays;

import javay.util.UArys;

public class Karatsuba {

    public static void main(String[] args) {
        int[] a = {
            12, 34567890, 12345678, 90123456, 78901234
        };
        int b = 99;
        int[] c = null;
        c = Karatsuba.multiplyByInt(a, b);
        UArys.printAry2(c);
    }
    public static int[] shiftLeft(int[] mag, int n) {
        int nInts = n >>> 5;
        int nBits = n & 0x1f;
        int magLen = mag.length;
        int newMag[] = null;

        if (nBits == 0) {
            newMag = new int[magLen + nInts];
            System.arraycopy(mag, 0, newMag, 0, magLen);
        } else {
            int i = 0;
            int nBits2 = 32 - nBits;
            int highBits = mag[0] >>> nBits2;
            if (highBits != 0) {
                newMag = new int[magLen + nInts + 1];
                newMag[i++] = highBits;
            } else {
                newMag = new int[magLen + nInts];
            }
            int j=0;
            while (j < magLen-1) {
                newMag[i++] = mag[j++] << nBits | mag[j] >>> nBits2;
            }
            newMag[i] = mag[j] << nBits;
        }
        return newMag;
    }
    public static int[] multiplyByInt(int[] x, int y) {
        if (Integer.bitCount(y) == 1) {
            return shiftLeft(x, Integer.numberOfTrailingZeros(y));
        }
        int xlen = x.length;
        int[] rmag =  new int[xlen + 1];
        long carry = 0;
        long yl = y & 0xFFFFFFFFL;
        int rstart = rmag.length - 1;
        for (int i = xlen - 1; i >= 0; i--) {
            long product = (x[i] & 0xFFFFFFFFL) * yl + carry;
            rmag[rstart--] = (int) product;
            carry = product >>> 32;
            System.out.println(product + "," + carry);
        }
        if (carry == 0L) {
            // remove 0;
            rmag = Arrays.copyOfRange(rmag, 1, rmag.length);
        } else {
            rmag[rstart] = (int) carry;
        }
        return rmag;
    }
    public static int[] multiplyToLen(int[] x, int xlen, int[] y, int ylen, int[] z) {
        int xstart = xlen - 1;
        int ystart = ylen - 1;

        if (z == null || z.length < (xlen+ ylen))
            z = new int[xlen+ylen];

        long carry = 0;
        for (int j=ystart, k=ystart+1+xstart; j >= 0; j--, k--) {
            long product = (y[j] & LONG_MASK) *
                           (x[xstart] & LONG_MASK) + carry;
            z[k] = (int)product;
            carry = product >>> 32;
        }
        z[xstart] = (int)carry;

        for (int i = xstart-1; i >= 0; i--) {
            carry = 0;
            for (int j=ystart, k=ystart+1+i; j >= 0; j--, k--) {
                long product = (y[j] & LONG_MASK) *
                               (x[i] & LONG_MASK) +
                               (z[k] & LONG_MASK) + carry;
                z[k] = (int)product;
                carry = product >>> 32;
            }
            z[i] = (int)carry;
        }
        return z;
    }
	public void multiply() {
        // 递归
//        int xlen = x.mag.length;
//        int ylen = y.mag.length;
//
//        // The number of ints in each half of the number.
//        int half = (Math.max(xlen, ylen)+1) / 2;
//
//        // xl and yl are the lower halves of x and y respectively,
//        // xh and yh are the upper halves.
//        BigInteger xl = x.getLower(half);
//        BigInteger xh = x.getUpper(half);
//        BigInteger yl = y.getLower(half);
//        BigInteger yh = y.getUpper(half);
//
//        BigInteger p1 = xh.multiply(yh);  // p1 = xh*yh
//        BigInteger p2 = xl.multiply(yl);  // p2 = xl*yl
//
//        // p3=(xh+xl)*(yh+yl)
//        BigInteger p3 = xh.add(xl).multiply(yh.add(yl));
//
//        // result = p1 * 2^(32*2*half) + (p3 - p1 - p2) * 2^(32*half) + p2
//        BigInteger result = p1.shiftLeft(32*half).add(p3.subtract(p1).subtract(p2)).shiftLeft(32*half).add(p2);
//
//        if (x.signum != y.signum) {
//            return result.negate();
//        } else {
//            return result;
//        }
	}

}
