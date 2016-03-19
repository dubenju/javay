package javay.test.math;

import java.util.Arrays;

import javay.math.BigNum;
import javay.util.UArys;

public class BigNum2 {
	/** 符号:正号:1,负号:-1. */
	public final int signed;
	private int[] datas;
	/** 数据的长度. */
	public final int length;
	private final int scale;

	public BigNum2(BigNum in) {
		int[] ids = in.datas;
		int len = ids.length;
		// int 8 0xFFFFFFFFL
		int leno = len / 8;
		if (len % 8 != 0) {
			leno ++;
		}
		this.datas = new int[leno];
		for (int i = 0; i < ids.length; i ++) {
			UArys.add(datas, ids[i]);
		}
		UArys.printAryH(datas);
		UArys.printAryL(datas);
		UArys.printAry(datas);
		this.signed = in.signed;
		this.scale = in.length - in.scale;
		this.length = in.length;
	}
	  /**
	   * 构造函数.
	   * @param si 符号
	   * @param da 数据
	   * @param len 长度
	   * @param sca 小数点位置
	   */
	  public BigNum2(int si, int[] da, int len, int sca) {
	    this.signed = si;
	    this.datas = da.clone();
	    this.length = len;
	    this.scale = sca;
	    // check is zero.
//	    this.isZero = chkIsZero();
	  }

	  


//	public BigNum2 add(BigNum2 augend) {
////        int rscale = this.scale;
////        long sdiff = (long) rscale - augend.scale;
////        int[] fst;
////        int[] snd;
////        if (sdiff != 0) {
////            if (sdiff < 0) {
//////                int raise = BigDecimal.checkScale(fst, -sdiff);
////                int raise = (int) -sdiff;
////                rscale = augend.scale;
////                fst = bigMultiplyPowerTen(fst, raise);
////            } else {
//////                int raise = BigDecimal.checkScale(snd, sdiff);
////                int raise = sdiff
////                snd = bigMultiplyPowerTen(snd, raise);
////            }
////        }
////        int[] sum = BigInteger.add(fst, snd);
////        return (this.signum == augend.signum) ? new BigDecimal(sum, INFLATED, rscale, 0) : valueOf(sum, rscale, 0);
//		return null;
//	}
	
