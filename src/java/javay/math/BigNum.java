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
  private byte signed;
  /** 数据 十进制 一位对应一位. */
  private byte[] datas;
  /** 数据的长度. */
  private int length;
  /** 小数点的起始位置. */
  private int scale;

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
   * @param str 字符串
   */
  public BigNum(String str) {
    this(str.toCharArray(), 0 , str.toCharArray().length, 10);
//    System.out.println("★String:" + str);
  }

  public BigNum(String str, int numberSystem) {
    this(str.toCharArray(), 0 , str.toCharArray().length, numberSystem);
  }

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
    byte[] dats = new byte[len];
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
        dats[idy] = (byte) (in[idx] - '0');
      } else if (in[idx] >= 'A' && in[idx] <= 'F') {
        dats[idy] = (byte) (in[idx] - 'A' + 10);
      } else {
        throw new NumberFormatException();
      }
      idy ++;
    }
//    System.out.println("aaa scale=" + this.scale);

    this.length = idy;
    if (this.scale <= 0 || this.scale == this.length) {
      // 整数a.的时候，补成a.0的形式。
      this.scale = this.length;
      this.length ++;
      this.datas = new byte[this.length];
      int size = this.length;
      if (size > dats.length) {
        size = dats.length;
      }
      this.datas[this.datas.length - 1] = 0;
      System.arraycopy(dats, 0, this.datas, 0, size);
      dats = null;
    } else {
      this.datas = new byte[idy];
      System.arraycopy(dats, 0, this.datas, 0, idy);
      dats = null;
    }

    this.datas = this.removeLastZero(this.datas, this.scale);
    this.length = this.datas.length;
    /* DEBUG:print */
