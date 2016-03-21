package javay.test.math;

import java.math.RoundingMode;
import java.util.Arrays;

import javay.math.BigNum;
import javay.math.BigNumRound;
import javay.util.UArys;

public class BigNum2 {
    /** 符号:正号:1,负号:-1. */
    private final int signed;
    private int[] datas;
    /** 数据的长度. */
    private final int length;
    private final int scale;

    /** zero */
    private transient int isZero = 1;
    private transient String cache;

    public static final BigNum2 ZERO = new BigNum2("0");
    public static final BigNum2 ONE  = new BigNum2("1");
    /**
     * 构造函数.
     * @param in 输入数据 字符数组
     * @param offset 起始位置
     * @param len 长度
     * @param numberSystem 进制系统
     */
    private BigNum2(char[] in, int offset, int len, int numberSystem) {
      /* 初始化 */
      int idx = offset;
      /* 符号判断 */
      if (in[idx] == '-') {
        this.signed = -1;
        idx ++;
      } else if (in[idx] == '+') {
        this.signed = 1;
        idx ++;
      } else {
        // default is plus.
        this.signed = 1;
      }

      /* 数值变换  */
      int dotpos = -1;
      this.isZero = 1;
      int[] dats = new int[len + 2]; // default:0.0
      int idy = 0;
      int start = -1, end = -1, control = 1;
      while (idx < len) {
        if (in[idx] == '.') {
        if (dotpos != -1) {
          // when .. or .0.
          throw new NumberFormatException();
        }
        if (start == -1) {
          idy ++;
        }
        // start < 0 . or .0 => 0.
        // start < 0 0. or 00. or 0.0 or 00.0 => 0.
        // start = 0 1. or 01. or 1.0 or 01.0 => 1.
        dotpos = idy;
        if (control == 1) {
          if (this.isZero == 0) {
            dotpos ++;
          } else {
            start = idy - 1;
            dotpos = 1;
          }
          if (start < 0) {
            start = 0;
          }
          control = 2;
        }
        idx ++;
        continue;
        }

        if (in[idx] == '0') {
        dats[idy] = (in[idx] - '0');
        if (control == 1) {
          start = 0;
        } else {
          end = idy;
        }
        } else if ('0' < in[idx] && in[idx] <= '9') {
        dats[idy] = (in[idx] - '0');
        this.isZero = 0;
        if (control == 2) {
          end = idy;
        }
        if (control == 1) {
          start = idy;
          control = 2;
        }
        } else if ('A' <= in[idx] && in[idx] <= 'F') {
        dats[idy] =  (in[idx] - 'A' + 10);
        this.isZero = 0;
        if (control == 2) {
          end = idy;
        }
        if (control == 1) {
          start = idy;
          control = 2;
        }
        } else if ('a' <= in[idx] && in[idx] <= 'f') {
          dats[idy] =  (in[idx] - 'a' + 10);
          this.isZero = 0;
          if (control == 2) {
            end = idy;
          }
          if (control == 1) {
            start = idy;
            control = 2;
          }
        } else {
        throw new NumberFormatException();
        }

        idy ++;
        idx ++;
      } // while

      if(start < 0) {
        start = 0;
      }
      if (end < 1) {
        end = 1;
        if (dotpos < 0) {
          dotpos = end;
        }
      } else {
        if (dotpos < 0) {
        end ++;
        dotpos = end;
        }
      }
      this.length = end - start + 1;
      int leno = this.length / 8;
      if (this.length % 8 != 0) {
        leno ++;
      }
      UArys.printAry(dats);
      this.datas = new int[leno];
      for (int i = 0; i < dats.length; i ++) {
        UArys.add(this.datas, dats[i]);
      }
//      this.datas = new int[this.length];
//      for (int posi = start, poso = 0; posi <= end; posi ++, poso ++) {
//        this.datas[poso] = dats[posi];
//      }
      this.scale = dotpos;

//      if (numberSystem != 10) {
//        BigNum res = this.createNum(0);
//        BigNum ns   = this.createNum(numberSystem);
//        int indx = 0;
//        for (int i = this.scale - 1; i >= 0; i --, indx ++) {
//        res = res.add(ns.pow(i).multiply(this.createNum(this.datas[indx])));
//        }
//        for (int i = 1; i <= (this.length - this.scale); i ++, indx ++) {
//        res = res.add(
//            this.createNum(this.datas[indx]).divide(ns.pow(i), 40, BigNumRound.HALF_EVENT));
//        }
//        this.datas = res.datas;
//        this.length = res.length;
//        this.scale = res.scale;
//      }
    }