	  /**
	   * 加法.
	   * @param augend 加数
	   * @return 和
	   */
	  public BigNum2 add(BigNum2 augend) {
//	    if (augend.isZero == 1) {
//	      // a + 0 = a
//	      return this;
//	    }
//	    if (this.isZero == 1) {
//	      // 0 + a = a
//	      return augend;
//	    }

	    if (this.signed == augend.signed) {
	      /*
	       *  aaa.aa  (5,3)2
	       *   bb.bbb (5,2)3
	       *    c.c   (2,1)1
	       * dddd.d   (5,4)1
	       */
	      /* 整数部长度 */
	      int scaleS = (augend.scale > this.scale) ? augend.scale + 1 : this.scale + 1;
	      /* 小数部长度 */
	      int decT = this.length - this.scale;
	      int decA = augend.length - augend.scale;
	      int diff = decT - decA;
	      if ( diff != 0) {
	          if (diff < 0) {
	              // t
	          } else {
	              // a
	          }
	      }
	      /* 长度 */
//	      int[] dataS = new int[scaleS + ( (decA > decT) ? decA : decT )];
	      int[] dataS = UArys.add(this.datas, augend.datas);

//	      int offT = scaleS - this.scale;
//	      int offA = scaleS - augend.scale;
//	      int posT = offT + this.length;
//	      int posA = offA + augend.length;
//
//	      int idx = dataS.length - 1;
//	      if (posA > posT) {
//	        while(idx >= posT) {
//	            dataS[idx] = augend.datas[idx - offA];
//	            idx --;
//	        }
//	      } else {
//	        while(idx >= posA) {
//	            dataS[idx] = this.datas[idx - offT];
//	            idx --;
//	        }
//	      }
//
//	      if (offA > offT) {
//	          while(idx >= offA) {
//	              dataS[idx] = dataS[idx] + this.datas[idx - offT] + augend.datas[idx - offA];
//	              if (dataS[idx] >= 10) {
//	                dataS[idx] -= 10;
//	                dataS[idx - 1] = 1;
//	              }
//	              idx --;
//	          }
//	          while(idx > 0) {
//	              dataS[idx] = dataS[idx] + this.datas[idx - offT];
//	              if (dataS[idx] >= 10) {
//	                dataS[idx] -= 10;
//	                dataS[idx - 1] = 1;
//	              }
//	              idx --;
//	            }
//	        } else {
//	            while(idx >= offT) {
//	                dataS[idx] = dataS[idx] + this.datas[idx - offT] + augend.datas[idx - offA];
//	                if (dataS[idx] >= 10) {
//	                  dataS[idx] -=  10;
//	                  dataS[idx - 1] = 1;
//	                }
//	                idx --;
//	            }
//	          while(idx > 0) {
//	              dataS[idx] = dataS[idx] + augend.datas[idx - offA];
//	              if (dataS[idx] >= 10) {
//	                dataS[idx] -= 10;
//	                dataS[idx - 1] = 1;
//	              }
//	              idx --;
//	            }
//	        }

	      BigNum2 res = new BigNum2(this.signed, dataS, dataS.length, scaleS);
//	      check(this, augend, res, "+", 0, RoundingMode.UNNECESSARY);
	      return res;
	    } else {
//	      if (this.signed < 0) {
//	        return augend.subtract(new BigNum((0x00 - this.signed), this.datas,
//	             this.scale));
//	      } else {
//	        return this.subtract(new BigNum((0x00 - augend.signed),
//	            augend.datas,  augend.scale));
//	      }
	    	return null;
	    }
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
//	private static BigInteger longRadix[] = {
//        null,
//        null,
//        valueOf(0x4000000000000000L),
//        valueOf(0x383d9170b85ff80bL),
//        valueOf(0x4000000000000000L),
//        valueOf(0x6765c793fa10079dL),
//        valueOf(0x41c21cb8e1000000L),
//        valueOf(0x3642798750226111L),
//        valueOf(0x1000000000000000L),
//        valueOf(0x12bf307ae81ffd59L),
//        valueOf( 0xde0b6b3a7640000L), // 10
//        valueOf(0x4d28cb56c33fa539L),
//        valueOf(0x1eca170c00000000L),
//        valueOf(0x780c7372621bd74dL),
//        valueOf(0x1e39a5057d810000L),
//        valueOf(0x5b27ac993df97701L),
//        valueOf(0x1000000000000000L),
//        valueOf(0x27b95e997e21d9f1L),
//        valueOf(0x5da0e1e53c5c8000L),
//        valueOf( 0xb16a458ef403f19L),
//        valueOf(0x16bcc41e90000000L),
//        valueOf(0x2d04b7fdd9c0ef49L),
//        valueOf(0x5658597bcaa24000L),
//        valueOf( 0x6feb266931a75b7L),
//        valueOf( 0xc29e98000000000L),
//        valueOf(0x14adf4b7320334b9L),
//        valueOf(0x226ed36478bfa000L),
//        valueOf(0x383d9170b85ff80bL),
//        valueOf(0x5a3c23e39c000000L),
//        valueOf( 0x4e900abb53e6b71L),
//        valueOf( 0x7600ec618141000L),
//        valueOf( 0xaee5720ee830681L),
//        valueOf(0x1000000000000000L),
//        valueOf(0x172588ad4f5f0981L),
//        valueOf(0x211e44f7d02c1000L),
//        valueOf(0x2ee56725f06e5c71L),
//        valueOf(0x41c21cb8e1000000L)
//    };
//private static int digitsPerLong[] = {
//         0, // 0
//         0, // 1
//        62, // 2 01
//        39, // 3 012
//        31, // 4 0123
//        27, // 5 01234
//        24, // 6 012345
//        22, // 7 0123456
//        20, // 8 01234567
//        19, // 9 012345678
//        18, //10 0123456789
//        18, //11 0123456789A
//        17, //12 0123456789AB
//        17, //13 0123456789ABC
//        16, //14 0123456789ABCD
//        16, //15 0123456789ABCDE
//        15, //16 0123456789ABCDEF
//        15, //17 0123456789ABCDEFG
//        15, //18 0123456789ABCDEFGH
//        14, //19 0123456789ABCDEFGHI
//        14, //20 0123456789ABCDEFGHIJ
//        14, //21 0123456789ABCDEFGHIJK
//        14, //22 0123456789ABCDEFGHIJKL
//        13, //23 0123456789ABCDEFGHIJKLM
//        13, //24 0123456789ABCDEFGHIJKLMN
//        13, //25 0123456789ABCDEFGHIJKLMNO
//        13, //26 0123456789ABCDEFGHIJKLMNOP
//        13, //27 0123456789ABCDEFGHIJKLMNOPQ
//        13, //28 0123456789ABCDEFGHIJKLMNOPQR
//        12, //29 0123456789ABCDEFGHIJKLMNOPQRS
//        12, //30 0123456789ABCDEFGHIJKLMNOPQRST
//        12, //31 0123456789ABCDEFGHIJKLMNOPQRSTU
//        12, //32 0123456789ABCDEFGHIJKLMNOPQRSTUV
//        12, //33 0123456789ABCDEFGHIJKLMNOPQRSTUVW
//        12, //34 0123456789ABCDEFGHIJKLMNOPQRSTUVWX
//        12, //35 0123456789ABCDEFGHIJKLMNOPQRSTUVWXY
//        12  //36 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ
//    };
//    /* zero[i] is a string of i consecutive zeros. */
//    private static String zeros[] = new String[64];
//    static {
//        zeros[63] =
//            "000000000000000000000000000000000000000000000000000000000000000";
//        for (int i=0; i < 63; i++) {
//            zeros[i] = zeros[63].substring(0, i);
//        }
//    }
//
//    private String smallToString(int radix) {
//        if (signum == 0) {
//            return "0";
//        }
//
//        // Compute upper bound on number of digit groups and allocate space
//        int maxNumDigitGroups = (4 * this.dats.length + 6) / 7;
//        String digitGroup[] = new String[maxNumDigitGroups];
//
//        // Translate number to string, a digit group at a time
//        int[] tmp = this.dats;
//        int numGroups = 0;
//        while (tmp.signum != 0) {
//            BigInteger d = longRadix[radix];
//
//            MutableBigInteger q = new MutableBigInteger(),
//                              a = new MutableBigInteger(tmp.mag),
//                              b = new MutableBigInteger(d.mag);
//            MutableBigInteger r = a.divide(b, q);
//            BigInteger q2 = q.toBigInteger(tmp.signum * d.signum);
//            BigInteger r2 = r.toBigInteger(tmp.signum * d.signum);
//
//            digitGroup[numGroups++] = Long.toString(r2.longValue(), radix);
//            tmp = q2;
//        }
//
//        // Put sign (if any) and first digit group into result buffer
//        StringBuilder buf = new StringBuilder(numGroups*digitsPerLong[radix]+1);
//        if (signum < 0) {
//            buf.append('-');
//        }
//        buf.append(digitGroup[numGroups-1]);
//
//        // Append remaining digit groups padded with leading zeros
//        for (int i=numGroups-2; i >= 0; i--) {
//            // Prepend (any) leading zeros for this digit group
//            int numLeadingZeros = digitsPerLong[radix]-digitGroup[i].length();
//            if (numLeadingZeros != 0) {
//                buf.append(zeros[numLeadingZeros]);
//            }
//            buf.append(digitGroup[i]);
//        }
//        return buf.toString();
//    }
}