//    System.out.println("datas=" + String.valueOf(toCharary(datas, datas.length)));
    if (numberSystem != 10) {
      BigNum res = this.createNum(0);
      BigNum ns   = this.createNum(numberSystem);
//      System.out.println("长度=" + this.length + ",整数长度=" + this.scale + "小数长度=" +
      //(this.length - this.scale) + ",ns=" + ns);
      int indx = 0;
      for (int i = this.scale - 1; i >= 0; i --, indx ++) {
        res = res.add(ns.pow(i).multiply(this.createNum(this.datas[indx])));
      }
//      System.out.println("整数=" + res);
      for (int i = 1; i <= (this.length - this.scale); i ++, indx ++) {
        res = res.add(
            this.createNum(this.datas[indx]).divide(ns.pow(i), 40, BigNumRound.HALF_EVENT));
      }
//      System.out.println("整数=" + res);
      this.datas = res.datas;
      this.length = res.length;
      this.scale = res.scale;
    }
  }

  private BigNum createNum(int in) {
    byte signed = 0x01;
    int an = in;
    if (in < 0) {
      signed = -0x01;
      an = -in;
    }
    int lengt = 1;
    int scal = 1;
    byte[] datas = null;
    if (an < 10) {
      datas = new byte[1];
      datas[0] = (byte) in;
    } else if (an < 100) {
      lengt = 2;
      scal = 2;
      datas = new byte[2];
      if (an < 20) {
        datas[0] = (byte) 1;
        datas[1] = (byte) (an - 10);
      }
    }
    return new BigNum(signed, datas, lengt, scal);
  }
  /**
   * 构造函数.
   * @param si 符号
   * @param da 数据
   * @param len 长度
   * @param sca 小数点位置
   */
  public BigNum(byte si, byte[] da, int len, int sca) {
    this.signed = si;
    byte[] buf = this.removeLastZero(da, sca);
    this.datas = buf;
    this.length = buf.length;
    this.scale = sca;
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
//    System.out.println("加法●●●●" + this + "＋" + augend +"等于");
    if (augend.isZero()) {
      // a + 0 = a
      return this;
    }
    if (this.isZero()) {
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
        decS = augend.length - augend.scale;
      }
      /* 长度 */
      int lengthS = 2 + scaleS + decS;
      byte[] dataS = new byte[lengthS];

//      System.out.println("加法scaleS=" + scaleS + ",decS=" + decS + ",lengthS=" + lengthS);
      long a = 0;
      /* 小数部 */
      for(int idx = decS; idx > 0; idx --) {
//        System.out.println("a1=" + a);
        if (0<= (this.scale + idx) && (this.scale + idx) < this.length) {
//          System.out.println("a=" + this.datas[this.scale + idx]);
          a = a + this.datas[this.scale + idx];
        }
        if (0 <= (augend.scale + idx) && (augend.scale + idx) < augend.length) {
//          System.out.println("a=" + augend.datas[augend.scale + idx]);
          a = a + augend.datas[augend.scale + idx];
        }
//        System.out.println("a=" + a);
        dataS[1 + scaleS + idx] = (byte) (0xFF & (a % 10));
        a = a / 10;
//        System.out.println("加法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));
      }
      /* 整数部 */
      for (int idx = 0; idx <= scaleS; idx ++) {
//        System.out.println("a2=" + a);
        if (0 <= (this.scale - idx) && (this.scale - idx) < this.datas.length) {
          a = a + this.datas[this.scale - idx];
//          System.out.println("a=" + this.datas[this.scale - idx]);
        }
        if (0 <= (augend.scale - idx)  && (augend.scale - idx) < augend.datas.length) {
//          System.out.println("a=" + augend.datas[augend.scale - idx]);
          a = a + augend.datas[augend.scale - idx];
        }
//        System.out.println("a=" + a);
        if ((1 + scaleS - idx) < dataS.length) {
          dataS[1 + scaleS - idx] = (byte) (0xFF & (a % 10));
        }
        a = a / 10;
//        System.out.println("加法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));
      }
//      System.out.println("a3=" + a);
      dataS[0] = (byte) (0xFF & a);
//      System.out.println("加法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));

      scaleS ++;
      byte[] dataS1 = removeFirstZero(dataS, scaleS);

      BigNum res = new BigNum(this.signed, dataS1, dataS1.length, dataS1.length - dataS.length + scaleS);
//      System.out.println(res);
      check(this, augend, res, "+", 0, RoundingMode.UNNECESSARY);
//      double chksum = this.toDouble(CalcultorConts.MAX_DOUBLE_SCALE) + augend.toDouble(CalcultorConts.MAX_DOUBLE_SCALE);
//      if (chksum != res.toDouble(CalcultorConts.MAX_DOUBLE_SCALE)) {
//        throw new ArithmeticException("[ERROR]" + this + "+" + augend + "=" + res + "=>" + res.toDouble(CalcultorConts.MAX_DOUBLE_SCALE) + "<>" + chksum);
//      }
      return res;
    } else {
      if (this.signed < 0) {
//        System.out.println("-----");
        return augend.subtract(new BigNum((byte)(0x00-this.signed), this.datas, this.length, this.scale));
      } else {
        return this.subtract(new BigNum((byte)(0x00-augend.signed), augend.datas, augend.length, augend.scale));
      }
    }
  }

  /**
   *
   * @param subtrahend
   * @return
   */
  public BigNum subtract(BigNum subtrahendi) {
//    System.out.println("减法●●●●" + this + "－" + subtrahendi +"等于");
    if (subtrahendi.isZero()) {
      return this;
    }
    if (this.isZero()) {
      return new BigNum((byte)(0x00-subtrahendi.signed), subtrahendi.datas, subtrahendi.length, subtrahendi.scale);
    }
    if (this.signed == subtrahendi.signed) {
      byte signeds = 0x01;
      // 大小调整
      BigNum minuend = this;
      BigNum subtrahend = subtrahendi;
      if (minuend.abs().compareTo(subtrahendi.abs()) < 0) {
//        System.out.println("减法CHG:" + minuend.abs() + " vs " + subtrahend.abs() + "=");
        minuend = subtrahendi;
        subtrahend = this;
        signeds = -1;
      }

//      System.out.println(minuend + " - " + subtrahend + "=");
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
      byte[] dataS = new byte[lengthS];
      byte[] carryS = new byte[lengthS];

//      System.out.println("减法scaleS=" + scaleS + ",decS=" + decS + ",lengthS=" + lengthS);
      long a = 0;
      byte carry = 0;
      /* 小数部 */
      for(int idx = decS; idx > 0; idx --) {
//        System.out.println("a1=" + a);
        if (0 <= (minuend.scale + idx) && (minuend.scale + idx) < minuend.length) {
//          System.out.println("a=" + minuend.datas[minuend.scale + idx]);
          a = a + minuend.datas[minuend.scale + idx];
        }
        if (0 <= (subtrahend.scale + idx) && (subtrahend.scale + idx) < subtrahend.length) {
//          System.out.println("a=" + subtrahend.datas[subtrahend.scale + idx]);
          a = a - subtrahend.datas[subtrahend.scale + idx];
        }
//        System.out.println("a=" + a);
        if (a < 0) {
          a = 10 + a;
          carry = -1;
        }
        dataS[1 + scaleS + idx] = (byte) (0xFF & (a % 10));
        carryS[1 + scaleS + idx] = carry;
        a = a / 10;
        a = a + carry;
        carry = 0;
//        System.out.println("减法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));
      }
      /* 整数部 */
      for (int idx = 0; idx <= scaleS; idx ++) {
//        System.out.println("整数部a2=" + a);
        if ((minuend.scale - idx) >= 0 && (minuend.scale - idx) < minuend.datas.length) {
//          System.out.println("整数部("+ (minuend.scale - idx) +")=" + minuend.datas[minuend.scale - idx]);
          a = a + minuend.datas[minuend.scale - idx];
        }
        if ((subtrahend.scale - idx) >= 0 && (subtrahend.scale - idx) < subtrahend.datas.length) {
//          System.out.println("整数部a=" + subtrahend.datas[subtrahend.scale - idx]);
          a = a - subtrahend.datas[subtrahend.scale - idx];
        }
//        System.out.println("整数部a=" + a);
        if (a < 0) {
          a = 10 + a;
          carry = -1;
        }
//        System.out.println("整数部(1 + scaleS - idx)=" + (1 + scaleS - idx) +",dataS.length=" + dataS.length + ",a=" + a + ",carry=" + carry);
        if ((1 + scaleS - idx) < dataS.length) {
          dataS[1 + scaleS - idx] = (byte) (0xFF & (a % 10));
          carryS[1 + scaleS - idx] = carry;
        }
        a = a / 10;
        a = a + carry;
        carry = 0;
//        System.out.println("减法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));
      }

//      System.out.println("a3=" + a);
      if (a < 0) {
        a = 10 + a;
        carry = -1;
      }
      dataS[0] = (byte) (0xFF & a);
      carryS[0] = carry;
//      printary(carryS);
//      System.out.println("减法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));
//      System.out.println("减法carryS=" + String.valueOf(toCharary(carryS, carryS.length)));


//      if (carry == -1) {
//        signeds = -1;
//      }

      scaleS += 1;
      byte[] dataS1 = removeFirstZero(dataS, scaleS);

      BigNum res = new BigNum(signeds, dataS1, dataS1.length, dataS1.length - dataS.length + scaleS);
//      System.out.println(res);
      check(this, subtrahendi, res, "-", 0, RoundingMode.UNNECESSARY);
//      double chksum = this.toDouble(CalcultorConts.MAX_DOUBLE_SCALE) - subtrahend.toDouble(CalcultorConts.MAX_DOUBLE_SCALE);
//      if (chksum != res.toDouble(CalcultorConts.MAX_DOUBLE_SCALE)) {
//        throw new ArithmeticException("[ERROR]" + this + "-" + subtrahend + "=" + res + "=>" + res.toDouble(CalcultorConts.MAX_DOUBLE_SCALE) + "<>" + chksum);
//      }
      return res;
    } else {
      return this.add(new BigNum((byte)(0x00-subtrahendi.signed), subtrahendi.datas, subtrahendi.length, subtrahendi.scale));
    }
  }

  /**
   *
   * @param multiplicand
   * @return
   */
  public BigNum multiply(BigNum multiplicand) {
//    System.out.println("乘法●●●●" + this + "×" + multiplicand +"等于");
    if (multiplicand.isZero()) {
      return multiplicand;
    }
    if (multiplicand.equals(BigNum.ONE)) {
      return this;
    }
    if (this.isZero()) {
      return this;
    }
    if (this.equals(BigNum.ONE)) {
      return multiplicand;
    }
    /* 符号 */
    byte signed1 = this.signed;
    byte signed2 = multiplicand.signed;
    byte signed = (byte) (signed1 * signed2);

    /* 长度 */
    int len1 = this.length;
    int len2 = multiplicand.length;
    int len = len1 + len2 + 1;

    /* 小数点位置 */
    int scale1 = this.scale;
    int scale2 = multiplicand.scale;
    // 小数部长度
    int decimal_len = len - ((len1 - scale1) + (len2 - scale2));
//    System.out.println("乘法小数部长度 =" + decimal_len + "scale1=" + scale1 + "scale2" + scale2);

    /* 数据 */
    long[][] data = new long[len][len];
    int x = 0, y = 0;
    for (int idx = multiplicand.length - 1; idx >= 0; idx --) {
      for (int idy = this.length - 1; idy >= 0; idy --) {
        data[x][y] = multiplicand.datas[idx] * this.datas[idy];
        y ++;
      }
      x ++;
      y = x;
    }
//    printary(data);

    long[] dat = new long[len];
    long carry = 0;
    for (int n = 0; n < data[0].length; n ++) {
      long value = carry;
      carry = 0;
      for(int m = 0; m < data.length; m ++) {
        value = value + data[m][n];
        if (value >= 10) {
          carry += value / 10;
          value %= 10;
        }
      }
      dat[n] = value;
    }
//    printary(dat);

    byte[] result = new byte[len];
    if (carry != 0) {
      // carry是最高位。
      len ++;
      result = new byte[len];
    }
    long carry2 = 0;
    int j = len - 1;
    for (int i = 0; i < dat.length; i ++) {
      if ((dat[i] + carry2) >= 10) {
        result[j - i] = (byte) (( dat[i] + carry2 ) % 10);
        carry2 = dat[i] / 10;
      } else {
        result[j - i] = (byte) (dat[i] + carry2);
      }
    }
    if (j - dat.length >= 0) {
      if ((carry + carry2)  >= 10) {
        result[j - dat.length] = (byte) ((carry + carry2 ) % 10);
        carry2 = (carry + carry2) / 10;
        if (carry2 != 0) {
          // ERROR
        }
      } else {
        result[j - dat.length] = (byte) (carry + carry2);
      }
    } else {
      // ERROR
    }

    // remove zero;
    byte[] result1 = removeFirstZero(result, decimal_len);

    BigNum res = new BigNum(signed, result1, result1.length, result1.length - result.length + decimal_len);
//    double dres = res.toDouble(13);
//    System.out.println(res);
    check(this, multiplicand, res, "*", 0, RoundingMode.UNNECESSARY);
//    double t1 = this.toDouble(14);
//    double t2 = multiplicand.toDouble(14);
//    double chksum = t1 * t2;
//    if (dres != chksum) {
//      throw new ArithmeticException("[ERROR]" + this + "*" + multiplicand + "=" + res + "=>" + dres + "<>" + chksum + "=" + t1 + "*" + t2);
//    }
    return res;
  }

  /**
   * 除法
   * @param divisor
   * @param decimal_len
   * @param roundmode
   * @return
   */
  public BigNum divide(BigNum divisor, int decimal_len, BigNumRound roundmode) {
//    System.out.println("除法●●●●" + this + "÷" + divisor +"等于");
    if (divisor.isZero()) {
      // 除数为零时
      throw new ArithmeticException("Division by zero");
    }
    if (divisor.equals(BigNum.ONE)) {
      return this;
    }
    if (this.isZero()) {
      return this;
    }

    // 符号
    byte osigned = 0x01;
    if (this.signed != divisor.signed) {
      osigned = -1;
    }

    // 小数点位置
    int dscale  = divisor.scale;
    int tscale  = this.scale;

    // 被除数同步
    tscale += divisor.length - dscale;
//    System.out.println("除法调整后被除数小数点位置:" + tscale + "/除数" + dscale + "=>" + divisor.length + "被除数整数部长度" + tscale + "vs除数长度" + divisor.length + "小于时需移位,大于等于时则开始量取");

    // 最大精度，小数部长度
    int max_decimal_len = decimal_len;
    if (BigNumRound.HALF_EVENT.equals(roundmode)) {
      max_decimal_len ++;
    }

    int dlen  = divisor.length;
    byte[] tmp_divi = this.removeFirstZero(divisor.datas, divisor.datas.length);
    dlen = tmp_divi.length;

    int ido = 0;
    int oscale = 0; // 小数点位置
    int odecimal_cnt = -1; // 小数位数
    // 假定商的位数＝被除数的整数部－除数的长度（已无小数）＋1（至少是个数所以+1）
    int olen = tscale - dlen + 1;
    if (olen <= 0) {
      olen = 2 - olen; // 0.0
      oscale = 1;
      odecimal_cnt = olen - 1;
      ido = odecimal_cnt;
    }
//    System.out.println("除法●●●●️长度" + olen + ",oscale" + oscale + ",odecimal_cnt=" + odecimal_cnt + ",ido=" + ido);
    byte[] out = new byte[olen];

    int idx = 0;
    int idx_next = 0;
    byte[] tmp = new byte[dlen];
    int len_tmp = tmp.length;
    if (this.datas.length < tmp.length) {
      // 不足位零补齐
      System.arraycopy(this.datas, idx, tmp, idx, this.datas.length);
    } else {
      System.arraycopy(this.datas, idx, tmp, idx, len_tmp);
    }

//    System.out.println("除法tmp=" + String.valueOf(toCharary(tmp, tmp.length)) + "/" + String.valueOf(toCharary(tmp_divi, tmp_divi.length)));
    idx_next = len_tmp;

    while(true) {
      int c = cmp_ary(tmp, len_tmp, tmp_divi);
      if (c >= 0) {
        out[ido] = (byte) (out[ido] + 1);
        // shift postition
        tmp = sub_ary(tmp, len_tmp, tmp_divi);
//        System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
//        System.out.println("out["+ ido + "]=" + out[ido]);
      }
      if (c < 0) {
        byte[] temp;
        if (tmp[0] == 0) {
          temp = new byte[tmp.length];
          System.arraycopy(tmp, 1, temp, 0, tmp.length - 1);
        } else {
          temp = new byte[len_tmp + 1];
          System.arraycopy(tmp, 0, temp, 0, len_tmp);
        }
//        System.out.println("temp=" + String.valueOf(toCharary(temp, temp.length)));

        if (odecimal_cnt >= 0) {
          odecimal_cnt ++;
        }
        idx = idx_next;
//        System.out.println("input pos=" + idx + "tscale=" + tscale + "ido=" + ido);
        if (idx == tscale) {
          // 小数点位置
          oscale = ido + 1;
          odecimal_cnt = 0;
//          System.out.println("除法★小数点位置oscale=" + oscale );
        }
        if (idx < this.datas.length) {
          System.arraycopy(this.datas, idx, temp, temp.length - 1, 1);
        } else {
          if(BigNumRound.HALF_EVENT.equals(roundmode)) {
            // 银行家算法
            // ==5, after is zero?
            if (0 <= (oscale + decimal_len) && (oscale + decimal_len) < ido) {
              if (out[oscale + decimal_len] == 5) {
                if (out[ido] != 0) {
                  break;
                }
                //if (odecimal_len > max_decimal_len + 10) {
                if (odecimal_cnt > (max_decimal_len + 10)) {
                  break;
                }
              }
            }
          }
          // 向小数部延长
//          System.out.println("olen=" + olen + ",odecimal_cnt=" + odecimal_cnt + "vs" + max_decimal_len);
          if (odecimal_cnt > max_decimal_len) {
            // 超过指定长度结束。
            // banker
            break;
          }
        }
        // 向右移位
        olen ++;
        byte[] out2 = new byte[olen];
        System.arraycopy(out, 0, out2, 0, out.length);
        out = out2;

        idx_next ++;
        ido ++;

        tmp = temp;
        len_tmp = tmp.length;
//        System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
      }
//      System.out.println("除法out=" + String.valueOf(toCharary(out, out.length)));
    }

//    System.out.println("除法apos=" + (oscale + decimal_len - 1) + ",val=" + out[(oscale + decimal_len - 1)]);
    RoundingMode rm = RoundingMode.UNNECESSARY;
    if (BigNumRound.UP.equals(roundmode)) {
      rm = RoundingMode.UP;
      // 远离零方向舍入,> 0 进上
      if (out[(oscale + decimal_len - 1)] != 0) {
        out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
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
        if (out[(oscale + decimal_len - 1)] != 0) {
          out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
        }
      }
    }
    if (BigNumRound.FLOOR.equals(roundmode)) {
      rm = RoundingMode.FLOOR;
      // 向负无穷方向舍入,
      if (osigned < 0) {
        if (out[(oscale + decimal_len - 1)] != 0) {
          out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
        }
      }
    }
    if (BigNumRound.HALF_UP.equals(roundmode)) {
      rm = RoundingMode.HALF_UP;
      // 最近数字舍入(5进)。这是我们最经典的四舍五入。
      if (out[(oscale + decimal_len - 1)] > 4) {
        out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
      }
    }
    if (BigNumRound.HALF_DOWN.equals(roundmode)) {
      rm = RoundingMode.HALF_DOWN;
      // 最近数字舍入(5舍)。在这里5是要舍弃的。五舍六入。
      if (out[(oscale + decimal_len - 1)] > 5) {
        out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
      }
    }
    if (BigNumRound.HALF_EVENT.equals(roundmode)) {
      rm = RoundingMode.HALF_EVEN;
      // 银行家舍入法。
//      System.out.println("除法pos=" + (oscale + decimal_len) + ",val=" + out[(oscale + decimal_len)]);
      if (5 < out[(oscale + decimal_len)]) {
        // （2）如果保留位数的后一位如果是6，则进上去。例如5.216保留两位小数为5.22。
        out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
      }
      if (5 == out[(oscale + decimal_len)]) {
        if (is_zero_ary(out, (oscale + decimal_len + 1)) == false) {
          // （4）如果保留位数的后一位如果是5，而且5后面仍有数。例如5.2254保留两位小数为5.23，也就是说如果5后面还有数据，则无论奇偶都要进入。
          out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
        } else {
          if (out[(oscale + decimal_len - 1)] % 2 != 0) {
            // （3）如果保留位数的后一位如果是5，而且5后面不再有数，要根据应看尾数“5”的前一位决定是舍去还是进入: 如果是奇数则进入，如果是偶数则舍去。
            out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
          }
        }
      }
      // （1）要求保留位数的后一位如果是4，则舍去。例如5.214保留两位小数为5.21。
    }
    byte[] out3 = new byte[(oscale + decimal_len)];
    System.arraycopy(out, 0, out3, 0, out3.length);
//    System.out.println("除法out1=" + String.valueOf(toCharary(out3, out3.length)) + "小数点位置=" + oscale);
    byte[] out2 = removeFirstZero(out3, oscale);
    oscale = oscale - out3.length + out2.length;
//    System.out.println("除法out2=" + String.valueOf(toCharary(out2, out2.length)) + "小数点位置=" + oscale);
//    System.out.println(this.toString() + "/" + divisor.toString() + "=");

    BigNum res = new BigNum(osigned, out2, out2.length, oscale);
//    System.out.println(res);
    check(this, divisor, res, "/", decimal_len, rm);
    double dres = res.toDouble(16);
    double t1 = this.toDouble(14);
    double t2 = divisor.toDouble(14);
    double chksum = t1 / t2;
    if (dres != chksum) {
      throw new ArithmeticException("[ERROR]" + this + "/" + divisor + "=" + res + "=>" + dres + "<>" + chksum + "=" + t1 + "/" + t2);
    }
    return res;
  }

  /**
   *
   * @param a
   * @param lena
   * @param b
   * @return 1: a > b, 0: a = b, -1:a < b
   */
  protected int cmp_ary(byte[] a, int lena, byte[] b) {
    int c = 0;
    int offset = 0;
    while (lena > (b.length + offset)) {
      if (a[offset] > 0) {
        c = 1;
        break;
      }
      offset ++;
    }
    if (c != 0) {
//      System.out.println("a:" + String.valueOf(toCharary(a, lena)) + "_vs_" + String.valueOf(toCharary(b, b.length)) + "=" + c + ",offset=" + offset);
      return c;
    }

    int offsetb = 0;
    while(b.length > (lena + offsetb)) {
      if (b[offsetb] > 0) {
        c = -1;
        break;
      }
      offsetb ++;
    }
    if (c != 0) {
//      System.out.println("c:" + String.valueOf(toCharary(a, lena)) + "_vs_" + String.valueOf(toCharary(b, b.length)) + "=" + c + ",offset=" + offset);
      return c;
    }
    for (int idx = 0; idx < b.length; idx ++) {
      if (a[idx + offset] > b[idx + offsetb]) {
        c = 1;
        break;
      }
      if (a[idx + offset] < b[idx + offsetb]) {
        c = -1;
        break;
      }
    }

//    System.out.println("b:" + String.valueOf(toCharary(a, lena)) + "_vs_" + String.valueOf(toCharary(b, b.length)) + "=" + c);
    return c;
  }
  /**
   *
   * @param a
   * @param b
   * @return a - b
   */
  protected byte[] sub_ary(byte[] a, int lena, byte[] b) {
    byte[] c2 = new byte[lena];
    long m = 0;
    long carry = 0;
    int posb, posa;
    for (posb = b.length - 1, posa = lena - 1; posb >= 0 && posa >= 0; posb --, posa --) {
      m = (a[posa] - b[posb]) + carry;
      carry = 0;
      if (m < 0) {
        carry --;
        m = 10 + m;
      }
      c2[posa] = (byte) m;
//      System.out.println("□" + a[posa] + "-" + b[posb] + "=" + c2[posa]);
    }
    while (carry == 0 && posa >= 0 && a[posa] != 0) {
      c2[posa] = a[posa];
      posa --;
    }
    while (posa >= 0 && carry < 0) {
      c2[posa] = (byte) (a[posa] + carry);
//      System.out.println("□" + a[posa] + "-" + carry + "=" + c2[posa]);
      carry = 0;
      posa --;
    }

    return c2;
  }

  public boolean isZero() {
    boolean result = true;
    for (byte b : this.datas) {
      if (b != 0x00) {
        result = false;
        break ;
      }
    }
    return result;
  }

  /**
   *
   * @param in 数据
   * @param dotpos 整数部的长度
   * @return 格式化后的数据
   */
  protected byte[] removeFirstZero(byte[] in, int dotpos) {
    int decimal_len = in.length - dotpos;
//    System.out.println("@removeFirstZero:in的长度=" + in.length + ",整数部的长度=" + dotpos + ",小数部长度=" + decimal_len + ",in:" + String.valueOf(toCharary(in, in.length)));
    int i = 0;
    boolean bFlag = false;
    for(i = 0; 0 < dotpos && i < dotpos; i ++) {
//      System.out.println("i=" + i + "in[i]=" + in[i]);
      bFlag = true;
      if (in[i] != 0) {
        i ++;
        break;
      }
    }

//    System.out.println("@removeFirstZero:i=" + i + ",bFlag=" + bFlag);
    if (bFlag && i == 1 && (i + decimal_len == in.length)) {
      // 0.* 去除个数+小数部长度=数据长度的话，不需要格式化
//      System.out.println("@removeFirstZero:return by equal");
      return in;
    }

    byte[] res;
    if (dotpos > 1 && i == dotpos) {
      // 整数部为零
      res = new byte[decimal_len + 1];
      //i --;
    } else {
      if (bFlag) {
        res = new byte[decimal_len + dotpos - i + 1];
        //i --;
      } else {
        res = new byte[decimal_len + dotpos + 1];
        i = 0;
      }
    }

//    if (bFlag == false) {
//      i = -1;
//    }
    i -= 2;
    if (i < -1) {
      i = -1;
    }
//    System.out.println("@removeFirstZero:res.length=" + res.length + ",in.length=" + in.length + ",i=" + i);
    if (res.length <= in.length) {
      System.arraycopy(in, i + 1, res, 0, res.length);
    } else {
      System.arraycopy(in, i + 1, res, res.length - in.length, in.length);
    }
    return res;
  }

  protected byte[] removeLastZero(byte[] in, int dotpos) {
    int pos = in.length - 1;
    for (;pos > (dotpos - 1); pos --) {
      if (in[pos] != 0) {
        break;
      }
    }
    pos ++;
    byte[] res = new byte[pos];
    System.arraycopy(in, 0, res, 0, pos);
    return res;
  }

  /**
   *
   * @param divisor
   * @return
   */
  public BigNum mod(BigNum divisor) {
//    System.out.println("this=" + String.valueOf(toCharary(this.datas, this.datas.length)));
//    System.out.println("divisor=" + String.valueOf(toCharary(divisor.datas, divisor.datas.length)));
    if (divisor.isZero()) {
      // 除数为零时
      throw new ArithmeticException("Division by zero");
    }
    if (divisor.equals(BigNum.ONE)) {
      return this;
    }
    if (this.isZero()) {
      return this;
    }

    // 符号
    byte osigned = 0x01;
    if (this.signed != divisor.signed) {
      osigned = -1;
    }

    // 位数
    int dlen = divisor.length;
    int tlen = this.length;
    int olen = tlen - dlen + 1;
//    System.out.println("长度:=" + olen + ",tlen=" + tlen + ",dlen=" + dlen);

    // 小数点位置
    int dscale = divisor.scale;
    int tscale = this.scale;
    // 被除数同步
    int off = dlen - dscale;
    tscale += off;
//    System.out.println("小数点对齐长度：" + off);

    // 小数部长度
    int odecimal_len = tlen - tscale;
    if (tlen < dlen) {
      odecimal_len += dlen - tlen;
    }
//    System.out.println("小数点长度:" + odecimal_len + "=" + tscale + "/" + dscale + ",tlen=" + tlen + ",dlen=" + dlen);

    int idx = 0, idx_next = 0;
    byte[] tmp = new byte[dlen];
    int len_tmp = tmp.length;
    System.arraycopy(this.datas, idx, tmp, idx, len_tmp);
//    System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
    idx_next = len_tmp;

    byte[] out = new byte[olen];
    int ido = 0;
    while(true) {
      int c = cmp_ary(tmp, len_tmp, divisor.datas);
      if (c >= 0) {
        out[ido] = (byte) (out[ido] + 1);
        // shift postition
        tmp = sub_ary(tmp, len_tmp, divisor.datas);
//        System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
//        System.out.println("out["+ ido + "]=" + out[ido]);
      }
      if (c < 0) {
        byte[] temp;
        if (tmp[0] == 0) {
          temp = new byte[tmp.length];
          System.arraycopy(tmp, 1, temp, 0, tmp.length - 1);
        } else {
          temp = new byte[tmp.length + 1];
          System.arraycopy(tmp, 0, temp, 0, tmp.length);
        }
//        System.out.println("temp=" + String.valueOf(toCharary(temp, temp.length)));

        idx = idx_next;
        if (idx < this.datas.length) {
          System.arraycopy(this.datas, idx, temp, temp.length - 1, 1);
        } else {
          // 超过指定长度结束。
          System.out.println("WARNINGGGGGGG");
          out = tmp;
          break;
        }
        idx_next ++;
        ido ++;
        if (ido >= olen) {
          // end
          //tmp
          // 小数点
          int oscale = olen - odecimal_len;
          int lead = 0;
          if ((oscale - off) <= 0) {
            lead = 1 - oscale + off;
//            System.out.println("lead=" + lead + ",oscale=" + oscale + ",off=" + off);
          }

          // 数值
          int lent = tmp.length;
          int leny = this.datas.length - 1 - idx;
          if (lent + leny +lead == 0) {
            out = new byte[1];
            out[0] = 0;
          } else {
            out = new byte[lent + leny + lead];
            for(int i = 0; i < lead; i ++) {
              out[i] = 0;
            }
          }
          if (lent > 0) {
            System.arraycopy(tmp, 0, out, lead, lent);
          }
          if (leny > 0) {
            System.arraycopy(this.datas, idx, out, lead + lent, leny);
          }
//          System.out.println("结果长度：" + out.length + ",lent=" + lent + ",leny=" + leny);
          break;
        }

        tmp = temp;
        len_tmp = tmp.length;
//        System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
      }
    }
//    System.out.println("out1=" + String.valueOf(toCharary(out, out.length)));
//    out = removeFirstZero(out, olen - odecimal_len);
//    System.out.println("out2=" + String.valueOf(toCharary(out, out.length)));
//    System.out.println(this.toString() + "/" + divisor.toString() + "=");
    return new BigNum(osigned, out, out.length, out.length - odecimal_len - off);
  }

  /**
   *
   * @param o
   * @return
   */
  public BigNum pow(long n) {
    if (n < 0) {
      return new BigNum("1.0").divide(pow(new BigNum(0-n)), 40, BigNumRound.HALF_EVENT);
    }
    BigNum result = new BigNum("1");
    if (n == 0) {
      return result;
    }
    long idx = 0;
    while(idx < n) {
      idx ++;
      result = result.multiply(this);
    }
    return result;
  }

  /**
   *
   * @param o
   * @return
   */
  public BigNum pow(BigNum n) {
    if (n.compareTo(BigNum.ZERO) < 0) {
      return new BigNum("1.0").divide(pow(n.negate()), 40, BigNumRound.HALF_EVENT);
    }
    BigNum result = new BigNum("1");
    if (n.isZero()) {
      return result;
    }
    BigNum idx = new BigNum("0");
    while(idx.compareTo(n) < 0) {
      idx = idx.add(BigNum.ONE);
      result = result.multiply(this);
    }
    return result;
  }

  /**
   * 阶乘
   * 一个正整数的阶乘/层（英语：factorial）是所有小于及等于该数的正整数的积，并且有0的阶乘为1。自然数n的阶乘写作n!。1808年，基斯顿·卡曼引进这个表示法。
   * 素数阶乘是所有小于或等于该数且大于或等于2的素数的积，自然数n的素数阶乘，写作n#。
   * @return
   */
  public BigNum factorial() {
    if(this.compareTo(BigNum.ZERO) < 0) {
      return this;
    }
    if(this.compareTo(BigNum.ZERO) == 0) {
      return BigNum.ONE;
    }

    BigNum result = new BigNum(this);
    BigNum next = this.subtract(BigNum.ONE);
    next = next.integral();
    while(next.compareTo(BigNum.ZERO) > 0) {
      result = result.multiply(next);
      next = next.subtract(BigNum.ONE);
    }
    return result;
  }
  /**
   * 比较大小
   */
  public int compareTo(BigNum o) {
//    System.out.print(this + " vs " + o + " = ");
    int result = 0;
    if (this.signed > o.signed) {
//      System.out.println(1);
      return 1;
    }
    if (this.signed < o.signed) {
//      System.out.println(-1);
      return -1;
    }
    int max = this.scale;
    int pre = max - o.scale;
    boolean bReadThis = true;
    if (max < o.scale) {
      max = o.scale;
      pre = max - this.scale;
      bReadThis = false;
    }
    int a , b;
    if (pre > 0) {
      if (bReadThis) {
        for (a = 0; a < pre; a ++) {
          if (this.datas[a] > 0) {
            result = 1;
            break;
          }
        }
      } else {
        for (a = 0; a < pre; a ++) {
          if (o.datas[a] > 0) {
            result = -1;
            break;
          }
        }
      }
    }
    if (result == 0) {
      // 整数部
      // for (a = this.scale - 1, b = o.scale - 1; a >= 0 && b >= 0; a --, b --) {
      for (a = 0, b = 0; a < this.scale && b < o.scale; a ++, b ++) {
        result = this.datas[a] - o.datas[b];
        if( result != 0 ) {
          break;
        }
      }
//      System.out.println("比较result1=" + result);
//      if (result == 0) {
//        if (a >= 0) {
//          result = 1;
//        } else if (b >= 0) {
//          result = -1;
//        }
//      }
    }
//    System.out.println("比较result2=" + result);
    if (result == 0) {
      // 小数部
      for (a = this.scale, b = o.scale; 0 <= a && a < this.length && 0<= b && b < o.length; a ++, b ++) {
        result = this.datas[a] - o.datas[b];
        if( result != 0 ) {
          break;
        }
      }
//      System.out.println("比较result3=" + result);
      if (result == 0) {
        if (a < this.length) {
          result = 1;
        } else if (b < o.length) {
          result = -1;
        }
      }
    }
//    System.out.println("比较result4=" + result);
//    System.out.println(this.signed * result + "(" + this.signed + result + ")");
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
   *
   * @param o
   * @return
   */
  public BigNum max(BigNum o) {
    return (this.compareTo(o) >= 0 ? this : o);
  }
  /**
   *
   * @param o
   * @return
   */
  public BigNum min(BigNum o) {
    return (this.compareTo(o) <= 0 ? this : o);
  }
  /**
   *
   * @return
   */
  public BigNum negate() {
    return new BigNum((byte) (0x00 - this.signed), this.datas, this.length, this.scale);
  }
  /**
   *
   * @return
   */
  public BigNum plus() {
    return this;
  }
  /**
   *
   * @return
   */
  public BigNum abs() {
    return (this.signed  < 0 ? negate() : this);
  }

  /**
   * 取整
   * @return
   */
  public BigNum integral() {
    byte[] data = new byte[this.scale];
    System.arraycopy(this.datas, 0, data, 0, this.scale);
    return new BigNum(this.signed, data, this.scale, this.scale);
  }

  public String toBinaryString() {
    StringBuffer buf = new StringBuffer();
    if (this.signed > 0) {
      buf.append("+");
    } else {
      buf.append("-");
    }
    BigNum n2 = new BigNum("2.0");
    BigNum z = this.integral();
    BigNum x = this.subtract(z);
    Stack<Integer> stk = new Stack<Integer>();
    if (z.compareTo(BigNum.ZERO) == 0) {
      stk.push(0);
    }
    while(z.compareTo(BigNum.ZERO) > 0) {
      BigNum s = z.divide(n2, 0, BigNumRound.DOWN);
      BigNum y = z.subtract(s.multiply(n2));
//      System.out.println(z + "/2=" + s + ",y=" + y);
      stk.push(y.toInt());
      z = s;
    }
    while(!stk.isEmpty()) {
      buf.append(stk.pop());
    }
    if (x.compareTo(BigNum.ZERO) != 0) {
      buf.append(".");
    }
    int cnt = 0;
    while(cnt < 40 && x.compareTo(BigNum.ZERO) != 0) {
      BigNum j = x.multiply(n2);
      BigNum jz = j.integral();
      buf.append(jz.toInt());

      BigNum y = j.subtract(jz);
      x = y;
      cnt ++;
    }
//    System.out.println(buf.toString());
    return buf.toString();
  }
  public String toOctalString() {
    StringBuffer buf = new StringBuffer();
    if (this.signed > 0) {
      buf.append("+");
    } else {
      buf.append("-");
    }
    BigNum n2 = new BigNum("8.0");
    BigNum z = this.integral();
    BigNum x = this.subtract(z);
    Stack<Integer> stk = new Stack<Integer>();
    if (z.compareTo(BigNum.ZERO) == 0) {
      stk.push(0);
    }
    while(z.compareTo(BigNum.ZERO) > 0) {
      BigNum s = z.divide(n2, 0, BigNumRound.DOWN);
      BigNum y = z.subtract(s.multiply(n2));
//      System.out.println(z + "/2=" + s + ",y=" + y);
      stk.push(y.toInt());
      z = s;
    }
    while(!stk.isEmpty()) {
      buf.append(stk.pop());
    }
    if (x.compareTo(BigNum.ZERO) != 0) {
      buf.append(".");
    }
    int cnt = 0;
    while(cnt < 40 && x.compareTo(BigNum.ZERO) != 0) {
      BigNum j = x.multiply(n2);
      BigNum jz = j.integral();
      buf.append(jz.toInt());

      BigNum y = j.subtract(jz);
      x = y;
      cnt ++;
    }
//    System.out.println(buf.toString());
    return buf.toString();
  }
  /**
   *
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    if (this.signed == -1) {
      buf.append("-");
    }
    int idx = 0;
    String tmp;
    for(idx = 0; idx < this.length; idx ++) {
      short ch = this.datas[idx];
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
      if((idx + 1) == this.scale) {
        buf.append(".");
      }
    }
//    System.out.println("[length=" + this.length + ",scale=" + this.scale + "]" + String.valueOf(toCharary(datas, datas.length)));
    if (idx == this.scale) {
      buf.append("0");
    }
    return buf.toString();
  }
  public String toHexString() {
    StringBuffer buf = new StringBuffer();
    if (this.signed > 0) {
      buf.append("+");
    } else {
      buf.append("-");
    }
    BigNum n2 = new BigNum("16.0");
    BigNum z = this.integral();
    BigNum x = this.subtract(z);
    Stack<Integer> stk = new Stack<Integer>();
    if (z.compareTo(BigNum.ZERO) == 0) {
      stk.push(0);
    }
    while(z.compareTo(BigNum.ZERO) > 0) {
      BigNum s = z.divide(n2, 0, BigNumRound.DOWN);
      BigNum y = z.subtract(s.multiply(n2));
//      System.out.println(z + "/2=" + s + ",y=" + y);
      stk.push(y.toInt());
      z = s;
    }
    while(!stk.isEmpty()) {
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
    if (x.compareTo(BigNum.ZERO) != 0) {
      buf.append(".");
    }
    int cnt = 0;
    while(cnt < 40 && x.compareTo(BigNum.ZERO) != 0) {
      BigNum j = x.multiply(n2);
      BigNum jz = j.integral();
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

      BigNum y = j.subtract(jz);
      x = y;
      cnt ++;
    }
//    System.out.println(buf.toString());
    return buf.toString();
  }
  public String toScientificNotation() {
    // aen
    StringBuffer buf = new StringBuffer();
    if (this.signed < 0) {
      buf.append("-");
    }
    int n = 0;
    boolean bflag = false;
    int i = 0;
    for (; i < this.scale; i ++) {
      byte by = this.datas[i];
      if (bflag) {
        n ++;
        buf.append(by);
        continue;
      }
      if (by != 0) {
        bflag = true;
        buf.append(by);
        buf.append(".");
        n ++;
      }
    }
    if (bflag == false) {
      for(;i < this.length; i ++) {
        byte by = this.datas[i];
        if (!bflag) {
          n --;
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
      for(;i < this.length; i ++) {
        byte by = this.datas[i];
        buf.append(by);
      }
    }
    buf.append("e");
    buf.append(n);
    System.out.println(n);
    return buf.toString();
  }

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
  public float toFloat() {
    //float res = 0.0f;
    //return res;
    return FloatingDecimal.parseFloat(this.toString());
  }
  public double toDouble(int scale) {
//    double res = 0.0d;
//    return res;
//    return FloatingDecimal.parseDouble(this.toString());
    double res = 0.0;
    String str = this.toString();
    int idx_pot = str.indexOf('.');
    if (idx_pot > 0) {
    } else {
      idx_pot = 0;
    }
    byte[] bys = str.getBytes();

    int start_pos = idx_pot + scale;
    if (start_pos >= bys.length) {
      start_pos = bys.length - 1;
    }
    double a = 1.0;
    int c = 0;
    for(int i = start_pos; i >= 0; i --) {
      byte by = bys[i];
      if ((char) by == '.') {
        a = 1.0;
        c = -1;
      } else {
        a = Math.pow(10.0, idx_pot - i + c);
        double b = a * (by - '0');
        res = res + b;
      }
    }
    return res;
  }

  protected byte[] add_ary(byte[] data, int pos, byte val) {
    int carry = val;
    int a = 0;
    for (int i = pos; i >= 0; i --) {
      a = data[i] + carry;
      if ((a / 10) > 0) {
        carry = a / 10;
      } else {
        carry = 0;
      }
      data[i] = (byte) (a % 10);
    }
    if (carry != 0) {
      byte[] tmp = new byte[data.length + 1];
      tmp[0] = (byte) carry;
      System.arraycopy(data, 0, tmp, 1, data.length);
      data = tmp;
    }
    return data;
  }
  protected boolean is_zero_ary(byte[] data, int pos) {
    for(int i = pos; i < data.length; i ++) {
      if (data[i] != 0) {
        return false;
      }
    }
    return true;
  }

  public BigNum round(int scale, BigNumRound roundmode) {
    int pos = this.scale + scale - 1;
    int pos_next = pos + 1;
//    int pos_prev = pos - 1;
    if (pos < 0 || pos > this.length) {
      return this;
    }
    byte cur_val = this.datas[pos];

//    byte pre_val = 0;
//    if (pos_prev >= 0 && pos_prev < this.length) {
//      pre_val = this.datas[pos_prev];
//    }
//    System.out.println("pos" + pos_prev + ",val" + pre_val);
//    System.out.println("pos" + pos + ",val" + cur_val);
    byte nex_val = 0;
    if (pos_next >= 0 && pos_next < this.length) {
      nex_val = this.datas[pos_next];
    }
//    System.out.println("pos" + pos_next + ",val" + nex_val);
    byte signed = this.signed;
    int scaleo = this.scale;
    int length = scaleo;
    if (pos > scaleo) {
      length = pos;
    }
    length ++;
    byte[] datas = new byte[length];
    System.arraycopy(this.datas, 0, datas, 0, length);

    if (BigNumRound.UP.equals(roundmode)) {
      // 远离零方向舍入,> 0 进上
      if (nex_val != 0) {
        datas = add_ary(datas, pos, (byte) 1);
      }
    }
    if (BigNumRound.DOWN.equals(roundmode)) {
      // 趋向零方向舍入,> 0 舍下
    }
    if (BigNumRound.CELLING.equals(roundmode)) {
      // 向正无穷方向舍入,
      if (signed > 0) {
        if (nex_val != 0) {
          datas = add_ary(datas, pos, (byte) 1);
        }
      }
    }
    if (BigNumRound.FLOOR.equals(roundmode)) {
      // 向负无穷方向舍入,
      if (signed < 0) {
        if (nex_val != 0) {
          datas = add_ary(datas, pos, (byte) 1);
        }
      }
    }
    if (BigNumRound.HALF_UP.equals(roundmode)) {
      // 最近数字舍入(5进)。这是我们最经典的四舍五入。
      if (nex_val > 4) {
        datas = add_ary(datas, pos, (byte) 1);
      }
    }
    if (BigNumRound.HALF_DOWN.equals(roundmode)) {
      // 最近数字舍入(5舍)。在这里5是要舍弃的。五舍六入。
      if (nex_val > 5) {
        datas = add_ary(datas, pos, (byte) 1);
      }
    }
    if (BigNumRound.HALF_EVENT.equals(roundmode)) {
      // 银行家舍入法。
      if (nex_val > 5) {
        // （2）如果保留位数的后一位如果是6，则进上去。例如5.216保留两位小数为5.22。
        datas = add_ary(datas, pos, (byte) 1);
      }
      if (nex_val == 5) {
        if (is_zero_ary(this.datas, pos_next + 1) == false) {
          // is not zero
          // （4）如果保留位数的后一位如果是5，而且5后面仍有数。例如5.2254保留两位小数为5.23，也就是说如果5后面还有数据，则无论奇偶都要进入。
          datas = add_ary(datas, pos, (byte) 1);
        } else {
//          System.out.println("prev" + pos_prev + ",val" + pre_val);
          if ((cur_val % 2) != 0) {
            // （3）如果保留位数的后一位如果是5，而且5后面不再有数，要根据应看尾数“5”的前一位决定是舍去还是进入: 如果是奇数则进入，如果是偶数则舍去。
            datas = add_ary(datas, pos, (byte) 1);
          }
        }
      }
      // （1）要求保留位数的后一位如果是4，则舍去。例如5.214保留两位小数为5.21。
    }
    return new BigNum(signed, datas, length, scaleo);
  }

  public BigNum and(BigNum num) {
    String t = this.toBinaryString();
    String n = num.toBinaryString();
    System.out.println(t);
    System.out.println(n);
    int tlen = t.length();
    int nlen = n.length();
    int ta = tlen;
    int na = nlen;
    int tb = 0;
    int nb = 0;
    int tpi = t.indexOf(".");
    int npi = n.indexOf(".");
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
        tar[i] = t.charAt(it);
      }
      if (in > 0 && in < nlen) {
        nar[i] = n.charAt(in);
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
        tar[i] = t.charAt(it);
      }
      if (in < nlen) {
        nar[i] = n.charAt(in);
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
  public BigNum or(BigNum num) {
    String t = this.toBinaryString();
    String n = num.toBinaryString();
    System.out.println(t);
    System.out.println(n);
    int tlen = t.length();
    int nlen = n.length();
    int ta = tlen;
    int na = nlen;
    int tb = 0;
    int nb = 0;
    int tpi = t.indexOf(".");
    int npi = n.indexOf(".");
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
        tar[i] = t.charAt(it);
      }
      if (in > 0 && in < nlen) {
        nar[i] = n.charAt(in);
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
        tar[i] = t.charAt(it);
      }
      if (in < nlen) {
        nar[i] = n.charAt(in);
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
  public BigNum xor(BigNum num) {
    String t = this.toBinaryString();
    String n = num.toBinaryString();
    System.out.println(t);
    System.out.println(n);
    int tlen = t.length();
    int nlen = n.length();
    int ta = tlen;
    int na = nlen;
    int tb = 0;
    int nb = 0;
    int tpi = t.indexOf(".");
    int npi = n.indexOf(".");
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
        tar[i] = t.charAt(it);
      }
      if (in > 0 && in < nlen) {
        nar[i] = n.charAt(in);
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
        tar[i] = t.charAt(it);
      }
      if (in < nlen) {
        nar[i] = n.charAt(in);
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
  public BigNum not() {
    String t = this.toBinaryString();
    char[] tar = new char[t.length()];
    for (int i = 0; i < tar.length; i ++) {
      char ch = t.charAt(i);
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
  public BigNum lsh(BigNum shift) {
    if (shift.compareTo(BigNum.ZERO) < 0) {
      return rsh(shift);
    }
    String t = this.toBinaryString();
    System.out.println(t);
    int tlen = t.length();
    int ta = tlen;
    int tb = 0;
    int tpi = t.indexOf(".");
    if (tpi >= 0) {
      tb = ta - tpi;
      ta = tpi;
    } else {
      tpi = ta + 1;
      tb = 1;
    }
    int olen= ta + tb + shift.toInt();
    char[] obuf = new char[olen];
    Arrays.fill(obuf, '0');
    int o = 0;
    boolean bdot = false;
    for (int i = 0; i < tlen; i ++) {
      char ch = t.charAt(i);
      if (ch == '.') {
        continue;
      }
      if (o == (ta + shift.toInt())) {
        obuf[o] = '.';
        o ++;
        bdot = true;
      }
      obuf[o] = ch;
      o ++;
    }
    if (bdot == false) {
      obuf[ta + shift.toInt()] = '.';
    }
    BigNum res = new BigNum(obuf, 0, olen, 2);
    System.out.println(res.toBinaryString());
    return res;
  }
  public BigNum rsh(BigNum shift) {
    if (shift.compareTo(BigNum.ZERO) < 0) {
      return lsh(shift);
    }
    String t = this.toBinaryString();
    System.out.print("in:" + t);
    int tlen = t.length();
    int ta = tlen;
    int tb = 0;
    int tpi = t.indexOf(".");
    if (tpi >= 0) {
      tb = ta - tpi;
      ta = tpi;
    } else {
      tpi = ta + 1;
      tb = 1;
    }
    int olen= ta + tb;
    int shf = shift.toInt();
    System.out.print(",shift:" + shf);
//    System.out.println("olen=" + olen + ",shf=" + shf + ",ta=" + ta + ",tb=" + tb);
    char[] obuf = new char[olen];
    Arrays.fill(obuf, '0');
    int o = shf - 1;
    if (shf < tb) {
      o ++;
    } else {
      o += 2;
    }
//    System.out.println("o=" + o);
    boolean bdot = false;
    for (int i = 0; i < olen - shf; i ++) {
      char ch = t.charAt(i);
      if (ch == '.') {
        continue;
      }
      if (o == (ta)) {
        obuf[o] = '.';
        o ++;
        bdot = true;
      }
      if (o >= olen) {
        break;
      }
      obuf[o] = ch;
      o ++;
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
  public static boolean check(BigNum a, BigNum b, BigNum c, String optionS, int scale, RoundingMode roundingMode) {
    boolean res = false;
    String stra = null;
    BigDecimal bda = null;
    if (a != null) {
      stra = a.toString();
      bda = new BigDecimal(stra);
    }
    String strb = null;
    BigDecimal bdb = null;
    if (b != null) {
      strb = b.toString();
      bdb = new BigDecimal(strb);
    }
    String strc = null;
    BigDecimal bdc = null;
    if (c != null) {
      strc = c.toString();
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
      throw new ArithmeticException("[ERROR]" + a + optionS + b + "=" + c + "=>" + bdc + "<>" + bdd + "(scale=" + scale + ",roundingMode=" + roundingMode + ")");
      }
    return res;
  }
  public void printary(char[] in) {
    for(char ch: in) {
      System.out.print(ch);
    }
    System.out.println();
  }
  public void printary(long[] in) {

    int idx = 0;

    for(idx = 0; idx < in.length; idx ++) {
      long ch = in[idx];

      System.out.print(ch + ",");
    }
    System.out.println();
  }
  public void printary(long[][] in) {

    int idx = 0, idy = 0;
    System.out.println("-----long[][]");
    for(idx = 0; idx < in.length; idx ++) {
      for(idy = 0; idy < in[0].length; idy ++) {
        long ch = in[idx][idy];

        System.out.print(ch + ",");
      }
      System.out.println();
    }
    System.out.println();
  }
  public char[] toCharary(byte[] in, int len) {
    char[] res = new char[len];
    for(int i = 0; i < len; i ++) {
      res[i] = (char) ('0' + in[i]);
    }
    return res;
  }

  public void test_cmp_ary() {
    byte[] a = { 2, 4};
    byte[] b = { 0, 3, 0};
    System.out.println(cmp_ary(a, 2, b));
    a = new byte[] {1, 5, 0};
    b = new byte[] {1, 8, 0, 0};
    System.out.println(cmp_ary(a, 3, b));
  }

  public void test_add_ary() {
    byte[] a = { 9, 9, 9};
    byte[] b = add_ary(a, 1, (byte) 0x01);
    System.out.println("datas=" + String.valueOf(toCharary(b, b.length)));
  }
}
