package javay.math;

import java.util.Arrays;

import javay.test.math.BigInteger;
import javay.test.math.BigNum2;
import javay.util.UArys;

public class Karatsuba {

    public static void main(String[] args) {
        String str = "1234567890123456789012345678901234.0";
        BigNum num = new BigNum(str);
        BigNum2 num2 = new BigNum2(num);

        int[] a = {
            0, 155824, 1610344444, -1323851740, -236789900
        };
        int b = 99;
        int[] c = null;
        c = Karatsuba.multiplyByInt(a, b);
        UArys.printAry2(c);
        int[] d = { 99 };
        int[] e = new int[6];
        e = multiplyToLen(a, a.length, d, d.length, e);
        UArys.printAry2(c);
    }
    public static int[] add(int[] x, int[] y) {
        // If x is shorter, swap the two arrays
        if (x.length < y.length) {
            int[] tmp = x;
            x = y;
            y = tmp;
        }

        int xIndex = x.length;
        int yIndex = y.length;
        int result[] = new int[xIndex];
        long sum = 0;
        if (yIndex == 1) {
            sum = (x[--xIndex] & 0xFFFFFFFFL) + (y[0] & 0xFFFFFFFFL) ;
            result[xIndex] = (int)sum;
        } else {
            // Add common parts of both numbers
            while (yIndex > 0) {
                sum = (x[--xIndex] & 0xFFFFFFFFL) + (y[--yIndex] & 0xFFFFFFFFL) + (sum >>> 32);
                result[xIndex] = (int) sum;
            }
        }
        // Copy remainder of longer number while carry propagation is required
        boolean carry = (sum >>> 32 != 0);
        while (xIndex > 0 && carry) {
            carry = ((result[--xIndex] = x[xIndex] + 1) == 0);
        }

        // Copy remainder of longer number
        while (xIndex > 0) {
            result[--xIndex] = x[xIndex];
        }

        // Grow result if necessary
        if (carry) {
            int bigger[] = new int[result.length + 1];
            System.arraycopy(result, 0, bigger, 1, result.length);
            bigger[0] = 0x01;
            return bigger;
        }
        return result;
    }
    private static int[] subtract(int[] big, int[] little) {
        int bigIndex = big.length;
        int result[] = new int[bigIndex];
        int littleIndex = little.length;
        long difference = 0;

        // Subtract common parts of both numbers
        while (littleIndex > 0) {
            difference = (big[--bigIndex] & 0xFFFFFFFFL) -
                         (little[--littleIndex] & 0xFFFFFFFFL) +
                         (difference >> 32);
            result[bigIndex] = (int)difference;
        }

        // Subtract remainder of longer number while borrow propagates
        boolean borrow = (difference >> 32 != 0);
        while (bigIndex > 0 && borrow)
            borrow = ((result[--bigIndex] = big[bigIndex] - 1) == -1);

        // Copy remainder of longer number
        while (bigIndex > 0)
            result[--bigIndex] = big[bigIndex];

        return result;
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

    public static int[] multiplyToLen(int[] x, int xlen, int[] y, int ylen, int[] z) {
        int xstart = xlen - 1;
        int ystart = ylen - 1;

        if (z == null || z.length < (xlen + ylen)) {
            z = new int[xlen + ylen];
        }

        long carry = 0;
        for (int j = ystart, k = ystart + 1 + xstart; j >= 0; j --, k --) {
            long product = (y[j] & 0xFFFFFFFFL) * (x[xstart] & 0xFFFFFFFFL) + carry;
            z[k] = (int) product;
            carry = product >>> 32;
        }
        z[xstart] = (int) carry;

        for (int i = xstart - 1; i >= 0; i --) {
            carry = 0;
            for (int j = ystart, k = ystart + 1 + i; j >= 0; j --, k --) {
                long product = (y[j] & 0xFFFFFFFFL) * (x[i] & 0xFFFFFFFFL) + (z[k] & 0xFFFFFFFFL) + carry;
                z[k] = (int) product;
                carry = product >>> 32;
            }
            z[i] = (int) carry;
        }
        return z;
    }
	public int[] multiply(int[] x, int[] y) {
        // 递归
        int xlen = x.length;
        int ylen = y.length;
        if (xlen == 1) {
            return ;
        }
        if (ylen == 1) {
           return ;
        }

        // The number of ints in each half of the number.
        int half = (Math.max(xlen, ylen)+1) / 2;

        // xl and yl are the lower halves of x and y respectively,
        // xh and yh are the upper halves.
        int[] xl = getLower(x, half);
        int[] xh = getUpper(x, half);
        int[] yl = getLower(y, half);
        int[] yh = getUpper(y, half);

        int[] p1 = multiply(xh, yh);  // p1 = xh*yh
        int[] p2 = multiply(xl, yl);  // p2 = xl*yl

        // p3=(xh+xl)*(yh+yl)
        int[] p3 = multiply(add(xh, xl), add(yh, yl));

        // result = p1 * 2^(32*2*half) + (p3 - p1 - p2) * 2^(32*half) + p2
        int[] result = add(add(shiftLeft(p1,32*half), shiftLeft(subtract(subtract(p3, p1), p2), 32*half)), p2);

//        if (x.signum != y.signum) {
//            return result.negate();
//        } else {
            return result;
//        }
	}
    private int[] getLower(int[] mag, int n) {
        int len = mag.length;

        if (len <= n) {
            return abs();
        }

        int lowerInts[] = new int[n];
        System.arraycopy(mag, len - n, lowerInts, 0, n);

        return trustedStripLeadingZeroInts(lowerInts);
    }
    private int[] getUpper(int[] mag, int n) {
        int len = mag.length;

        if (len <= n) {
            return ZERO;
        }

        int upperLen = len - n;
        int upperInts[] = new int[upperLen];
        System.arraycopy(mag, 0, upperInts, 0, upperLen);

        return trustedStripLeadingZeroInts(upperInts);
    }
    private static int[] trustedStripLeadingZeroInts(int val[]) {
        int vlen = val.length;
        int keep;

        // Find first nonzero byte
        for (keep = 0; keep < vlen && val[keep] == 0; keep++)
            ;
        return keep == 0 ? val : java.util.Arrays.copyOfRange(val, keep, vlen);
    }



}
