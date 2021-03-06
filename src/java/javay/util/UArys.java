package javay.util;

import java.util.ArrayList;
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
    public static int[] subtract(int[] big, int[] little) {
        int bigIndex = big.length;
        int result[] = new int[bigIndex];
        int littleIndex = little.length;
        long difference = 0;
//printAry(big);
//printAry(little);
        // Subtract common parts of both numbers
        while (littleIndex > 0) {
            difference = (big[--bigIndex] & 0xFFFFFFFFL) - (little[--littleIndex] & 0xFFFFFFFFL) + (difference >> 32);
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
//            System.out.println(product + "," + carry);
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
    public static int[] multiply(int[] x, int[] y) {
        int[] z = new int[x.length + y.length];
        for (int i = x.length - 1; i >= 0; i --) {
            long carry = 0;
            for (int j = y.length - 1, k= y.length + i; j >= 0; j --, k --) {
                long product = (y[j] & 0xFFFFFFFFL) * (x[i] & 0xFFFFFFFFL) + (z[k] & 0xFFFFFFFFL) + carry;
                z[k] = (int) product;
                carry = product >>> 32;
            }
            z[i] = (int) carry;
        }
        return z;
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
        while (-- xlen > 0) {
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
//            if (D4 < 0) {
//                // (6)往回加
//                q[j] = q[j] - 1;
//                ud = add(ud , vd);
//            } else {
//                // (7)对j进行循环
//            }
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
    private static char[] TBL_CH= {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
        };
    public static String toStringx(int[] in, int radix) {
        StringBuilder buf = new StringBuilder();
        for (int i =  in.length - 1; i >= 0; i --) {
            long m = in[i];
            long a = 0;
            int b = 0;
            do {
                a = m / radix;
                b = (int) (m - a * radix);
                buf.insert(0, TBL_CH[b]);
                m = a;
            } while(a > 0);
        }
        buf.append(",len=" + buf.length() + "," + radix + "进制数");
        return buf.toString();
    }
    public static boolean isZero(int[] in) {
        boolean bRes = true;
        for (int i : in) {
            if(i != 0) {
                bRes = false;
                break;
            }
        }
        return bRes;
    }
    public static String toString(int[] in, int radix) {
        StringBuilder buf = new StringBuilder();
        int[] tmp = in;
        if (isZero(tmp)) {
            buf.append("00");
        }
        while (isZero(tmp) == false) {
            int[] q = divide(tmp, radix);
            int[] u = multiply(q, radix);
            int[] c = subtract(tmp, u);

            System.out.print("-----------tmp=");
            printAry(tmp);
            System.out.print("q=");
            printAry(q);
            System.out.print("u=");
            printAry(u);
            System.out.print("c=");
            printAry(c);

            buf.insert(0, c[c.length - 1]);
            tmp = q;
        }
//        buf.append(",len=" + buf.length() + "," + radix + "进制数");
        return buf.toString();
    }

    public static byte[][] split(byte[] in, byte[] keys) {
        if ( in == null ) return null;
        if ( keys == null || keys.length <= 0) return new byte[][] {in};
        if (in.length < keys.length) return new byte[][] {in};
        ArrayList<SplitedInfo> splited = new ArrayList<SplitedInfo>();
        int pre_pos = 0;
        int pos = 0;
        int length = 0;
        int len_in = in.length;
        int len_keys = keys.length;
        int max = (len_in - len_keys) + 1;

        while(pos < max) {
            // System.out.println("pos=" + pos + ",max=" + max);
            if (compare(in, pos, keys) == true) {
                // pos

                // length
                length = pos - pre_pos;
                // System.out.println("★pos:" + pre_pos + ",length=" + length);
                splited.add(new SplitedInfo(pre_pos, length));
                pos += len_keys - 1;
                pre_pos = pos + 1;
            }
            pos ++;
        }
        length = len_in - pre_pos;
        if (length > 0) {
            splited.add(new SplitedInfo(pre_pos, length));
        }
        // System.out.println("★pos:" + pre_pos + ",length=" + length);
        byte[][] result = new byte[splited.size()][];
        int i = 0;
        for (SplitedInfo info : splited) {
            if (info.getLength() == 0) {
                result[i] = null;
            } else {
                result[i] = new byte[info.getLength()];
                System.arraycopy(in, info.getPos(), result[i], 0, info.getLength());
            }
            i ++;
        }
        return result;
    }
    private static boolean compare(byte[] in, int pos, byte[] keys) {
        int i = 0;
        while(i < keys.length) {
            if (keys[i] != in[pos + i]) {
                return false;
            }
            i ++;
        }
        return true;
    }
}

class SplitedInfo {
    private int pos;
    private int length;
    public SplitedInfo() {
        this.pos = -1;
        this.length = -1;
    }
    public SplitedInfo(int pos, int length) {
        this.pos = pos;
        this.length = length;
    }
    /**
     * @return pos
     */
    public int getPos() {
        return pos;
    }
    /**
     * @param pos  pos
     */
    public void setPos(int pos) {
        this.pos = pos;
    }
    /**
     * @return length
     */
    public int getLength() {
        return length;
    }
    /**
     * @param length  length
     */
    public void setLength(int length) {
        this.length = length;
    }
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("pos=");
        buf.append(this.pos);
        buf.append(",length=");
        buf.append(this.length);
        return buf.toString();
    }
}
