package javay.math;

import sun.misc.FloatingDecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Stack;

/**
 * 大数
 * 0123456789
 * -abc.edfhi = len:8, dot pos:4
 * @author DBJ
 *
 */
public class BigNum implements Comparable<BigNum> {
  /** 符号:正号:1,负号:-1. */
  private int signed;
  /** 数据 十进制 一位对应一位. */
  private int[] datas;
  /** 数据的长度. */
  private int length;
  /** 小数点的起始位置. */
  private int scale;
  /** zero */
  private boolean isZero;

  public static final BigNum ZERO = new BigNum("0");
  public static final BigNum ONE  = new BigNum("1");

  //                      3.14159265358979323846
  //                      3.1415926535897932384626433832795
  //                      0.00000000011111111112222222222333333333344444444445
  //                      0.12345678901234567890123456789012345678901234567890
  public static final BigNum PI
      = new BigNum("3.14159265358979323846264338327950288419716939937510");
  public static final BigNum E
      = new BigNum("2.71828182845904523536028747135266249775724709369995");
  public static final BigNum LN10 = new BigNum("2.3025850929940456840179914547");
  public static final BigNum LNR = new BigNum("0.2002433314278771112016301167");
  public static final BigNum BYTE_MIN_VALUE = new BigNum("-128");
  public static final BigNum BYTE_MAX_VALUE = new BigNum( "127");
  public static final BigNum SHORT_MIN_VALUE = new BigNum("-32768");
  public static final BigNum SHORT_MAX_VALUE = new BigNum( "32767");
  public static final BigNum INT_MIN_VALUE = new BigNum("-2147483648");
  public static final BigNum INT_MAX_VALUE = new BigNum( "2147483647");
  public static final BigNum LONG_MIN_VALUE = new BigNum("-9223372036854775808");
  public static final BigNum LONG_MAX_VALUE = new BigNum( "9223372036854775807");

  /**
   * 构造函数.
   * @param in 输入数据 字符数组
   * @param offset 起始位置
   * @param len 长度
   * @param numberSystem 进制系统
   */
  public BigNum(char[] in, int offset, int len, int numberSystem) {
    /* 初始化 */
    this.signed = 0x01;

    /* 符号判断 */
    int idx = offset;
    if (in[idx] == '-') {
      this.signed = -1;
      idx ++;
    } else if (in[idx] == '+') {
      idx ++;
    }

    /* 数值变换  */
    int idy = 0;
    this.scale = -1;
    int cnt = 0;
    int[] dats = new int[len];
    for (; idx < len; idx ++) {
      if (in[idx] == '.') {
        if (this.scale != -1) {
          throw new NumberFormatException();
        }
        this.scale = cnt;
        continue;
      }
      cnt ++;

      if (in[idx] >= '0' && in[idx] <= '9') {
        dats[idy] = (in[idx] - '0');
      } else if (in[idx] >= 'A' && in[idx] <= 'F') {
        dats[idy] =  (in[idx] - 'A' + 10);
      } else {
        throw new NumberFormatException();
      }
      idy ++;
    }

    this.length = idy;
    if (this.scale <= 0 || this.scale == this.length) {
      // 整数a.的时候，补成a.0的形式。
      this.scale = this.length;
      this.length ++;
      this.datas = new int[this.length];
      int size = this.length;
      if (size > dats.length) {
        size = dats.length;
      }
      this.datas[this.datas.length - 1] = 0;
      System.arraycopy(dats, 0, this.datas, 0, size);
      dats = null;
    } else {
      this.datas = new int[idy];
      System.arraycopy(dats, 0, this.datas, 0, idy);
      dats = null;
    }

//    this.datas = this.removeLastZero(this.datas, this.scale);
    // TODO:??
//    this.datas = this.trimZero(this.datas, this.scale);
    this.length = this.datas.length;
    if (numberSystem != 10) {
      BigNum res = this.createNum(0);
      BigNum ns   = this.createNum(numberSystem);
      int indx = 0;
      for (int i = this.scale - 1; i >= 0; i --, indx ++) {
        res = res.add(ns.pow(i).multiply(this.createNum(this.datas[indx])));
      }
      for (int i = 1; i <= (this.length - this.scale); i ++, indx ++) {
        res = res.add(
            this.createNum(this.datas[indx]).divide(ns.pow(i), 40, BigNumRound.HALF_EVENT));
      }
      this.datas = res.datas;
      this.length = res.length;
      this.scale = res.scale;
    }
    
    // check is zero.
    this.isZero = chkIsZero();
  }

  private BigNum createNum(int in) {
    int signed = 0x01;
    int an = in;
    if (in < 0) {
      signed = -0x01;
      an = -in;
    }
    int lengt = 1;
    int scal = 1;
    int[] datas = null;
    if (an < 10) {
      datas = new int[1];
      datas[0] =  in;
    } else if (an < 100) {
      lengt = 2;
      scal = 2;
      datas = new int[2];
      if (an < 20) {
        datas[0] = 1;
        datas[1] = (an - 10);
      }
    }
    return new BigNum(signed, datas, scal);
  }

  /**
   * 构造函数.
   * @param si 符号
   * @param da 数据
   * @param len 长度
   * @param sca 小数点位置
   */
  public BigNum(int si, int[] da, int len, int sca) {
    this.signed = si;
    this.datas = da;
    this.length = len;
    this.scale = sca;
    // check is zero.
    this.isZero = chkIsZero();
  }

  // TODO:0.0
  public BigNum(int si, int[] in, int dotpos) {
    this.signed = si;
    this.scale = dotpos;

    int start = 0;
    for (; start < dotpos && in[start] == 0; start ++) ;
    this.scale -= start;

    int end = in.length - 1;
    for (;end > dotpos && in[end] == 0; end --) ;

    this.isZero = in[start] == 0 && in[end] == 0;
    this.length = end - start + 1;
    this.datas = new int[this.length];
    for (int idxi = start, idxr = 0; idxr < this.length; idxi ++, idxr ++) {
      this.datas[idxr] = in[idxi];
//      if (this.isZero && this.datas[idxr] != 0) {
//        this.isZero = false;
//      }
    }
  }

  /**
   * BigNum.
   * @param on BigNum
   */
  public BigNum(BigNum on) {
    this.scale = on.scale;
    this.signed = on.signed;
    this.datas = on.datas;
    this.length = on.length;
    this.isZero = on.isZero;
  }

  /**
   * 构造函数.
   * @param str 字符串
   */
  public BigNum(String str) {
    this(str.toCharArray(), 0 , str.toCharArray().length, 10);
  }

  public BigNum(String str, int numberSystem) {
    this(str.toCharArray(), 0 , str.toCharArray().length, numberSystem);
  }

  public BigNum(byte bn) {
    this(Byte.toString(bn));
  }

  /**
   * BigNum.
   * @param snum short
   */
  public BigNum(short snum) {
    this(Short.toString(snum));
  }

  public BigNum(int inum) {
    this(Integer.toString(inum));
  }

  public BigNum(long ln) {
    this(Long.toString(ln));
  }


  public BigNum(float fn) {
    this(Float.toString(fn));
  }

  public BigNum(double dn) {
    this(Double.toString(dn));
  }

  /* ********************************************
   * Option
   * ********************************************/

