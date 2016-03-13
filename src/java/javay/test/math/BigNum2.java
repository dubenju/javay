package javay.test.math;

import java.util.Arrays;

import javay.math.BigNum;

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
		printAry(dats);
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
	public static void printAry(int[] in) {
		System.out.print(" ");
		for (int i = 0; i< in.length; i++) {
			System.out.print(Integer.toHexString(in[i]).toUpperCase());
		}
		System.out.println(",len=" + in.length);
	}
	public static void printAry2(int[] in) {
		System.out.print(" ");
		for (int i = 0; i< in.length; i++) {
			System.out.print(in[i]);
		}
		System.out.println(",len=" + in.length);
	}
	public BigNum2 add(BigNum2 augend) {
        int rscale = this.scale;
        long sdiff = (long) rscale - augend.scale;
        int[] fst;
        int[] snd;
        if (sdiff != 0) {
            if (sdiff < 0) {
//                int raise = BigDecimal.checkScale(fst, -sdiff);
                int raise = (int) -sdiff;
                rscale = augend.scale;
                fst = bigMultiplyPowerTen(fst, raise);
            } else {
//                int raise = BigDecimal.checkScale(snd, sdiff);
                int raise = sdiff
                snd = bigMultiplyPowerTen(snd, raise);
            }
        }
        int[] sum = BigInteger.add(fst, snd);
        return (this.signum == augend.signum) ? new BigDecimal(sum, INFLATED, rscale, 0) : valueOf(sum, rscale, 0);
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
	public String toString() {
		return "";
	}
}