    private BigNum2 createNum(int in) {
      int signed = 0x01;
      int an = in;
      if (in < 0) {
        signed = -0x01;
        an = -in;
      }
//      int lengt = 1;
      int scal = 1;
      int[] datas = null;
      if (an < 10) {
        datas = new int[1];
        datas[0] =  in;
      } else if (an < 100) {
//        lengt = 2;
        scal = 2;
        datas = new int[2];
        if (an < 20) {
        datas[0] = 1;
        datas[1] = (an - 10);
        }
      }
      return new BigNum2(signed, datas, scal);
    }
    // TODO:0.0,01.10
    private BigNum2(int si, int[] in, int dotpos) {
      this.signed = si;

      int start = 0;
      while (start < dotpos && in[start] == 0) {
        start ++;
      }
      this.scale = dotpos - start;

      int end = in.length - 1;
      while (end > dotpos && in[end] == 0) {
         end --;
      }

      this.isZero = (in[start] == 0 && in[end] == 0 ) ? 1 : 0;
      this.length = end - start + 1;
      this.datas = new int[this.length];
      for (int idxi = start, idxr = 0; idxr < this.length; idxi ++, idxr ++) {
        this.datas[idxr] = in[idxi];
      }
    }

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
//      this.isZero = chkIsZero();
    }

    /**
     * 构造函数.
     * @param str 字符串
     */
    public BigNum2(String str) {
      this(str.toCharArray(), 0 , str.toCharArray().length, 10);
//      System.out.println("字符串:" + str);
    }

    public BigNum2(String str, int numberSystem) {
      this(str.toCharArray(), 0 , str.toCharArray().length, numberSystem);
    }

    /**
     * 加法.
     * @param augend 加数
     * @return 和
     */
    public BigNum2 add(BigNum2 augend) {
//      if (augend.isZero == 1) {
//        // a + 0 = a
//        return this;
//      }
//      if (this.isZero == 1) {
//        // 0 + a = a
//        return augend;
//      }

      if (this.signed == augend.signed) {
        /*
         *  aaa.aa  (5,3)2
         *   bb.bbb (5,2)3
         *    c.c   (2,1)1
         * dddd.d   (5,4)1
         */
        /* 整数部长度 */
        // 小数点的移动
        // int scaleS = (augend.scale > this.scale) ? augend.scale + 1 : this.scale + 1;
        int scaleS = (augend.scale > this.scale) ? augend.scale : this.scale;
        
        /* 小数部长度 */
        int decT = this.length - this.scale;
        int decA = augend.length - augend.scale;
        int dec = (decA > decT) ? decA : decT;
        int len = scaleS + dec;
        int diff = decT - decA;
        if ( diff != 0) {
          if (diff < 0) {
            // t * 10^diff
          } else {
            // a * 10 ^ diff
          }
        }
        /* 长度 */
//        int[] dataS = new int[scaleS + ( (decA > decT) ? decA : decT )];
        int[] dataS = UArys.add(this.datas, augend.datas);
//        System.out.println("(" + len +"," + scaleS + ")");
        BigNum2 res = new BigNum2(this.signed, dataS, len, len - dec);
        System.out.println(this + "(" + this.length +"," + this.scale + ") + " + augend + "(" + augend.length + "," +augend.scale + ") = " + res + "(" + len + "," + scaleS + ")");
//        check(this, augend, res, "+", 0, RoundingMode.UNNECESSARY);
        return res;
      } else {
        if (this.signed < 0) {
        return augend.subtract(new BigNum2((0x00 - this.signed), this.datas,
             this.scale));
        } else {
        return this.subtract(new BigNum2((0x00 - augend.signed),
            augend.datas,  augend.scale));
        }
      }
    }

    /**
     * 减法.
     * @param subtrahendi 减数
     * @return 差.
     */
    public BigNum2 subtract(BigNum2 subtrahendi) {
//        System.out.println("this=" + this);
//        System.out.println("subtrahendi=" + subtrahendi);
      if (subtrahendi.isZero == 1) {
        // a - 0 = a
        return this;
      }
      if (this.isZero == 1) {
        // 0 - a = -a
        return new BigNum2((0x00 - subtrahendi.signed), subtrahendi.datas,
           subtrahendi.scale);
      }

      // TODO:2.0-1.6
      if (this.signed == subtrahendi.signed) {
        int signeds = 1;
        // 大小调整
        BigNum2 minuend = null;
        BigNum2 subtrahend = null;
        int icmp = this.abs().compareTo(subtrahendi.abs());
        if (icmp == 0) {
          // a - a = 0
          return ZERO;
        } else if (icmp > 0) {
          minuend = this;
          subtrahend = subtrahendi;
          // subtrahend = subtrahendi;
        } else if (icmp < 0) {
          minuend = subtrahendi;
          subtrahend = this;
          signeds = -1;
        }

        /* 整数部长度 */
        // int scaleS = (subtrahend.scale > minuend.scale) ? subtrahend.scale + 1 : minuend.scale + 1;
        int scaleS = (subtrahend.scale > minuend.scale) ? subtrahend.scale : minuend.scale;
        /* 小数部长度 */
        int decM = minuend.length - minuend.scale;
        int decS = subtrahend.length - subtrahend.scale;
        /* 长度 */
//        int[] dataS = new int[scaleS + ( (decS > decM) ? decS : decM)];
        int[] dataS = UArys.subtract(minuend.datas, subtrahend.datas);

//        int offM = scaleS - minuend.scale;
//        int offA = scaleS - subtrahend.scale;
//        int posM = offM + minuend.length;
//        int posA = offA + subtrahend.length;

//        int idx = dataS.length - 1;
//        if (posA > posM) {
//        while(idx >= posM) {
//            dataS[idx] = dataS[idx] - subtrahend.datas[idx - offA];
//            if (dataS[idx] < 0) {
//            dataS[idx] += 10;
//            dataS[idx - 1] = dataS[idx - 1] - 1;
//            }
//            idx --;
//          }
//        } else {
//        // run ?
//        while(idx >= posA) {
//            dataS[idx] = minuend.datas[idx - offM];
//            idx --;
//          }
//        }
//
//        if (offA > offM) {
//          while(idx >= offA) {
//            dataS[idx] = dataS[idx] + minuend.datas[idx - offM] - subtrahend.datas[idx - offA];
//            if (dataS[idx] < 0) {
//              dataS[idx] +=  10;
//              dataS[idx - 1] = dataS[idx - 1] - 1;
//            }
//            idx --;
//            }
//
//        while(idx > 0) {
//            dataS[idx] = dataS[idx]  + minuend.datas[idx - offM];
//            if (dataS[idx] < 0) {
//            dataS[idx] +=  10;
//            dataS[idx - 1] = dataS[idx - 1] - 1;
//            }
//            idx --;
//          }
//        } else {
//          // run ?
//          while(idx >= offM) {
//            dataS[idx] = dataS[idx] + minuend.datas[idx - offM] - subtrahend.datas[idx - offA];
//            if (dataS[idx] < 0) {
//              dataS[idx] +=  10;
//              dataS[idx - 1] = dataS[idx - 1] - 1;
//            }
//            idx --;
//            }
//
//          while(idx > 0) {
//            dataS[idx] =  dataS[idx] - subtrahend.datas[idx - offA];
//            if (dataS[idx] < 0) {
//              dataS[idx] +=  10;
//              dataS[idx - 1] = dataS[idx - 1] - 1;
//            }
//            idx --;
//            }
//        }

        BigNum2 res = new BigNum2(signeds, dataS,  scaleS);
//        check(this, subtrahendi, res, "-", 0, RoundingMode.UNNECESSARY);
        return res;
      } else {
        return this.add(new BigNum2((0x00 - subtrahendi.signed),
          subtrahendi.datas,  subtrahendi.scale));
      }
    }

    /**
     * 乘法.
     * @param multiplicand 乘数
     * @return 积
     */
    public BigNum2 multiply(BigNum2 multiplicand) {
      if (multiplicand.isZero == 1) {
        // a * 0 = 0
        return multiplicand;
      }
      if (multiplicand.equals(BigNum.ONE)) {
        // a * 1 = a
        return this;
      }
      if (this.isZero == 1) {
        // 0 * a = 0
        return this;
      }
      if (this.equals(BigNum.ONE)) {
        // 1 * a = a
        return multiplicand;
      }
      // TODO: after
      // a * (-1) = -a
      // (-1) * a = -a

      /* 符号 */
      int signed1 = this.signed;
      int signed2 = multiplicand.signed;
      final int signed = (signed1 * signed2);

      /* 长度 */
      int len = this.length + multiplicand.length;

      /* 小数点位置 */
      // 小数部长度
      final int decimalLen = len - ((this.length - this.scale) + (multiplicand.length - multiplicand.scale));

      /* 数据 */
      int[] result = UArys.multiply(this.datas, multiplicand.datas);

      BigNum2 res = new BigNum2(signed, result,  decimalLen);
      // check(this, multiplicand, res, "*", 0, RoundingMode.UNNECESSARY);
      return res;
    }

    /**
     * 除法.
     * @param divisor 除数
     * @param decimalLen 小数部位数
     * @param roundmode 舍入模式
     * @return 商
     */
    public BigNum2 divide(BigNum2 divisor, int decimalLen, BigNumRound roundmode) {
      if (divisor.isZero == 1) {
        // 除数为零时
        throw new ArithmeticException("Division by zero");
      }
      if (divisor.equals(BigNum.ONE)) {
        // a / 1 = a
        return this;
      }
      // TODO:a / -1 = -a
      if (this.isZero == 1) {
        // 0 / a = 0
        return this;
      }
      // a / a = 1
      // -a / a = -1
      // a / -a = -1

      // 最大精度，小数部长度
      int maxDecimalLen = (BigNumRound.HALF_EVENT.equals(roundmode)) ? decimalLen + 1: decimalLen;
      // 符号
      int osigned = (this.signed != divisor.signed) ? -1 : 1;

      // 小数点位置 被除数同步
      int tscale  = this.scale + divisor.length - divisor.scale;

      int ido = 0;
      int oscale = 0; // 小数点位置
      int odecimalCnt = -1; // 小数位数
      // 假定商的位数＝被除数的整数部－除数的长度（已无小数）＋1（至少是个数所以+1）
      int olen = tscale - divisor.datas.length + 1;
      if (olen <= 0) {
        olen = 2 - olen; // 0.0
        oscale = 1;
        odecimalCnt = olen - 1;
        ido = odecimalCnt;
      }
      if (odecimalCnt < 0) {
        odecimalCnt = 0;
      }
      int[] out = new int[olen];

      int idx = 0;
      int[] tmp = new int[divisor.datas.length];
      if (this.datas.length < tmp.length) {
        // 不足位零补齐
        System.arraycopy(this.datas, idx, tmp, idx, this.datas.length);
      } else {
        System.arraycopy(this.datas, idx, tmp, idx, tmp.length);
      }

      int idxNext = tmp.length;
      while (true) {
//        printary(tmp);
        if (divide_cmp_ary(tmp, divisor.datas) >= 0) {
        out[ido] = out[ido] + 1;
        // shift postition
        tmp = divide_sub_ary(tmp, tmp.length, divisor.datas);
        } else {
        int[] temp;
        if (tmp[0] == 0) {
          temp = new int[tmp.length];
          System.arraycopy(tmp, 1, temp, 0, tmp.length - 1);
        } else {
          temp = new int[tmp.length + 1];
          System.arraycopy(tmp, 0, temp, 0, tmp.length);
        }

        if (odecimalCnt >= 0) {
          odecimalCnt ++;
        }
        idx = idxNext;
        if (idx == tscale) {
          // 小数点位置
          oscale = ido + 1;
        }

        if (idx < this.datas.length) {
//          System.arraycopy(this.datas, idx, temp, temp.length - 1, 1);
          temp[temp.length - 1] = this.datas[idx];
        } else {
          if (BigNumRound.HALF_EVENT.equals(roundmode)) {
            // 银行家算法
            // ==5, after is zero?
            if (0 <= (oscale + decimalLen) && (oscale + decimalLen) < ido) {
            if (out[oscale + decimalLen] == 5) {
              if (out[ido] != 0) {
                break;
              }
              if (odecimalCnt > (maxDecimalLen + 10)) {
                break;
              }
            }
            } // if (0 <= (oscale + decimalLen) && (oscale + decimalLen) < ido) {
          }
          // 向小数部延长
          if (odecimalCnt > maxDecimalLen) {
            // 超过指定长度结束。
            // banker
            break;
          }
        }

        // 向右移位
        olen ++;
        int[] out2 = new int[olen];
        System.arraycopy(out, 0, out2, 0, out.length);
        out = out2;

        idxNext ++;
        ido ++;

        tmp = temp;
//        lenTmp = tmp.length;
        }
      }

      RoundingMode rm = RoundingMode.UNNECESSARY;
      if (BigNumRound.UP.equals(roundmode)) {
        rm = RoundingMode.UP;
        // 远离零方向舍入,> 0 进上
        if (out[(oscale + decimalLen - 1)] != 0) {
        out = add_ary(out, (oscale + decimalLen - 1), (int) 1);
        }
      }
      if (BigNumRound.DOWN.equals(roundmode)) {
        rm = RoundingMode.DOWN;
        // 趋向零方向舍入,> 0 舍下
      }
      if (BigNumRound.CELLING.equals(roundmode)) {
        rm = RoundingMode.CEILING;
        // 向正无穷方向舍入,
        if (osigned > 0) {
        if (out[(oscale + decimalLen - 1)] != 0) {
          out = add_ary(out, (oscale + decimalLen - 1), (int) 1);
        }
        }
      }
      if (BigNumRound.FLOOR.equals(roundmode)) {
        rm = RoundingMode.FLOOR;
        // 向负无穷方向舍入,
        if (osigned < 0) {
        if (out[(oscale + decimalLen - 1)] != 0) {
          out = add_ary(out, (oscale + decimalLen - 1), (int) 1);
        }
        }
      }
      if (BigNumRound.HALF_UP.equals(roundmode)) {
        rm = RoundingMode.HALF_UP;
        // 最近数字舍入(5进)。这是我们最经典的四舍五入。
        if (out[(oscale + decimalLen - 1)] > 4) {
        out = add_ary(out, (oscale + decimalLen - 1), (int) 1);
        }
      }
      if (BigNumRound.HALF_DOWN.equals(roundmode)) {
        rm = RoundingMode.HALF_DOWN;
        // 最近数字舍入(5舍)。在这里5是要舍弃的。五舍六入。
        if (out[(oscale + decimalLen - 1)] > 5) {
        out = add_ary(out, (oscale + decimalLen - 1), (int) 1);
        }
      }
      if (BigNumRound.HALF_EVENT.equals(roundmode)) {
        rm = RoundingMode.HALF_EVEN;
        // 银行家舍入法。
        if (5 < out[(oscale + decimalLen)]) {
        // （2）如果保留位数的后一位如果是6，则进上去。例如5.216保留两位小数为5.22。
        out = add_ary(out, (oscale + decimalLen - 1), (int) 1);
        }
        if (5 == out[(oscale + decimalLen)]) {
        if (is_zero_ary(out, (oscale + decimalLen + 1)) == false) {
          // （4）如果保留位数的后一位如果是5，而且5后面仍有数。例如5.2254保留两位小数为5.23，也就是说如果5后面还有数据，则无论奇偶都要进入。
          out = add_ary(out, (oscale + decimalLen - 1), (int) 1);
        } else {
          if (out[(oscale + decimalLen - 1)] % 2 != 0) {
            // （3）如果保留位数的后一位如果是5，而且5后面不再有数，要根据应看尾数“5”的前一位决定是舍去还是进入: 如果是奇数则进入，如果是偶数则舍去。
            out = add_ary(out, (oscale + decimalLen - 1), (int) 1);
          }
        }
        }
        // （1）要求保留位数的后一位如果是4，则舍去。例如5.214保留两位小数为5.21。
      }
//      int[] out3 = new int[(oscale + decimalLen)];
//      System.arraycopy(out, 0, out3, 0, out3.length);
//      int[] out2 = removeFirstZero(out3, oscale);
//      oscale = oscale - out3.length + out2.length;

      BigNum2 res = new BigNum2(osigned, out,  oscale);
//      BigNum res = new BigNum(osigned, out2,  oscale);
//      check(this, divisor, res, "/", decimalLen, rm);
//      double dres = res.toDouble(16);
//      double t1 = this.toDouble(14);
//      double t2 = divisor.toDouble(14);
//      double chksum = t1 / t2;
//      if (dres != chksum) {
//        throw new ArithmeticException("[ERROR]" + this + "/" + divisor + "="
//          + res + "=>" + dres + "<>" + chksum + "=" + t1 + "/" + t2);
//      }
      return res;
    }
    protected int[] add_ary(int[] data, int pos, int val) {
        int carry = val;
        int an = 0;
        for (int i = pos; i >= 0; i --) {
          an = data[i] + carry;
          if ((an / 10) > 0) {
            carry = an / 10;
          } else {
            carry = 0;
          }
          data[i] = (int) (an % 10);
        }
        if (carry != 0) {
            int[] tmp = new int[data.length + 1];
          tmp[0] = (int) carry;
          System.arraycopy(data, 0, tmp, 1, data.length);
          data = tmp;
        }
        return data;
        }
    protected boolean is_zero_ary(int[] data, int pos) {
        for (int i = pos; i < data.length; i ++) {
          if (data[i] != 0) {
            return false;
          }
        }
        return true;
        }
    /**
     * divide_cmp_ary.
     * @param a int[]
     * @param b int[]
     * @return 1: a > b, 0: a = b, -1:a < b
     */
    protected int divide_cmp_ary(int[] an, int[] bn) {
//      printary(an);
//      printary(bn);

      int offseta = 0;
      while ((bn.length + offseta) < an.length) {
        if (an[offseta] > 0) {
        return 1;
        }
        offseta ++;
      }

      int offsetb = 0;
      while ((an.length + offsetb) < bn.length) {
        if (bn[offsetb] > 0) {
        return -1;
        }
        offsetb ++;
      }

      for (int idx = 0; idx < bn.length; idx ++) {
        if (an[idx + offseta] > bn[idx + offsetb]) {
        return 1;
        }
        if (an[idx + offseta] < bn[idx + offsetb]) {
        return -1;
        }
      }

      return 0;
    }

    /**
     * divide_sub_ary.
     * @param an int[]
     * @param lena int
     * @param bn int[]
     * @return a - b
     */
    protected int[] divide_sub_ary(int[] an, int lena, int[] bn) {
      int[] c2 = new int[lena];
      long mn = 0;
      long carry = 0;
      int posb;
      int posa;
      for (posb = bn.length - 1, posa = lena - 1; posb >= 0 && posa >= 0; posb --, posa --) {
        mn = (an[posa] - bn[posb]) + carry;
        carry = 0;
        if (mn < 0) {
        carry --;
        mn = 10 + mn;
        }
        c2[posa] = (int) mn;
      }
      while (carry == 0 && posa >= 0 && an[posa] != 0) {
        c2[posa] = an[posa];
        posa --;
      }
      while (posa >= 0 && carry < 0) {
        c2[posa] = (int) (an[posa] + carry);
        carry = 0;
        posa --;
      }

      return c2;
    }


    private static final long[] LONG_TEN_POWERS_TABLE = {
        1,               // 0 / 10^0
        10,              // 1 / 10^1
        100,             // 2 / 10^2
        1000,            // 3 / 10^3
        10000,             // 4 / 10^4
        100000,            // 5 / 10^5
        1000000,           // 6 / 10^6
        10000000,          // 7 / 10^7
        100000000,         // 8 / 10^8
        1000000000,        // 9 / 10^9
        10000000000L,        // 10 / 10^10
        100000000000L,       // 11 / 10^11
        1000000000000L,      // 12 / 10^12
        10000000000000L,     // 13 / 10^13
        100000000000000L,    // 14 / 10^14
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
      String res = cache;
      if (res == null) {
        cache = res = toStringx();
      }
      return res;
    }
    public String toStringx() {
//        System.out.println("长度:" + this.length);
//        System.out.println("小数点位置:" + this.scale);
//        System.out.println("==0:" + this.isZero);

        StringBuilder buf = new StringBuilder(this.length + 1);
        if (this.signed == -1) {
          buf.append("-");
        }

        String str = UArys.toString(this.datas, 10);
        System.out.println("[" +str + "]");
        buf.append(str.substring(0, this.scale));
        buf.append(".");
        buf.append(str.substring(this.scale));
//        int idx = 0;
//        for (; idx < this.scale; idx ++) {
//          if (this.datas[idx] >= 62) {
//          } else if (this.datas[idx] >= 36) {
//            buf.append((char)('a' + this.datas[idx] - 36));
//          } else if (this.datas[idx] >= 10) {
//            buf.append((char)('A' + this.datas[idx] - 10));
//          } else {
//            buf.append((char)('0' + this.datas[idx]));
//          }
//        } // for
//          buf.append(".");
//          for (; idx < this.length; idx ++) {
//            if (this.datas[idx] >= 62) {
//            } else if (this.datas[idx] >= 36) {
//              buf.append((char)('a' + this.datas[idx] - 36));
//            } else if (this.datas[idx] >= 10) {
//              buf.append((char)('A' + this.datas[idx] - 10));
//            } else {
//            buf.append((char)('0' + this.datas[idx]));
//            }
//        } // for
//        if (idx == this.scale) {
//          //45.
//          buf.append("0");
//        }
//        System.out.println(buf);
        return buf.toString();
        }