  /**
   * 加法.
   * @param augend 加数
   * @return 和
   */
  public BigNum add(BigNum augend) {
    if (augend.isZero) {
      // a + 0 = a
      return this;
    }
    if (this.isZero) {
      // 0 + a = a
      return augend;
    }

    if (this.signed == augend.signed) {
      /* 整数部长度 */
      int scaleS = this.scale;
      if (augend.scale > scaleS) {
        scaleS = augend.scale;
      }
      /* 小数部长度 */
      int decS = this.length - this.scale - 1;
      if ((augend.length - augend.scale - 1) > decS) {
        decS = augend.length - augend.scale - 1;
      }
      // System.out.println("整数部长度:" + scaleS + "小数部长度:" + decS);
      /* 长度 */
      int lengthS = 2 + scaleS + decS;
      int[] dataS = new int[lengthS];

      int an = 0;
      /* 小数部 */
      for (
        int idx = decS, idt = this.scale + decS, ida = augend.scale + decS, ido = 1 + scaleS + decS;
        idx > 0;
        idx --, idt --, ida --, ido --) {
        if (0 <= idt && idt < this.length) {
          an = an + this.datas[idt];
        }
        if (0 <= ida && ida < augend.length) {
          an = an + augend.datas[ida];
        }
        if (an >= 20) {
          // TODO:ERROR
          System.out.println("ERROR by an.");
        } else if (an >= 10) {
          dataS[ido] = an - 10;
          an = 1;
        } else {
          dataS[ido] = an;
          an = 0;
        }
      }
      /* 整数部 */
      for (
        int idx = 0, idt = this.scale, ida = augend.scale, ido = 1 + scaleS;
        idx <= scaleS;
        idx ++, idt --, ida --, ido --) {
        if (0 <= idt && idt < this.datas.length) {
          an = an + this.datas[idt];
        }
        if (0 <= ida && ida < augend.datas.length) {
          an = an + augend.datas[ida];
        }
        if (an >= 20) {
          // TODO:ERROR
          System.out.println("ERROR by an.");
        } else if (an >= 10) {
          if (ido < dataS.length) {
            dataS[ido] = an - 10;
          }
          an = 1;
        } else {
          if (ido < dataS.length) {
            dataS[ido] = an;
          }
          an = 0;
        }
      }
      dataS[0] = an;

      scaleS ++;
      BigNum res = new BigNum(this.signed, dataS, scaleS);
//      check(this, augend, res, "+", 0, RoundingMode.UNNECESSARY);
      return res;
    } else {
      if (this.signed < 0) {
        return augend.subtract(new BigNum((0x00 - this.signed), this.datas,
             this.scale));
      } else {
        return this.subtract(new BigNum((0x00 - augend.signed),
            augend.datas,  augend.scale));
      }
    }
  }

  /**
   * 加法.
   * @param augend 加数
   * @return 和
   */
  public BigNum addxy(BigNum augend) {
    if (augend.isZero) {
      // a + 0 = a
      return this;
    }
    if (this.isZero) {
      // 0 + a = a
      return augend;
    }

    if (this.signed == augend.signed) {
      /*
       *  aaa.aa  (5,3)2
       *   bb.bbb (5,2)3
       *    c.c   (2,1)1
       * dddd.d   (5,4)1
       */
      /* 整数部长度 */
      int scaleSx = this.scale;
      int scaleSn = augend.scale;
      if (scaleSn > scaleSx) {
        scaleSx = scaleSn;
        scaleSn = this.scale;
      }
      /* 小数部长度 */
      int decT = this.length - this.scale - 1;
      int decA = augend.length - augend.scale - 1;
      int decS = decT;
      if (decA > decS) {
        decS = decA;
      }
      // System.out.println("整数部长度:" + scaleS + "小数部长度:" + decS);
      /* 长度 */
      int lengthS = 2 + scaleSx + decS;
      int[] tdata = new int[lengthS];
      int[] adata = new int[lengthS];
      int[] dataS = new int[lengthS];

      for (int idxt = 0, idt = 1 + scaleSx - this.scale; idxt < this.length; idxt ++, idt ++) {
        tdata[idt] = this.datas[idxt];
      }
      for (int idxt = 0, ida = 1 + scaleSx - augend.scale; idxt < augend.length; idxt ++, ida ++) {
        adata[ida] = augend.datas[idxt];
      }

      int an = 0;
      /* 整数部+小数部 */
      for (int idx = lengthS - 1; idx > 0; idx --) {
         an = an + tdata[idx] + adata[idx];
        if (an >= 20) {
          // TODO:ERROR
          System.out.println("ERROR by an>=20." + an);
        } else 
        if (an >= 10) {
          dataS[idx] =  an - 10;
          an = 1;
        } else {
          dataS[idx] =  an;
          an = 0;
        }
      }
      dataS[0] = an;

      scaleSx ++;

      BigNum res = new BigNum(this.signed, dataS, scaleSx);
//      check(this, augend, res, "+", 0, RoundingMode.UNNECESSARY);
      return res;
    } else {
      if (this.signed < 0) {
        return augend.subtract(new BigNum((0x00 - this.signed), this.datas,
             this.scale));
      } else {
        return this.subtract(new BigNum((0x00 - augend.signed),
            augend.datas,  augend.scale));
      }
    }
  }

  /**
   * 加法.
   * @param augend 加数
   * @return 和
   */
  public BigNum addxx(BigNum augend) {
    if (augend.isZero) {
      // a + 0 = a
      return this;
    }
    if (this.isZero) {
      // 0 + a = a
      return augend;
    }

    if (this.signed == augend.signed) {
      /*
       *  aaa.aa  (5,3)2
       *   bb.bbb (5,2)3
       *    c.c   (2,1)1
       * dddd.d   (5,4)1
       */
      /* 整数部长度 */
      int scaleS = this.scale;
      if (augend.scale > scaleS) {
        scaleS = augend.scale;
      }
      scaleS ++;
      /* 小数部长度 */
      int decT = this.length - this.scale;
      int decA = augend.length - augend.scale;
      int decS = decT;
      if (decA > decS) {
        decS = decA;
      }
      // System.out.println("整数部长度:" + scaleS + "小数部长度:" + decS);
      /* 长度 */
      int lengthS = scaleS + decS;
      int[] dataS = new int[lengthS];

      int offT = scaleS - this.scale;
      int offA = scaleS - augend.scale;

      int posT = offT + this.length;
      int posA = offA + augend.length;

      int posC = posT;
      int posD = posA;
      int[] read = this.datas;
      int   readof = offT;
      if (posA > posC) {
        posC = posA;
        posD = posT;
        read = augend.datas;
        readof = offA;
      }

      int idx = lengthS - 1;
      while(idx >= posD) {
        dataS[idx] = read[idx - readof];
        idx --;
      }

      int posE = offT;
      read = augend.datas;
      readof = offA;
      if (offA > posE) {
        posE = offA;
        read = this.datas;
        readof = offT;
      }

      int an = 0;
      while(idx >= posE) {
        dataS[idx] = an + this.datas[idx - offT] + augend.datas[idx - offA];
        an = 0;
        if (dataS[idx] >= 10) {
          dataS[idx] -=  10;
          an = 1;
        }
        idx --;
      }

      while(idx > 0) {
        dataS[idx] = an + read[idx - readof];
        an = 0;
        if (dataS[idx] >= 10) {
          dataS[idx] -= 10;
          an = 1;
        }
        idx --;
      }
      dataS[0] = an;

      BigNum res = new BigNum(this.signed, dataS, scaleS);
//      check(this, augend, res, "+", 0, RoundingMode.UNNECESSARY);
      return res;
    } else {
      if (this.signed < 0) {
        return augend.subtract(new BigNum((0x00 - this.signed), this.datas,
             this.scale));
      } else {
        return this.subtract(new BigNum((0x00 - augend.signed),
            augend.datas,  augend.scale));
      }
    }
  }

