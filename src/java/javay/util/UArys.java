package javay.util;

import java.util.Arrays;

public class UArys {
	public static void printAryH(int[] in) {
		System.out.print(" ");
		for (int i = 0; i< in.length; i++) {
			System.out.print(Integer.toHexString(in[i]).toUpperCase());
		}
		System.out.println(",len=" + in.length);
	}
	public static void printAryL(int[] in) {
		System.out.print(" ");
		for (int i = 0; i< in.length; i++) {
			System.out.print((in[i]&0xFFFFFFFFL) + " ");
		}
		System.out.println(",len=" + in.length);
	}
	public static void printAry(int[] in) {
		System.out.print(" ");
		for (int i = 0; i< in.length; i++) {
			System.out.print((in[i]) + " ");
		}
		System.out.println(",len=" + in.length);
	}
	public static int[] add(int[] in, int n) {
		long p = 0;
		long c = n & 0XFFFFFFFFL;
		for (int i = in.length - 1;i >= 0; i --) {
			p = ( (in[i] & 0xFFFFFFFFL) * 10) + c;
			in[i] = (int) p;
			c = p >>> 32;
		}
		return in;
	}
	public static int[] add11(int[] in, int n) {
		long c = n & 0XFFFFFFFFL;
		for (int i = in.length - 1;i >= 0; i --) {
			long p = ( (in[i] & 0xFFFFFFFFL) * 10) + c;
			in[i] = (int) p;
			c = p >>> 32;
		}
		return in;
	}
    public static int[] add(int[] x, int[] y) {
        if (x.length < y.length) {
            // 如果x比较短的话,交换xy. a+b=b+a.
            int[] tmp = x;
            x = y;
            y = tmp;
        }

        int xIndex = x.length;
        int yIndex = y.length;
        int result[] = new int[xIndex];
        long sum = 0;
        if (yIndex == 1) {
            sum = (x[-- xIndex] & 0xFFFFFFFFL) + (y[0] & 0xFFFFFFFFL) ;
            result[xIndex] = (int) sum;
        } else {
            // Add common parts of both numbers
            while (yIndex > 0) {
                sum = (x[--xIndex] & 0xFFFFFFFFL) + (y[-- yIndex] & 0xFFFFFFFFL) + (sum >>> 32);
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
            result[-- xIndex] = x[xIndex];
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

    public static int[] multiply(int[] x, int y) {
//        if (Integer.bitCount(y) == 1) {
//            return shiftLeft(x, Integer.numberOfTrailingZeros(y));
//        }
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
    public static int[] multiply(int[] x, long y) {
//      if (Integer.bitCount(y) == 1) {
//          return shiftLeft(x, Integer.numberOfTrailingZeros(y));
//      }
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
    public static int[] dividea(int[] x, int y) {
        int n = x.length;
        long b = 0xFFFFFFFFL + 1;
        int[] q = new int[x.length];
        int r = 0;
        for (int j = 1; j <= n; j ++) {
            q[j] = (int) (b * r + x[j]) / y;
            r = (int) (b * r + x[j]) % y; 
        }
        return q;
    }
    public static int[] divide(int[] x, int y) {
        int[] quotient = new int[x.length];
        int rem = x[0];
        long remLong = rem & 0xFFFFFFFFL;
        long divisorLong = y & 0xFFFFFFFFL;
        if (remLong < divisorLong) {
            quotient[0] = 0;
        } else {
            quotient[0] = (int) (remLong / divisorLong);
            rem = (int) (remLong - (quotient[0] * divisorLong));
            remLong = rem & 0xFFFFFFFFL;
        }
        int xlen = x.length;
        while (--xlen > 0) {
            long dividendEstimate = (remLong << 32) | (x[0 + x.length - xlen] & 0xFFFFFFFFL);
            int q;
            if (dividendEstimate >= 0) {
                q = (int) (dividendEstimate / divisorLong);
                rem = (int) (dividendEstimate - q * divisorLong);
            } else {
                long tmp = divide(dividendEstimate, y);
                q = (int) (tmp & 0xFFFFFFFFL);
                rem = (int) (tmp >>> 32);
            }
            quotient[x.length - xlen] = q;
            remLong = rem & 0xFFFFFFFFL;
        }
        return quotient;
    }
    static long divide(long n, int d) {
        long dLong = d & 0xFFFFFFFFL;
        long r;
        long q;
        if (dLong == 1) {
            q = (int)n;
            r = 0;
            return (r << 32) | (q & 0xFFFFFFFFL);
        }

        // Approximate the quotient and remainder
        q = (n >>> 1) / (dLong >>> 1);
        r = n - q*dLong;

        // Correct the approximation
        while (r < 0) {
            r += dLong;
            q--;
        }
        while (r >= dLong) {
            r -= dLong;
            q++;
        }
        // n - q*dlong == r && 0 <= r <dLong, hence we're done.
        return (r << 32) | (q & 0xFFFFFFFFL);
    }
    /**
     * 高德纳除算法实现
     * @param u
     * @param v
     * @return q
     */
    public static int[] divide(int[]u, int[]v) {
        int vlen = v.length;
        if (v[vlen - 1] == 0 || vlen <= 1) {
            // Error.
        }
        int ulen = u.length;
        int m = ulen - vlen;
        int[] q = new int[m + 1];

        // (1)规格化
        long base = 4294967296L;
        long d = base / (v[0] + 1);
        int[] ud = multiply(u, d);
        int[] vd = multiply(v, d);

        // (2)初始化-(7)
        for (int j = 0; j <= m; j ++) {
            // (3)计算假商
            int qq = 0;
            if (u[j] == v[0]) {
                qq = (int) base - 1;
            } else {
                qq = (int) (ud[j] * base + ud[j + 1]) / vd[0]; 
            }
            int rr = (int) (ud[j] * base + ud[j + 1]) % vd[0];
            while(vd[1] * qq > (base * ud[j] + ud[j + 1] - qq * vd[0] * base + ud[j + 2])) {
                qq = qq - 1;
                rr += vd[0];
                if (base <= rr) {
                    break;
                }
            }
            // (4)乘和减
            ud = subtract(ud, multiply(vd , qq));
            // (5)测试余数
            q[j] = qq;
            if (D4 < 0) {
                // (6)往回加
                q[j] = q[j] - 1;
                ud = add(ud , vd);
            } else {
                // (7)对j进行循环
            }
        }
        // (8)不规格化
        return q;
    }

    /**
     * 
for (j = 0 to m) {
  if(u(j) == v(1)) {
    qq = base - 1;
  } else { 
    qq={ (u(j)*base+u(j+1)) / v(1); 
  }
  while ( qq * v(2) > (u(j) * base + u(j+1) - qq * v(1)) * base + u(j+2) ) {
    qq = qq - 1;
  }
  u= u - qq * v;
  q(j) = qq;
  if ( u < 0) {
    q(j) = q(j) - 1;
    u = u + v; }
  }
}
     */
}