//    private static BigInteger longRadix[] = {
//      null,
//      null,
//      valueOf(0x4000000000000000L),
//      valueOf(0x383d9170b85ff80bL),
//      valueOf(0x4000000000000000L),
//      valueOf(0x6765c793fa10079dL),
//      valueOf(0x41c21cb8e1000000L),
//      valueOf(0x3642798750226111L),
//      valueOf(0x1000000000000000L),
//      valueOf(0x12bf307ae81ffd59L),
//      valueOf( 0xde0b6b3a7640000L), // 10
//      valueOf(0x4d28cb56c33fa539L),
//      valueOf(0x1eca170c00000000L),
//      valueOf(0x780c7372621bd74dL),
//      valueOf(0x1e39a5057d810000L),
//      valueOf(0x5b27ac993df97701L),
//      valueOf(0x1000000000000000L),
//      valueOf(0x27b95e997e21d9f1L),
//      valueOf(0x5da0e1e53c5c8000L),
//      valueOf( 0xb16a458ef403f19L),
//      valueOf(0x16bcc41e90000000L),
//      valueOf(0x2d04b7fdd9c0ef49L),
//      valueOf(0x5658597bcaa24000L),
//      valueOf( 0x6feb266931a75b7L),
//      valueOf( 0xc29e98000000000L),
//      valueOf(0x14adf4b7320334b9L),
//      valueOf(0x226ed36478bfa000L),
//      valueOf(0x383d9170b85ff80bL),
//      valueOf(0x5a3c23e39c000000L),
//      valueOf( 0x4e900abb53e6b71L),
//      valueOf( 0x7600ec618141000L),
//      valueOf( 0xaee5720ee830681L),
//      valueOf(0x1000000000000000L),
//      valueOf(0x172588ad4f5f0981L),
//      valueOf(0x211e44f7d02c1000L),
//      valueOf(0x2ee56725f06e5c71L),
//      valueOf(0x41c21cb8e1000000L)
//    };
//private static int digitsPerLong[] = {
//       0, // 0
//       0, // 1
//      62, // 2 01
//      39, // 3 012
//      31, // 4 0123
//      27, // 5 01234
//      24, // 6 012345
//      22, // 7 0123456
//      20, // 8 01234567
//      19, // 9 012345678
//      18, //10 0123456789
//      18, //11 0123456789A
//      17, //12 0123456789AB
//      17, //13 0123456789ABC
//      16, //14 0123456789ABCD
//      16, //15 0123456789ABCDE
//      15, //16 0123456789ABCDEF
//      15, //17 0123456789ABCDEFG
//      15, //18 0123456789ABCDEFGH
//      14, //19 0123456789ABCDEFGHI
//      14, //20 0123456789ABCDEFGHIJ
//      14, //21 0123456789ABCDEFGHIJK
//      14, //22 0123456789ABCDEFGHIJKL
//      13, //23 0123456789ABCDEFGHIJKLM
//      13, //24 0123456789ABCDEFGHIJKLMN
//      13, //25 0123456789ABCDEFGHIJKLMNO
//      13, //26 0123456789ABCDEFGHIJKLMNOP
//      13, //27 0123456789ABCDEFGHIJKLMNOPQ
//      13, //28 0123456789ABCDEFGHIJKLMNOPQR
//      12, //29 0123456789ABCDEFGHIJKLMNOPQRS
//      12, //30 0123456789ABCDEFGHIJKLMNOPQRST
//      12, //31 0123456789ABCDEFGHIJKLMNOPQRSTU
//      12, //32 0123456789ABCDEFGHIJKLMNOPQRSTUV
//      12, //33 0123456789ABCDEFGHIJKLMNOPQRSTUVW
//      12, //34 0123456789ABCDEFGHIJKLMNOPQRSTUVWX
//      12, //35 0123456789ABCDEFGHIJKLMNOPQRSTUVWXY
//      12  //36 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ
//    };
//    /* zero[i] is a string of i consecutive zeros. */
//    private static String zeros[] = new String[64];
//    static {
//      zeros[63] =
//        "000000000000000000000000000000000000000000000000000000000000000";
//      for (int i=0; i < 63; i++) {
//        zeros[i] = zeros[63].substring(0, i);
//      }
//    }
//
//    private String smallToString(int radix) {
//      if (signum == 0) {
//        return "0";
//      }
//
//      // Compute upper bound on number of digit groups and allocate space
//      int maxNumDigitGroups = (4 * this.dats.length + 6) / 7;
//      String digitGroup[] = new String[maxNumDigitGroups];
//
//      // Translate number to string, a digit group at a time
//      int[] tmp = this.dats;
//      int numGroups = 0;
//      while (tmp.signum != 0) {
//        BigInteger d = longRadix[radix];
//
//        MutableBigInteger q = new MutableBigInteger(),
//                    a = new MutableBigInteger(tmp.mag),
//                    b = new MutableBigInteger(d.mag);
//        MutableBigInteger r = a.divide(b, q);
//        BigInteger q2 = q.toBigInteger(tmp.signum * d.signum);
//        BigInteger r2 = r.toBigInteger(tmp.signum * d.signum);
//
//        digitGroup[numGroups++] = Long.toString(r2.longValue(), radix);
//        tmp = q2;
//      }
//
//      // Put sign (if any) and first digit group into result buffer
//      StringBuilder buf = new StringBuilder(numGroups*digitsPerLong[radix]+1);
//      if (signum < 0) {
//        buf.append('-');
//      }
//      buf.append(digitGroup[numGroups-1]);
//
//      // Append remaining digit groups padded with leading zeros
//      for (int i=numGroups-2; i >= 0; i--) {
//        // Prepend (any) leading zeros for this digit group
//        int numLeadingZeros = digitsPerLong[radix]-digitGroup[i].length();
//        if (numLeadingZeros != 0) {
//            buf.append(zeros[numLeadingZeros]);
//        }
//        buf.append(digitGroup[i]);
//      }
//      return buf.toString();
//    }

    /**
     * 比较大小.
     */
    public int compareTo(BigNum2 on) {
      int result = 0;
      if (this.signed > on.signed) {
        return 1;
      }
      if (this.signed < on.signed) {
        return -1;
      }

      int max = this.scale;
      int pre = max - on.scale;
      boolean blReadThis = true;
      if (max < on.scale) {
        max = on.scale;
        pre = max - this.scale;
        blReadThis = false;
      }
      int an;
      int bn;
      if (pre > 0) {
        if (blReadThis) {
        for (an = 0; an < pre; an ++) {
          if (this.datas[an] > 0) {
            result = 1;
            break;
          }
        }
        } else {
        for (an = 0; an < pre; an ++) {
          if (on.datas[an] > 0) {
            result = -1;
            break;
          }
        }
        }
      }
      if (result == 0) {
        // 整数部
        // for (a = this.scale - 1, b = o.scale - 1; a >= 0 && b >= 0; a --, b --) {
        for (an = 0, bn = 0; an < this.scale && bn < on.scale; an ++, bn ++) {
        result = this.datas[an] - on.datas[bn];
        if ( result != 0 ) {
          break;
        }
        }
      }
      if (result == 0) {
        // 小数部
        for (an = this.scale, bn = on.scale; 0 <= an && an < this.length
          && 0 <= bn && bn < on.length; an ++, bn ++) {
        result = this.datas[an] - on.datas[bn];
        if ( result != 0 ) {
          break;
        }
        }
        if (result == 0) {
        if (an < this.length) {
          result = 1;
        } else if (bn < on.length) {
          result = -1;
        }
        }
      }
      return this.signed * result;
    }

    /**
     * 是否相等
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof BigNum2) {
        return this.compareTo((BigNum2) obj) == 0;
      } else if (obj instanceof String) {
        return this.compareTo(new BigNum2((String) obj)) == 0;
      } else {
        return false;
      }
    }

    /**
     * negate.
     * @return BigNum
     */
    public BigNum2 negate() {
      return new BigNum2((0x00 - this.signed), this.datas,  this.scale);
    }

    /**
     * plus.
     * @return BigNum
     */
    public BigNum2 plus() {
      return this;
    }

    /**
     * abs.
     * @return BigNum
     */
    public BigNum2 abs() {
      return (this.signed  < 0 ? negate() : this);
    }

}