  /**
   * subtract.
   * @param subtrahendi BigNum
   * @return BigNum
   */
  public BigNum subtract(BigNum subtrahendi) {
    if (subtrahendi.isZero) {
      return this;
    }
    if (this.isZero) {
      return new BigNum((0x00 - subtrahendi.signed), subtrahendi.datas,
           subtrahendi.scale);
    }
    if (this.signed == subtrahendi.signed) {
      int signeds = 0x01;
      // 大小调整
      BigNum minuend = this;
      BigNum subtrahend = subtrahendi;
      if (minuend.abs().compareTo(subtrahendi.abs()) < 0) {
        minuend = subtrahendi;
        subtrahend = this;
        signeds = -1;
      }

      /* 整数部长度 */
      int scaleS = minuend.scale;
      if (subtrahend.scale > scaleS) {
        scaleS = subtrahend.scale;
      }
      /* 小数部长度 */
      int decS = minuend.length - minuend.scale - 1;
      if ((subtrahend.length - subtrahend.scale - 1) > decS) {
        decS = subtrahend.length - subtrahend.scale;
      }
      /* 长度 */
      int lengthS = 2 + scaleS + decS;
      int[] dataS = new int[lengthS];
      int[] carryS = new int[lengthS];

      long an = 0;
      int carry = 0;
      /* 小数部 */
      for (int idx = decS; idx > 0; idx --) {
        if (0 <= (minuend.scale + idx) && (minuend.scale + idx) < minuend.length) {
          an = an + minuend.datas[minuend.scale + idx];
        }
        if (0 <= (subtrahend.scale + idx) && (subtrahend.scale + idx) < subtrahend.length) {
          an = an - subtrahend.datas[subtrahend.scale + idx];
        }
        if (an < 0) {
          an = 10 + an;
          carry = -1;
        }
        dataS[1 + scaleS + idx] = (int) (0xFF & (an % 10));
        carryS[1 + scaleS + idx] = carry;
        an = an / 10;
        an = an + carry;
        carry = 0;
      }
      /* 整数部 */
      for (int idx = 0; idx <= scaleS; idx ++) {
        if ((minuend.scale - idx) >= 0 && (minuend.scale - idx) < minuend.datas.length) {
          an = an + minuend.datas[minuend.scale - idx];
        }
        if ((subtrahend.scale - idx) >= 0 && (subtrahend.scale - idx) < subtrahend.datas.length) {
          an = an - subtrahend.datas[subtrahend.scale - idx];
        }
        if (an < 0) {
          an = 10 + an;
          carry = -1;
        }
        if ((1 + scaleS - idx) < dataS.length) {
          dataS[1 + scaleS - idx] = (int) (0xFF & (an % 10));
          carryS[1 + scaleS - idx] = carry;
        }
        an = an / 10;
        an = an + carry;
        carry = 0;
      }

      if (an < 0) {
        an = 10 + an;
        carry = -1;
      }
      dataS[0] = (int) (0xFF & an);
      carryS[0] = carry;

      scaleS += 1;
      /*
      byte[] dataS1 = removeFirstZero(dataS, scaleS);

      BigNum res = new BigNum(signeds, dataS1, dataS1.length,
          dataS1.length - dataS.length + scaleS);
      */
      BigNum res = new BigNum(signeds, dataS,  scaleS);
//      check(this, subtrahendi, res, "-", 0, RoundingMode.UNNECESSARY);
      return res;
    } else {
      return this.add(new BigNum((0x00 - subtrahendi.signed),
          subtrahendi.datas,  subtrahendi.scale));
    }
  }

  /**
   * multiply.
   * @param multiplicand BigNum
   * @return BigNum
   */
  public BigNum multiply(BigNum multiplicand) {
    if (multiplicand.isZero) {
      return multiplicand;
    }
    if (multiplicand.equals(BigNum.ONE)) {
      return this;
    }
    if (this.isZero) {
      return this;
    }
    if (this.equals(BigNum.ONE)) {
      return multiplicand;
    }
    /* 符号 */
    int signed1 = this.signed;
    int signed2 = multiplicand.signed;
    final int signed = (signed1 * signed2);

    /* 长度 */
    int len1 = this.length;
    int len2 = multiplicand.length;
    int len = len1 + len2 + 1;

    /* 小数点位置 */
    int scale1 = this.scale;
    int scale2 = multiplicand.scale;
    // 小数部长度
    final int decimalLen = len - ((len1 - scale1) + (len2 - scale2));

    /* 数据 */
    long[][] data = new long[len][len];
    int xn = 0;
    int yn = 0;
    for (int idx = multiplicand.length - 1; idx >= 0; idx --) {
      for (int idy = this.length - 1; idy >= 0; idy --) {
        data[xn][yn] = multiplicand.datas[idx] * this.datas[idy];
        yn ++;
      }
      xn ++;
      yn = xn;
    }

    long[] dat = new long[len];
    long carry = 0;
    for (int n = 0; n < data[0].length; n ++) {
      long value = carry;
      carry = 0;
      for (int m = 0; m < data.length; m ++) {
        value = value + data[m][n];
        if (value >= 10) {
          carry += value / 10;
          value %= 10;
        }
      }
      dat[n] = value;
    }

    int[] result = new int[len];
    if (carry != 0) {
      // carry是最高位。
      len ++;
      result = new int[len];
    }
    long carry2 = 0;
    int jn = len - 1;
    for (int i = 0; i < dat.length; i ++) {
      if ((dat[i] + carry2) >= 10) {
        result[jn - i] = (int) (( dat[i] + carry2 ) % 10);
        carry2 = dat[i] / 10;
      } else {
        result[jn - i] = (int) (dat[i] + carry2);
      }
    }
    if (jn - dat.length >= 0) {
      if ((carry + carry2)  >= 10) {
        result[jn - dat.length] = (int) ((carry + carry2 ) % 10);
        carry2 = (carry + carry2) / 10;
        if (carry2 != 0) {
          // TODO:ERROR
        }
      } else {
        result[jn - dat.length] = (int) (carry + carry2);
      }
    } else {
      // TODO:ERROR
    }

    /*
    // remove zero;
    byte[] result1 = removeFirstZero(result, decimalLen);

    BigNum res = new BigNum(signed, result1, result1.length,
        result1.length - result.length + decimalLen);
    */
    BigNum res = new BigNum(signed, result,  decimalLen);
    check(this, multiplicand, res, "*", 0, RoundingMode.UNNECESSARY);
    return res;
  }

