package javay.test.math;

import java.util.Arrays;

import javay.math.BigNum;
import javay.util.UArys;

public class BigNum2 {
	private final int signum;
	private int[] dats;
	private final int scale;

	public BigNum2(BigNum in) {
		int[] ids = in.datas;
		int len = ids.length;
		int leno = len / 8;
		if (len % 8 != 0) {
			leno ++;
		}
		this.dats = new int[leno];
		for (int i = 0; i < ids.length; i ++) {
			add1(dats, ids[i]);
		}
		UArys.printAry(dats);
		UArys.printAry2(dats);
		UArys.printAry3(dats);
		this.signum = in.signed;
		this.scale = in.length - in.scale;
	}
	public int[] add1(int[] in, int n) {
//		System.out.print("n=" + n + ",");
//		printAry(in);
		long p = 0;
		long c = n & 0XFFFFFFFFL;

		for (int i = in.length - 1;i >= 0; i --) {
			p = ( (in[i] & 0xFFFFFFFFL) * 10) + c;
			in[i] = (int) p;
			c = p >>> 32;
		}

		return in;
	}

	public BigNum2 add(BigNum2 augend) {
//        int rscale = this.scale;
//        long sdiff = (long) rscale - augend.scale;
//        int[] fst;
//        int[] snd;
//        if (sdiff != 0) {
//            if (sdiff < 0) {
////                int raise = BigDecimal.checkScale(fst, -sdiff);
//                int raise = (int) -sdiff;
//                rscale = augend.scale;
//                fst = bigMultiplyPowerTen(fst, raise);
//            } else {
////                int raise = BigDecimal.checkScale(snd, sdiff);
//                int raise = sdiff
//                snd = bigMultiplyPowerTen(snd, raise);
//            }
//        }
//        int[] sum = BigInteger.add(fst, snd);
//        return (this.signum == augend.signum) ? new BigDecimal(sum, INFLATED, rscale, 0) : valueOf(sum, rscale, 0);
		return null;
	}
	private static final long[] LONG_TEN_POWERS_TABLE = {
	        1,                     // 0 / 10^0
	        10,                    // 1 / 10^1
	        100,                   // 2 / 10^2
	        1000,                  // 3 / 10^3
	        10000,                 // 4 / 10^4
	        100000,                // 5 / 10^5
	        1000000,               // 6 / 10^6
	        10000000,              // 7 / 10^7
	        100000000,             // 8 / 10^8
	        1000000000,            // 9 / 10^9
	        10000000000L,          // 10 / 10^10
	        100000000000L,         // 11 / 10^11
	        1000000000000L,        // 12 / 10^12
	        10000000000000L,       // 13 / 10^13
	        100000000000000L,      // 14 / 10^14
	        1000000000000000L,     // 15 / 10^15
	        10000000000000000L,    // 16 / 10^16
	        100000000000000000L,   // 17 / 10^17
	        1000000000000000000L   // 18 / 10^18
	    };
    private static volatile BigInteger BIG_TEN_POWERS_TABLE[] = {
            BigInteger.ONE,
            BigInteger.valueOf(10),
            BigInteger.valueOf(100),
            BigInteger.valueOf(1000),
            BigInteger.valueOf(10000),
            BigInteger.valueOf(100000),
            BigInteger.valueOf(1000000),
            BigInteger.valueOf(10000000),
            BigInteger.valueOf(100000000),
            BigInteger.valueOf(1000000000),
            BigInteger.valueOf(10000000000L),
            BigInteger.valueOf(100000000000L),
            BigInteger.valueOf(1000000000000L),
            BigInteger.valueOf(10000000000000L),
            BigInteger.valueOf(100000000000000L),
            BigInteger.valueOf(1000000000000000L),
            BigInteger.valueOf(10000000000000000L),
            BigInteger.valueOf(100000000000000000L),
            BigInteger.valueOf(1000000000000000000L)
        };

        private static final int BIG_TEN_POWERS_TABLE_INITLEN =
            BIG_TEN_POWERS_TABLE.length;
        private static final int BIG_TEN_POWERS_TABLE_MAX =
            16 * BIG_TEN_POWERS_TABLE_INITLEN;
    private static BigInteger bigMultiplyPowerTen(BigInteger value, int n) {
        if (n <= 0)
            return value;
        if(n<LONG_TEN_POWERS_TABLE.length) {
                return value.multiply(LONG_TEN_POWERS_TABLE[n]);
        }
        return value.multiply(bigTenToThe(n));
    }
    private static BigInteger bigTenToThe(int n) {
        if (n < 0)
            return BigInteger.ZERO;

        if (n < BIG_TEN_POWERS_TABLE_MAX) {
            BigInteger[] pows = BIG_TEN_POWERS_TABLE;
            if (n < pows.length)
                return pows[n];
            else
                return expandBigIntegerTenPowers(n);
        }

        return BigInteger.TEN.pow(n);
    }
    private static BigInteger expandBigIntegerTenPowers(int n) {
        synchronized(BigDecimal.class) {
            BigInteger[] pows = BIG_TEN_POWERS_TABLE;
            int curLen = pows.length;
            // The following comparison and the above synchronized statement is
            // to prevent multiple threads from expanding the same array.
            if (curLen <= n) {
                int newLen = curLen << 1;
                while (newLen <= n)
                    newLen <<= 1;
                pows = Arrays.copyOf(pows, newLen);
                for (int i = curLen; i < newLen; i++)
                    pows[i] = pows[i - 1].multiply(BigInteger.TEN);
                // Based on the following facts:
                // 1. pows is a private local varible;
                // 2. the following store is a volatile store.
                // the newly created array elements can be safely published.
                BIG_TEN_POWERS_TABLE = pows;
            }
            return pows[n];
        }
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

	public String toString() {
		return "";
	}
    private String smallToString(int radix) {
        if (signum == 0) {
            return "0";
        }

        // Compute upper bound on number of digit groups and allocate space
        int maxNumDigitGroups = (4*mag.length + 6)/7;
        String digitGroup[] = new String[maxNumDigitGroups];

        // Translate number to string, a digit group at a time
        BigInteger tmp = this.abs();
        int numGroups = 0;
        while (tmp.signum != 0) {
            BigInteger d = longRadix[radix];

            MutableBigInteger q = new MutableBigInteger(),
                              a = new MutableBigInteger(tmp.mag),
                              b = new MutableBigInteger(d.mag);
            MutableBigInteger r = a.divide(b, q);
            BigInteger q2 = q.toBigInteger(tmp.signum * d.signum);
            BigInteger r2 = r.toBigInteger(tmp.signum * d.signum);

            digitGroup[numGroups++] = Long.toString(r2.longValue(), radix);
            tmp = q2;
        }

        // Put sign (if any) and first digit group into result buffer
        StringBuilder buf = new StringBuilder(numGroups*digitsPerLong[radix]+1);
        if (signum < 0) {
            buf.append('-');
        }
        buf.append(digitGroup[numGroups-1]);

        // Append remaining digit groups padded with leading zeros
        for (int i=numGroups-2; i >= 0; i--) {
            // Prepend (any) leading zeros for this digit group
            int numLeadingZeros = digitsPerLong[radix]-digitGroup[i].length();
            if (numLeadingZeros != 0) {
                buf.append(zeros[numLeadingZeros]);
            }
            buf.append(digitGroup[i]);
        }
        return buf.toString();
    }
}