  /**
   * 除法.
   * @param divisor BigNum
   * @param decimalLen int
   * @param roundmode BigNumRound
   * @return BigNum
   */
  public BigNum divide(BigNum divisor, int decimalLen, BigNumRound roundmode) {
    if (divisor.isZero) {
      // 除数为零时
      throw new ArithmeticException("Division by zero");
    }
    if (divisor.equals(BigNum.ONE)) {
      return this;
    }
    // -1
    if (this.isZero) {
      return this;
    }

    // 符号
    int osigned = 0x01;
    if (this.signed != divisor.signed) {
      osigned = -1;
    }

    // 小数点位置
    int dscale  = divisor.scale;
    int tscale  = this.scale;

    // 被除数同步
    tscale += divisor.length - dscale;

    // 最大精度，小数部长度
    int maxDecimalLen = decimalLen;
    if (BigNumRound.HALF_EVENT.equals(roundmode)) {
      maxDecimalLen ++;
    }

    int dlen  = divisor.length;
    int[] tmpDivi = this.removeFirstZero(divisor.datas, divisor.datas.length);
    dlen = tmpDivi.length;

    int ido = 0;
    int oscale = 0; // 小数点位置
    int odecimalCnt = -1; // 小数位数
    // 假定商的位数＝被除数的整数部－除数的长度（已无小数）＋1（至少是个数所以+1）
    int olen = tscale - dlen + 1;
    if (olen <= 0) {
      olen = 2 - olen; // 0.0
      oscale = 1;
      odecimalCnt = olen - 1;
      ido = odecimalCnt;
    }
    if (odecimalCnt < 0) {
    	odecimalCnt = 0;
    }
    System.out.println("@div:odecimalCnt=" + odecimalCnt + ",olen=" +olen);
    int[] out = new int[olen];

    int idx = 0;
    int idxNext = 0;
    int[] tmp = new int[dlen];
    int lenTmp = tmp.length;
    if (this.datas.length < tmp.length) {
      // 不足位零补齐
      System.arraycopy(this.datas, idx, tmp, idx, this.datas.length);
    } else {
      System.arraycopy(this.datas, idx, tmp, idx, lenTmp);
    }

    idxNext = lenTmp;

    while (true) {
      int cn = cmp_ary(tmp, lenTmp, tmpDivi);
      System.out.println("@div:cn=" + cn);
      if (cn >= 0) {
        out[ido] = (int) (out[ido] + 1);
        // shift postition
        tmp = sub_ary(tmp, lenTmp, tmpDivi);
      }
      if (cn < 0) {
        int[] temp;
        if (tmp[0] == 0) {
          temp = new int[tmp.length];
          System.arraycopy(tmp, 1, temp, 0, tmp.length - 1);
        } else {
          temp = new int[lenTmp + 1];
          System.arraycopy(tmp, 0, temp, 0, lenTmp);
        }

        if (odecimalCnt >= 0) {
          odecimalCnt ++;
        }
        idx = idxNext;
        if (idx == tscale) {
          // 小数点位置
          oscale = ido + 1;
        }

        System.out.println("idx=" + idx + ",this.datas.length=" + this.datas.length);
        if (idx < this.datas.length) {
          System.arraycopy(this.datas, idx, temp, temp.length - 1, 1);
        } else {
          if (BigNumRound.HALF_EVENT.equals(roundmode)) {
            // 银行家算法
            // ==5, after is zero?
            System.out.println("(oscale + decimalLen)=" + (oscale + decimalLen) + ",(oscale + decimalLen)=" + (oscale + decimalLen) + ",ido=" + ido);
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
          System.out.println("odecimalCnt=" + odecimalCnt + ",maxDecimalLen=" + maxDecimalLen);
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
        lenTmp = tmp.length;
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
    int[] out3 = new int[(oscale + decimalLen)];
    System.arraycopy(out, 0, out3, 0, out3.length);
    int[] out2 = removeFirstZero(out3, oscale);
    oscale = oscale - out3.length + out2.length;

    BigNum res = new BigNum(osigned, out2,  oscale);
//    check(this, divisor, res, "/", decimalLen, rm);
    double dres = res.toDouble(16);
    double t1 = this.toDouble(14);
    double t2 = divisor.toDouble(14);
    double chksum = t1 / t2;
//    if (dres != chksum) {
//      throw new ArithmeticException("[ERROR]" + this + "/" + divisor + "="
//          + res + "=>" + dres + "<>" + chksum + "=" + t1 + "/" + t2);
//    }
    return res;
  }

  /**
   * cmp_ary.
   * @param a int[]
   * @param lena int
   * @param b int[]
   * @return 1: a > b, 0: a = b, -1:a < b
   */
  protected int cmp_ary(int[] an, int lena, int[] bn) {
    int cn = 0;
    int offset = 0;
    printary(an);
    printary(bn);
    while (lena > (bn.length + offset)) {
      if (an[offset] > 0) {
        cn = 1;
        break;
      }
      offset ++;
    }
    if (cn != 0) {
      return cn;
    }

    int offsetb = 0;
    while (bn.length > (lena + offsetb)) {
      if (bn[offsetb] > 0) {
        cn = -1;
        break;
      }
      offsetb ++;
    }
    if (cn != 0) {
      return cn;
    }
    for (int idx = 0; idx < bn.length; idx ++) {
      if (an[idx + offset] > bn[idx + offsetb]) {
        cn = 1;
        break;
      }
      if (an[idx + offset] < bn[idx + offsetb]) {
        cn = -1;
        break;
      }
    }

    return cn;
  }

  /**
   * sub_ary.
   * @param an int[]
   * @param lena int
   * @param bn int[]
   * @return a - b
   */
  protected int[] sub_ary(int[] an, int lena, int[] bn) {
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

  /**
   * chkIsZero.
   * @return boolean
   */
  public boolean chkIsZero() {
    boolean result = true;
    for (int b : this.datas) {
      if (b != 0) {
        result = false;
        break ;
      }
    }
    return result;
  }

  /**
   * removeFirstZero.
   * @param in 数据
   * @param dotpos 整数部的长度
   * @return 格式化后的数据
   */
  protected int[] removeFirstZero(int[] in, int dotpos) {
    int decimalLen = in.length - dotpos;
    int inu = 0;
    boolean blFlag = false;
    if (0 < dotpos) {
      for (inu = 0; inu < dotpos; inu ++) {
        blFlag = true;
        if (in[inu] != 0) {
          inu ++;
          break;
        }
      }
    }

    if (blFlag && inu == 1 && (inu + decimalLen == in.length)) {
      // 0.* 去除个数+小数部长度=数据长度的话，不需要格式化
      return in;
    }

    int[] res;
    if (dotpos > 1 && inu == dotpos) {
      // 整数部为零
      res = new int[decimalLen + 1];
      //i --;
    } else {
      if (blFlag) {
        res = new int[decimalLen + dotpos - inu + 1];
        //i --;
      } else {
        res = new int[decimalLen + dotpos + 1];
        inu = 0;
      }
    }

    inu -= 2;
    if (inu < -1) {
      inu = -1;
    }
    if (res.length <= in.length) {
      System.arraycopy(in, inu + 1, res, 0, res.length);
    } else {
      System.arraycopy(in, inu + 1, res, res.length - in.length, in.length);
    }
    return res;
  }

  /**
   * mod.
   * @param divisor BigNum
   * @return BigNum
   */
  public BigNum mod(BigNum divisor) {
    if (divisor.isZero) {
      // 除数为零时
      throw new ArithmeticException("Division by zero");
    }
    if (divisor.equals(BigNum.ONE)) {
      return this;
    }
    if (this.isZero) {
      return this;
    }

    // 符号
    int osigned = 0x01;
    if (this.signed != divisor.signed) {
      osigned = -1;
    }

    // 位数
    int dlen = divisor.length;
    int tlen = this.length;
    final int olen = tlen - dlen + 1;

    // 小数点位置
    int dscale = divisor.scale;
    int tscale = this.scale;
    // 被除数同步
    int off = dlen - dscale;
    tscale += off;

    // 小数部长度
    int odecimalLen = tlen - tscale;
    if (tlen < dlen) {
      odecimalLen += dlen - tlen;
    }

    int idx = 0;
    int idxNext = 0;
    int[] tmp = new int[dlen];
    int lenTmp = tmp.length;
    System.arraycopy(this.datas, idx, tmp, idx, lenTmp);
    idxNext = lenTmp;

    int[] out = new int[olen];
    int ido = 0;
    while (true) {
      int cn = cmp_ary(tmp, lenTmp, divisor.datas);
      if (cn >= 0) {
        out[ido] = (int) (out[ido] + 1);
        // shift postition
        tmp = sub_ary(tmp, lenTmp, divisor.datas);
      }
      if (cn < 0) {
    	  int[] temp;
        if (tmp[0] == 0) {
          temp = new int[tmp.length];
          System.arraycopy(tmp, 1, temp, 0, tmp.length - 1);
        } else {
          temp = new int[tmp.length + 1];
          System.arraycopy(tmp, 0, temp, 0, tmp.length);
        }

        idx = idxNext;
        if (idx < this.datas.length) {
          System.arraycopy(this.datas, idx, temp, temp.length - 1, 1);
        } else {
          // 超过指定长度结束。
          System.out.println("WARNINGGGGGGG");
          out = tmp;
          break;
        }
        idxNext ++;
        ido ++;
        if (ido >= olen) {
          // end
          //tmp
          // 小数点
          int oscale = olen - odecimalLen;
          int lead = 0;
          if ((oscale - off) <= 0) {
            lead = 1 - oscale + off;
          }

          // 数值
          int lent = tmp.length;
          int leny = this.datas.length - 1 - idx;
          if (lent + leny + lead == 0) {
            out = new int[1];
            out[0] = 0;
          } else {
            out = new int[lent + leny + lead];
            for (int i = 0; i < lead; i ++) {
              out[i] = 0;
            }
          }
          if (lent > 0) {
            System.arraycopy(tmp, 0, out, lead, lent);
          }
          if (leny > 0) {
            System.arraycopy(this.datas, idx, out, lead + lent, leny);
          }
          break;
        }

        tmp = temp;
        lenTmp = tmp.length;
      }
    }
    return new BigNum(osigned, out,  out.length - odecimalLen - off);
  }

  /**
   * pow.
   * @param nn long
   * @return BigNum
   */
  public BigNum pow(long nn) {
    if (nn < 0) {
      return new BigNum("1.0").divide(pow(new BigNum(0 - nn)), 40, BigNumRound.HALF_EVENT);
    }
    BigNum result = new BigNum("1");
    if (nn == 0) {
      return result;
    }
    long idx = 0;
    while (idx < nn) {
      idx ++;
      result = result.multiply(this);
    }
    return result;
  }

  /**
   * pow.
   * @param nn BigNum
   * @return BigNum
   */
  public BigNum pow(BigNum nn) {
    if (nn.compareTo(BigNum.ZERO) < 0) {
      return new BigNum("1.0").divide(pow(nn.negate()), 40, BigNumRound.HALF_EVENT);
    }
    BigNum result = new BigNum("1");
    if (nn.isZero) {
      return result;
    }
    BigNum idx = new BigNum("0");
    while (idx.compareTo(nn) < 0) {
      idx = idx.add(BigNum.ONE);
      result = result.multiply(this);
    }
    return result;
  }

  /**
   * 阶乘.
   * 一个正整数的阶乘/层（英语：factorial）是所有小于及等于该数的正整数的积，并且有0的阶乘为1。自然数n的阶乘写作n!。1808年，基斯顿·卡曼引进这个表示法。
   * 素数阶乘是所有小于或等于该数且大于或等于2的素数的积，自然数n的素数阶乘，写作n#。
   * @return BigNum
   */
  public BigNum factorial() {
    if (this.compareTo(BigNum.ZERO) < 0) {
      return this;
    }
    if (this.compareTo(BigNum.ZERO) == 0) {
      return BigNum.ONE;
    }

    BigNum result = new BigNum(this);
    BigNum next = this.subtract(BigNum.ONE);
    next = next.integral();
    while (next.compareTo(BigNum.ZERO) > 0) {
      result = result.multiply(next);
      next = next.subtract(BigNum.ONE);
    }
    return result;
  }

  /**
   * 比较大小.
   */
  public int compareTo(BigNum on) {
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
    if (obj instanceof BigNum) {
      return this.compareTo((BigNum) obj) == 0;
    } else if (obj instanceof String) {
      return this.compareTo(new BigNum((String) obj)) == 0;
    } else {
      return false;
    }
  }

  /**
   * max.
   * @param on BigNum
   * @return BigNum
   */
  public BigNum max(BigNum on) {
    return (this.compareTo(on) >= 0 ? this : on);
  }

  /**
   * min.
   * @param on BigNum
   * @return BigNum
   */
  public BigNum min(BigNum on) {
    return (this.compareTo(on) <= 0 ? this : on);
  }

  /**
   * negate.
   * @return BigNum
   */
  public BigNum negate() {
    return new BigNum((0x00 - this.signed), this.datas,  this.scale);
  }

  /**
   * plus.
   * @return BigNum
   */
  public BigNum plus() {
    return this;
  }

  /**
   * abs.
   * @return BigNum
   */
  public BigNum abs() {
    return (this.signed  < 0 ? negate() : this);
  }

  /**
   * 取整.
   * @return BigNum
   */
  public BigNum integral() {
    int[] data = new int[this.scale];
    System.arraycopy(this.datas, 0, data, 0, this.scale);
    return new BigNum(this.signed, data, this.scale, this.scale);
  }

  /**
   * toBinaryString.
   * @return String
   */
  public String toBinaryString() {
    StringBuffer buf = new StringBuffer();
    if (this.signed > 0) {
      buf.append("+");
    } else {
      buf.append("-");
    }
    BigNum n2 = new BigNum("2.0");
    BigNum zn = this.integral();
    BigNum xn = null;
    xn = this.subtract(zn);
    Stack<Integer> stk = new Stack<Integer>();
    if (zn.compareTo(BigNum.ZERO) == 0) {
      stk.push(0);
    }
    while (zn.compareTo(BigNum.ZERO) > 0) {
      BigNum sn = zn.divide(n2, 0, BigNumRound.DOWN);
      BigNum yn = zn.subtract(sn.multiply(n2));
      stk.push(yn.toInt());
      zn = sn;
    }
    while (!stk.isEmpty()) {
      buf.append(stk.pop());
    }
    if (xn.compareTo(BigNum.ZERO) != 0) {
      buf.append(".");
    }
    int cnt = 0;
    while (cnt < 40 && xn.compareTo(BigNum.ZERO) != 0) {
      BigNum jn = xn.multiply(n2);
      BigNum jz = jn.integral();
      buf.append(jz.toInt());

      BigNum yn = jn.subtract(jz);
      xn = yn;
      cnt ++;
    }
    return buf.toString();
  }

  /**
   * toOctalString.
   * @return String
   */
  public String toOctalString() {
    StringBuffer buf = new StringBuffer();
    if (this.signed > 0) {
      buf.append("+");
    } else {
      buf.append("-");
    }
    BigNum n2 = new BigNum("8.0");
    BigNum zn = this.integral();
    BigNum xn = null;
    xn = this.subtract(zn);
    Stack<Integer> stk = new Stack<Integer>();
    if (zn.compareTo(BigNum.ZERO) == 0) {
      stk.push(0);
    }
    while (zn.compareTo(BigNum.ZERO) > 0) {
      BigNum sn = zn.divide(n2, 0, BigNumRound.DOWN);
      BigNum yn = zn.subtract(sn.multiply(n2));
      stk.push(yn.toInt());
      zn = sn;
    }
    while (!stk.isEmpty()) {
      buf.append(stk.pop());
    }
    if (xn.compareTo(BigNum.ZERO) != 0) {
      buf.append(".");
    }
    int cnt = 0;
    while (cnt < 40 && xn.compareTo(BigNum.ZERO) != 0) {
      BigNum jn = xn.multiply(n2);
      BigNum jz = jn.integral();
      buf.append(jz.toInt());

      BigNum yn = jn.subtract(jz);
      xn = yn;
      cnt ++;
    }
    return buf.toString();
  }

  /**
   * toString.
   */
  @Override
  public String toString() {
    System.out.println("长度:" + this.length);
    System.out.println("小数点位置:" + this.scale);
    System.out.println("=0:" + this.isZero);
    StringBuffer buf = new StringBuffer();
    if (this.signed == -1) {
      buf.append("-");
    }
    int idx = 0;
    String tmp;
    for (idx = 0; idx < this.length; idx ++) {
      int ch = this.datas[idx];
      if (ch >= 62) {
        tmp = ch + ",";
      } else if (ch >= 36) {
        tmp = String.valueOf((char)('a' + ch - 36));
      } else if (ch >= 10) {
        tmp = String.valueOf((char)('A' + ch - 10));
      } else {
        tmp = String.valueOf((char)('0' + ch));
      }
      buf.append(tmp);
      if ((idx + 1) == this.scale) {
        buf.append(".");
      }
    }
    if (idx == this.scale) {
      buf.append("0");
    }
    return buf.toString();
  }

  /**
   * toHexString.
   * @return String
   */
  public String toHexString() {
    StringBuffer buf = new StringBuffer();
    if (this.signed > 0) {
      buf.append("+");
    } else {
      buf.append("-");
    }
    BigNum n2 = new BigNum("16.0");
    BigNum zn = this.integral();
    BigNum xn = null;
    xn = this.subtract(zn);
    Stack<Integer> stk = new Stack<Integer>();
    if (zn.compareTo(BigNum.ZERO) == 0) {
      stk.push(0);
    }
    while (zn.compareTo(BigNum.ZERO) > 0) {
      BigNum sn = zn.divide(n2, 0, BigNumRound.DOWN);
      BigNum yn = zn.subtract(sn.multiply(n2));
      stk.push(yn.toInt());
      zn = sn;
    }
    while (!stk.isEmpty()) {
      int ch = stk.pop();
      String tmp = "";
      if (ch >= 62) {
        tmp = ch + ",";
      } else if (ch >= 36) {
        tmp = String.valueOf((char)('a' + ch - 36));
      } else if (ch >= 10) {
        tmp = String.valueOf((char)('A' + ch - 10));
      } else {
        tmp = String.valueOf((char)('0' + ch));
      }
      buf.append(tmp);
    }
    if (xn.compareTo(BigNum.ZERO) != 0) {
      buf.append(".");
    }
    int cnt = 0;
    while (cnt < 40 && xn.compareTo(BigNum.ZERO) != 0) {
      BigNum jn = xn.multiply(n2);
      BigNum jz = jn.integral();
      int ch = jz.toInt();
      String tmp = "";
      if (ch >= 62) {
        tmp = ch + ",";
      } else if (ch >= 36) {
        tmp = String.valueOf((char)('a' + ch - 36));
      } else if (ch >= 10) {
        tmp = String.valueOf((char)('A' + ch - 10));
      } else {
        tmp = String.valueOf((char)('0' + ch));
      }
      buf.append(tmp);

      BigNum yn = jn.subtract(jz);
      xn = yn;
      cnt ++;
    }
    return buf.toString();
  }

  /**
   * toScientificNotation.
   * @return String
   */
  public String toScientificNotation() {
    // aen
    StringBuffer buf = new StringBuffer();
    if (this.signed < 0) {
      buf.append("-");
    }
    int nn = 0;
    boolean bflag = false;
    int in = 0;
    for (; in < this.scale; in ++) {
      int by = this.datas[in];
      if (bflag) {
        nn ++;
        buf.append(by);
        continue;
      }
      if (by != 0) {
        bflag = true;
        buf.append(by);
        buf.append(".");
        nn ++;
      }
    }
    if (bflag == false) {
      for (;in < this.length; in ++) {
        int by = this.datas[in];
        if (!bflag) {
          nn --;
        } else {
          buf.append(by);
          continue;
        }
        if (by != 0) {
          bflag = true;
          buf.append(by);
          buf.append(".");
        }

      }
      if (!bflag) {
        buf.append("0.0");
      }
    } else {
      for (;in < this.length; in ++) {
        int by = this.datas[in];
        buf.append(by);
      }
    }
    buf.append("e");
    buf.append(nn);
    System.out.println(nn);
    return buf.toString();
  }

  /**
   * toByte.
   * @return byte
   */
  public byte toByte() {
    if (this.compareTo(BigNum.BYTE_MIN_VALUE) < 0 || this.compareTo(BigNum.BYTE_MAX_VALUE) > 0) {
      throw new java.lang.ArithmeticException("Overflow");
    }
    byte result = 0;
    for (int i = 0; i < this.scale; i ++) {
      result = (byte) (result * 10 + this.datas[i]);
    }
    return (byte) (this.signed * result);
  }

  /**
   * toShort.
   * @return short
   */
  public short toShort() {
    if (this.compareTo(BigNum.SHORT_MIN_VALUE) < 0 || this.compareTo(BigNum.SHORT_MAX_VALUE) > 0) {
      throw new java.lang.ArithmeticException("Overflow");
    }
    short result = 0;
    for (int i = 0; i < this.scale; i ++) {
      result = (short) (result * 10 + this.datas[i]);
    }
    return (short) (this.signed * result);
  }

  /**
   * toInt.
   * @return int
   */
  public int toInt() {
    if (this.compareTo(BigNum.INT_MIN_VALUE) < 0 || this.compareTo(BigNum.INT_MAX_VALUE) > 0) {
      throw new java.lang.ArithmeticException("Overflow");
    }
    int result = 0;
    for (int i = 0; i < this.scale; i ++) {
      result = (int) (result * 10 + this.datas[i]);
    }
    return (int) (this.signed * result);
  }

  /**
   * toLong.
   * @return long
   */
  public long toLong() {
    if (this.compareTo(BigNum.LONG_MIN_VALUE) < 0 || this.compareTo(BigNum.LONG_MAX_VALUE) > 0) {
      throw new java.lang.ArithmeticException("Overflow");
    }
    long result = 0;
    for (int i = 0; i < this.scale; i ++) {
      result = (long) (result * 10 + this.datas[i]);
    }
    return (long) (this.signed * result);
  }

  /**
   * toFloat.
   * @return float
   */
  public float toFloat() {
    //float res = 0.0f;
    //return res;
    return FloatingDecimal.parseFloat(this.toString());
  }

  /**
   * toDouble.
   * @param scale int
   * @return double
   */
  public double toDouble(int scale) {
    double res = 0.0;
    String str = this.toString();
    int idxPot = str.indexOf('.');
    if (idxPot < 0) {
      idxPot = 0;
    }
    byte[] bys = str.getBytes();

    int startPos = idxPot + scale;
    if (startPos >= bys.length) {
      startPos = bys.length - 1;
    }
    double an = 1.0;
    int cn = 0;
    for (int i = startPos; i >= 0; i --) {
      byte by = bys[i];
      if ((char) by == '.') {
        an = 1.0;
        cn = -1;
      } else {
        an = Math.pow(10.0, idxPot - i + cn);
        double bn = an * (by - '0');
        res = res + bn;
      }
    }
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
   * round.
   * @param scale int
   * @param roundmode BigNumRound
   * @return BigNum
   */
  public BigNum round(int scale, BigNumRound roundmode) {
    int pos = this.scale + scale - 1;
    int posNext = pos + 1;
    if (pos < 0 || pos > this.length) {
      return this;
    }
    int curVal = this.datas[pos];

    int nexVal = 0;
    if (posNext >= 0 && posNext < this.length) {
      nexVal = this.datas[posNext];
    }
    int signed = this.signed;
    int scaleo = this.scale;
    int length = scaleo;
    if (pos > scaleo) {
      length = pos;
    }
    length ++;
    int[] datas = new int[length];
    System.arraycopy(this.datas, 0, datas, 0, length);

    if (BigNumRound.UP.equals(roundmode)) {
      // 远离零方向舍入,> 0 进上
      if (nexVal != 0) {
        datas = add_ary(datas, pos, (int) 1);
      }
    }
    if (BigNumRound.DOWN.equals(roundmode)) {
      // 趋向零方向舍入,> 0 舍下
    }
    if (BigNumRound.CELLING.equals(roundmode)) {
      // 向正无穷方向舍入,
      if (signed > 0) {
        if (nexVal != 0) {
          datas = add_ary(datas, pos, (int) 1);
        }
      }
    }
    if (BigNumRound.FLOOR.equals(roundmode)) {
      // 向负无穷方向舍入,
      if (signed < 0) {
        if (nexVal != 0) {
          datas = add_ary(datas, pos, (int) 1);
        }
      }
    }
    if (BigNumRound.HALF_UP.equals(roundmode)) {
      // 最近数字舍入(5进)。这是我们最经典的四舍五入。
      if (nexVal > 4) {
        datas = add_ary(datas, pos, (int) 1);
      }
    }
    if (BigNumRound.HALF_DOWN.equals(roundmode)) {
      // 最近数字舍入(5舍)。在这里5是要舍弃的。五舍六入。
      if (nexVal > 5) {
        datas = add_ary(datas, pos, (int) 1);
      }
    }
    if (BigNumRound.HALF_EVENT.equals(roundmode)) {
      // 银行家舍入法。
      if (nexVal > 5) {
        // （2）如果保留位数的后一位如果是6，则进上去。例如5.216保留两位小数为5.22。
        datas = add_ary(datas, pos, (int) 1);
      }
      if (nexVal == 5) {
        if (is_zero_ary(this.datas, posNext + 1) == false) {
          // is not zero
          // （4）如果保留位数的后一位如果是5，而且5后面仍有数。例如5.2254保留两位小数为5.23，也就是说如果5后面还有数据，则无论奇偶都要进入。
          datas = add_ary(datas, pos, (int) 1);
        } else {
          if ((curVal % 2) != 0) {
            // （3）如果保留位数的后一位如果是5，而且5后面不再有数，要根据应看尾数“5”的前一位决定是舍去还是进入: 如果是奇数则进入，如果是偶数则舍去。
            datas = add_ary(datas, pos, (int) 1);
          }
        }
      }
      // （1）要求保留位数的后一位如果是4，则舍去。例如5.214保留两位小数为5.21。
    }
    return new BigNum(signed, datas, length, scaleo);
  }

  /**
   * and.
   * @param num BigNum
   * @return BigNum
   */
  public BigNum and(BigNum num) {
    String tn = this.toBinaryString();
    String nn = num.toBinaryString();
    System.out.println(tn);
    System.out.println(nn);
    int tlen = tn.length();
    int nlen = nn.length();
    int ta = tlen;
    int na = nlen;
    int tb = 0;
    int nb = 0;
    int tpi = tn.indexOf(".");
    int npi = nn.indexOf(".");
    if (tpi >= 0) {
      tb = ta - tpi;
      ta = tpi;
    } else {
      tpi = ta + 1;
      tb = 1;
    }
    if (npi >= 0) {
      nb = na - npi;
      na = npi;
    } else {
      npi = na + 1;
      nb = 1;
    }
    System.out.println("this" + tlen + "," + ta + "," + tb);
    System.out.println(" num" + nlen + "," + na + "," + nb);
    int oa = ta;
    int ob = tb;
    if (oa < na) {
      oa = na;
    }
    if (ob < nb) {
      ob = nb;
    }
    System.out.println(" out" + (oa + ob) + "," + oa + "," + ob);

    char[] tar = new char[oa + ob];
    char[] nar = new char[oa + ob];
    char[] oar = new char[oa + ob];
    Arrays.fill(tar, '0');
    Arrays.fill(nar, '0');
    Arrays.fill(oar, '0');
    int it = tpi - 1;
    int in = npi - 1;
    for (int i = oa - 1; i > 0; i --) {
      // i=0 signed.
      if (it > 0 && it < tlen) {
        tar[i] = tn.charAt(it);
      }
      if (in > 0 && in < nlen) {
        nar[i] = nn.charAt(in);
      }
      System.out.println(i + ":" + tar[i] + "," + nar[i]);
      if (tar[i] == nar[i] && tar[i] == '1') {
        oar[i] = '1';
      }
      it --;
      in --;
    }
    it = tpi + 1;
    in = npi + 1;
    for (int i = oa + 1; i < (oa + ob); i ++) {
      if (it < tlen) {
        tar[i] = tn.charAt(it);
      }
      if (in < nlen) {
        nar[i] = nn.charAt(in);
      }
      if (tar[i] == nar[i] && tar[i] == '1') {
        oar[i] = '1';
      }
      it ++;
      in ++;
    }
    oar[0] = '-';
    if (this.signed == num.signed) {
      oar[0] = '+';
    }
    oar[oa] = '.';
    BigNum res = new BigNum(oar, 0, oa + ob, 2);
    System.out.println(res.toBinaryString());
    return res;
  }

  /**
   * or.
   * @param num BigNum
   * @return BigNum
   */
  public BigNum or(BigNum num) {
    String tn = this.toBinaryString();
    String nn = num.toBinaryString();
    System.out.println(tn);
    System.out.println(nn);
    int tlen = tn.length();
    int nlen = nn.length();
    int ta = tlen;
    int na = nlen;
    int tb = 0;
    int nb = 0;
    int tpi = tn.indexOf(".");
    int npi = nn.indexOf(".");
    if (tpi >= 0) {
      tb = ta - tpi;
      ta = tpi;
    } else {
      tpi = ta + 1;
      tb = 1;
    }
    if (npi >= 0) {
      nb = na - npi;
      na = npi;
    } else {
      npi = na + 1;
      nb = 1;
    }
    System.out.println("this" + tlen + "," + ta + "," + tb);
    System.out.println(" num" + nlen + "," + na + "," + nb);
    int oa = ta;
    int ob = tb;
    if (oa < na) {
      oa = na;
    }
    if (ob < nb) {
      ob = nb;
    }
    System.out.println(" out" + (oa + ob) + "," + oa + "," + ob);

    char[] tar = new char[oa + ob];
    char[] nar = new char[oa + ob];
    char[] oar = new char[oa + ob];
    Arrays.fill(tar, '0');
    Arrays.fill(nar, '0');
    Arrays.fill(oar, '0');
    int it = tpi - 1;
    int in = npi - 1;
    for (int i = oa - 1; i > 0; i --) {
      // i=0 signed.
      if (it > 0 && it < tlen) {
        tar[i] = tn.charAt(it);
      }
      if (in > 0 && in < nlen) {
        nar[i] = nn.charAt(in);
      }
      System.out.println(i + ":" + tar[i] + "," + nar[i]);
      if (tar[i] == '1' || nar[i] == '1') {
        oar[i] = '1';
      }
      it --;
      in --;
    }
    it = tpi + 1;
    in = npi + 1;
    for (int i = oa + 1; i < (oa + ob); i ++) {
      if (it < tlen) {
        tar[i] = tn.charAt(it);
      }
      if (in < nlen) {
        nar[i] = nn.charAt(in);
      }
      if (tar[i] == '1' || nar[i] == '1') {
        oar[i] = '1';
      }
      it ++;
      in ++;
    }
    oar[0] = '-';
    if (this.signed == num.signed) {
      oar[0] = '+';
    }
    oar[oa] = '.';
    BigNum res = new BigNum(oar, 0, oa + ob, 2);
    System.out.println(res.toBinaryString());
    return res;
  }

  /**
   * xor.
   * @param num BigNum
   * @return BigNum
   */
  public BigNum xor(BigNum num) {
    String tn = this.toBinaryString();
    String nn = num.toBinaryString();
    System.out.println(tn);
    System.out.println(nn);
    int tlen = tn.length();
    int nlen = nn.length();
    int ta = tlen;
    int na = nlen;
    int tb = 0;
    int nb = 0;
    int tpi = tn.indexOf(".");
    int npi = nn.indexOf(".");
    if (tpi >= 0) {
      tb = ta - tpi;
      ta = tpi;
    } else {
      tpi = ta + 1;
      tb = 1;
    }
    if (npi >= 0) {
      nb = na - npi;
      na = npi;
    } else {
      npi = na + 1;
      nb = 1;
    }
    System.out.println("this" + tlen + "," + ta + "," + tb);
    System.out.println(" num" + nlen + "," + na + "," + nb);
    int oa = ta;
    int ob = tb;
    if (oa < na) {
      oa = na;
    }
    if (ob < nb) {
      ob = nb;
    }
    System.out.println(" out" + (oa + ob) + "," + oa + "," + ob);

    char[] tar = new char[oa + ob];
    char[] nar = new char[oa + ob];
    char[] oar = new char[oa + ob];
    Arrays.fill(tar, '0');
    Arrays.fill(nar, '0');
    Arrays.fill(oar, '0');
    int it = tpi - 1;
    int in = npi - 1;
    for (int i = oa - 1; i > 0; i --) {
      // i=0 signed.
      if (it > 0 && it < tlen) {
        tar[i] = tn.charAt(it);
      }
      if (in > 0 && in < nlen) {
        nar[i] = nn.charAt(in);
      }
      System.out.println(i + ":" + tar[i] + "," + nar[i]);
      if (tar[i] != nar[i]) {
        oar[i] = '1';
      }
      it --;
      in --;
    }
    it = tpi + 1;
    in = npi + 1;
    for (int i = oa + 1; i < (oa + ob); i ++) {
      if (it < tlen) {
        tar[i] = tn.charAt(it);
      }
      if (in < nlen) {
        nar[i] = nn.charAt(in);
      }
      if (tar[i] != nar[i]) {
        oar[i] = '1';
      }
      it ++;
      in ++;
    }
    oar[0] = '-';
    if (this.signed == num.signed) {
      oar[0] = '+';
    }
    oar[oa] = '.';
    BigNum res = new BigNum(oar, 0, oa + ob, 2);
    System.out.println(res.toBinaryString());
    return res;
  }

  /**
   * not.
   * @return BigNum
   */
  public BigNum not() {
    String tn = this.toBinaryString();
    char[] tar = new char[tn.length()];
    for (int i = 0; i < tar.length; i ++) {
      char ch = tn.charAt(i);
      if (ch == '+') {
        tar[i] = '-';
      }
      if (ch == '-') {
        tar[i] = '+';
      }
      if (ch == '1') {
        tar[i] = '0';
      }
      if (ch == '0') {
        tar[i] = '1';
      }
      tar[i] = ch;
    }
    BigNum res = new BigNum(tar, 0, tar.length, 2);
    System.out.println(res.toBinaryString());
    return res;
  }

  /**
   * lsh.
   * @param shift BigNum
   * @return BigNum
   */
  public BigNum lsh(BigNum shift) {
    if (shift.compareTo(BigNum.ZERO) < 0) {
      return rsh(shift);
    }
    String tn = this.toBinaryString();
    System.out.println(tn);
    int tlen = tn.length();
    int ta = tlen;
    int tb = 0;
    int tpi = tn.indexOf(".");
    if (tpi >= 0) {
      tb = ta - tpi;
      ta = tpi;
    } else {
      tpi = ta + 1;
      tb = 1;
    }
    int olen = ta + tb + shift.toInt();
    char[] obuf = new char[olen];
    Arrays.fill(obuf, '0');
    int on = 0;
    boolean bdot = false;
    for (int i = 0; i < tlen; i ++) {
      char ch = tn.charAt(i);
      if (ch == '.') {
        continue;
      }
      if (on == (ta + shift.toInt())) {
        obuf[on] = '.';
        on ++;
        bdot = true;
      }
      obuf[on] = ch;
      on ++;
    }
    if (bdot == false) {
      obuf[ta + shift.toInt()] = '.';
    }
    BigNum res = new BigNum(obuf, 0, olen, 2);
    System.out.println(res.toBinaryString());
    return res;
  }

  /**
   * rsh.
   * @param shift BigNum
   * @return BigNum
   */
  public BigNum rsh(BigNum shift) {
    if (shift.compareTo(BigNum.ZERO) < 0) {
      return lsh(shift);
    }
    String tn = this.toBinaryString();
    System.out.print("in:" + tn);
    int tlen = tn.length();
    int ta = tlen;
    int tb = 0;
    int tpi = tn.indexOf(".");
    if (tpi >= 0) {
      tb = ta - tpi;
      ta = tpi;
    } else {
      tpi = ta + 1;
      tb = 1;
    }
    int olen = ta + tb;
    int shf = shift.toInt();
    System.out.print(",shift:" + shf);
    char[] obuf = new char[olen];
    Arrays.fill(obuf, '0');
    int on = shf - 1;
    if (shf < tb) {
      on ++;
    } else {
      on += 2;
    }
    boolean bdot = false;
    for (int i = 0; i < olen - shf; i ++) {
      char ch = tn.charAt(i);
      if (ch == '.') {
        continue;
      }
      if (on == (ta)) {
        obuf[on] = '.';
        on ++;
        bdot = true;
      }
      if (on >= olen) {
        break;
      }
      obuf[on] = ch;
      on ++;
    }
    if (bdot == false) {
      obuf[ta] = '.';
    }
    BigNum res = new BigNum(obuf, 0, olen, 2);
    System.out.println(",out:" + res.toBinaryString());
    return res;
  }

  /* ****************************
   * for DEBUG
   * ****************************/

  /**
   * check.
   * @param an BigNum
   * @param bn BigNum
   * @param cn BigNum
   * @param optionS String
   * @param scale String
   * @param roundingMode RoundingMode
   * @return boolean
   */
  public static boolean check(BigNum an, BigNum bn, BigNum cn, String optionS,
      int scale, RoundingMode roundingMode) {
    boolean res = false;
    String stra = null;
    BigDecimal bda = null;
    if (an != null) {
      stra = an.toString();
      bda = new BigDecimal(stra);
    }
    String strb = null;
    BigDecimal bdb = null;
    if (bn != null) {
      strb = bn.toString();
      bdb = new BigDecimal(strb);
    }
    String strc = null;
    BigDecimal bdc = null;
    if (cn != null) {
      strc = cn.toString();
      bdc = new BigDecimal(strc);
    }
    BigDecimal bdd = null;
    if ("+".equals(optionS)) {
      bdd = bda.add(bdb);
    } else if ("-".equals(optionS)) {
      bdd = bda.subtract(bdb);
    } else if ("*".equals(optionS)) {
      bdd = bda.multiply(bdb);
    } else if ("/".equals(optionS)) {
      bdd = bda.divide(bdb, scale, roundingMode);
    }
    if (bdc.compareTo(bdd) == 0) {
      res = true;
    }
    if (!res) {
      throw new ArithmeticException("[ERROR]" + an + optionS + bn + "=" + cn
          + "=>" + bdc + "<>" + bdd + "(scale=" + scale + ",roundingMode="
          + roundingMode + ")");
    }
    return res;
  }
  /**
   * printary.
   * @param in int[]
   */
  public void printary(int[] in) {
    for (int ch: in) {
      System.out.print(ch);
    }
    System.out.println();
  }

  /**
   * printary.
   * @param in char[]
   */
  public void printary(char[] in) {
    for (char ch: in) {
      System.out.print(ch);
    }
    System.out.println();
  }

  /**
   * printary.
   * @param in long[]
   */
  public void printary(long[] in) {
    int idx = 0;

    for (idx = 0; idx < in.length; idx ++) {
      long ch = in[idx];

      System.out.print(ch + ",");
    }
    System.out.println();
  }

  /**
   * printary.
   * @param in long[][]
   */
  public void printary(long[][] in) {

    int idx = 0;
    int idy = 0;
    System.out.println("-----long[][]");
    for (idx = 0; idx < in.length; idx ++) {
      for (idy = 0; idy < in[0].length; idy ++) {
        long ch = in[idx][idy];
        System.out.print(ch + ",");
      }
      System.out.println();
    }
    System.out.println();
  }

  /**
   * toCharary.
   * @param in int[]
   * @param len int
   * @return char[]
   */
  public char[] toCharary(int[] in, int len) {
    char[] res = new char[len];
    for (int i = 0; i < len; i ++) {
      res[i] = (char) ('0' + in[i]);
    }
    return res;
  }

  /**
   * test_cmp_ary.
   */
  public void test_cmp_ary() {
    int[] an = { 2, 4};
    int[] bn = { 0, 3, 0};
    System.out.println(cmp_ary(an, 2, bn));
    an = new int[] {1, 5, 0};
    bn = new int[] {1, 8, 0, 0};
    System.out.println(cmp_ary(an, 3, bn));
  }

  /**
   * test_add_ary.
   */
  public void test_add_ary() {
	  int[] an = { 9, 9, 9};
	  int[] bn = add_ary(an, 1, (int) 0x01);
    System.out.println("datas=" + String.valueOf(toCharary(bn, bn.length)));
  }
}
