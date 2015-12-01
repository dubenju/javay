package javay.math;

/**
 *
 * 0123456789
 * -abc.edfhi = len:8, dot pos:4
 * @author DBJ
 *
 */
public class BigNum implements Comparable<BigNum> {
    /** 符号:正号:1,负号:-1 */
    private byte signed;
    /** 数据 十进制 一位对应一位 */
    private byte[] datas;
    /** 数据的长度 */
    private int length;
    /** 小数点的起始位置 */
    private int scale;

//    public static BigNum ZERO = new BigNum("0");
//    public static BigNum ONE  = new BigNum("1");

    /**
     * 构造函数
     * @param str 字符串
     */
    public BigNum(String str) {
        this(str.toCharArray(), 0 , str.toCharArray().length);
//        System.out.println("★String:" + str);
    }

    /**
     * 构造函数
     * @param in 输入数据 字符数组
     * @param offset 起始位置
     * @param len 长度
     * @param system 进制系统
     */
    public BigNum(char[] in, int offset, int len) {
    	// TODO:exception
        /* 初始化 */
        this.signed = 0x01;
        this.datas = new byte[len];

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
        for(; idx < len; idx ++) {
            if(in[idx] == '.') {
                this.scale = cnt;
                continue;
            }
            cnt ++;

            this.datas[idy] = (byte) (in[idx] - '0');
            idy ++;
        }
//        System.out.println("aaa scale=" + this.scale);

        this.length = idy;
        if (this.scale <= 0) {
            this.scale = this.length;
        }

        /* DEBUG:print */
//        printary(datas);
//        System.out.println("datas=" + String.valueOf(toCharary(datas, datas.length)));
    }

    /**
     * 构造函数
     * @param si 符号
     * @param da 数据
     * @param len 长度
     * @param sca 小数点位置
     */
    public BigNum(byte si, byte[] da, int len, int sca) {
        this.signed = si;
        this.datas = da;
        this.length = len;
        this.scale = sca;
    }

    public BigNum(BigNum o) {
    	this.scale = o.scale;
    	this.signed = o.signed;
    	this.datas = o.datas;
    	this.length = o.length;
    }
    
    /**
     * 加法
     * @param augend 加数
     * @return 和
     */
    public BigNum add(BigNum augend) {
        if (augend.isZero()) {
            // 
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

            System.out.println("scaleS=" + scaleS + ",decS=" + decS + ",lengthS=" + lengthS);
            long a = 0;
            /* 小数部 */
            for(int idx = decS; idx > 0; idx --) {
                System.out.println("a1=" + a);
                if ((this.scale + idx) <= this.length) {
                    System.out.println("a=" + this.datas[this.scale + idx]);
                    a = a + this.datas[this.scale + idx];
                }
                if ((augend.scale + idx) <= augend.length) {
                    System.out.println("a=" + augend.datas[augend.scale + idx]);
                    a = a + augend.datas[augend.scale + idx];
                }
                System.out.println("a=" + a);
                dataS[1 + scaleS + idx] = (byte) (0xFF & (a % 10));
                a = a / 10;
            }
            /* 整数部 */
            for (int idx = 0; idx <= scaleS; idx ++) {
                System.out.println("a2=" + a);
                if ((this.scale - idx) >= 0) {
                    a = a + this.datas[this.scale - idx];
                    System.out.println("a=" + this.datas[this.scale - idx]);
                }
                if ((augend.scale - idx) >= 0) {
                    System.out.println("a=" + augend.datas[augend.scale - idx]);
                    a = a + augend.datas[augend.scale - idx];
                }
                System.out.println("a=" + a);
                dataS[1 + scaleS - idx] = (byte) (0xFF & (a % 10));
                a = a / 10;
            }
            System.out.println("a3=" + a);
            dataS[0] = (byte) (0xFF & a);

            return new BigNum(this.signed, dataS, lengthS, scaleS + 1);
        } else {
            return this.subtract(new BigNum((byte)(0x00-augend.signed), augend.datas, augend.length, augend.scale));
        }
    }

    /**
     * 
     * @param subtrahend
     * @return
     */
    public BigNum subtract(BigNum subtrahend) {
        if (subtrahend.isZero()) {
            return this;
        }
        if (this.isZero()) {
            return new BigNum((byte)(0x00-subtrahend.signed), subtrahend.datas, subtrahend.length, subtrahend.scale);
        }
        // TODO:大小调整
        if (this.signed == subtrahend.signed) {
            /* 整数部长度 */
            int scaleS = this.scale;
            if (subtrahend.scale > scaleS) {
                scaleS = subtrahend.scale;
            }
            /* 小数部长度 */
            int decS = this.length - this.scale - 1;
            if ((subtrahend.length - subtrahend.scale - 1) > decS) {
                decS = subtrahend.length - subtrahend.scale;
            }
            /* 长度 */
            int lengthS = 2 + scaleS + decS;
            byte[] dataS = new byte[lengthS];
            byte[] carryS = new byte[lengthS];

            System.out.println("scaleS=" + scaleS + ",decS=" + decS + ",lengthS=" + lengthS);
            long a = 0;
            byte carry = 0;
            /* 小数部 */
            for(int idx = decS; idx > 0; idx --) {
                System.out.println("a1=" + a);
                if ((this.scale + idx) <= this.length) {
                    System.out.println("a=" + this.datas[this.scale + idx]);
                    a = a + this.datas[this.scale + idx];
                }
                if ((subtrahend.scale + idx) <= subtrahend.length) {
                    System.out.println("a=" + subtrahend.datas[subtrahend.scale + idx]);
                    a = a - subtrahend.datas[subtrahend.scale + idx];
                }
                System.out.println("a=" + a);
                if (a < 0) {
                    a = 10 + a;
                    carry = -1;
                }
                dataS[1 + scaleS + idx] = (byte) (0xFF & (a % 10));
                carryS[1 + scaleS + idx] = carry;
                a = a / 10;
                // a = a + carry;
                carry = 0;
            }
            /* 整数部 */
            for (int idx = 0; idx <= scaleS; idx ++) {
                System.out.println("a2=" + a);
                if ((this.scale - idx) >= 0) {
                    System.out.println("a=" + this.datas[this.scale - idx]);
                    a = a + this.datas[this.scale - idx];
                }
                if ((subtrahend.scale - idx) >= 0) {
                    System.out.println("a=" + subtrahend.datas[subtrahend.scale - idx]);
                    a = a - subtrahend.datas[subtrahend.scale - idx];
                }
                System.out.println("a=" + a);
                if (a < 0) {
                    a = 10 + a;
                    carry = -1;
                }
                dataS[1 + scaleS - idx] = (byte) (0xFF & (a % 10));
                carryS[1 + scaleS - idx] = carry;
                a = a /10;
//                a = a + carry;
                carry = 0;
            }
            System.out.println("a3=" + a);
            if (a < 0) {
                a = 10 + a;
                carry = -1;
            }
            dataS[0] = (byte) (0xFF & a);
            carryS[0] = carry;
//            printary(carryS);
            System.out.println("carryS=" + String.valueOf(toCharary(carryS, carryS.length)));

            byte signeds = 0x01;
            if (carry == -1) {
                signeds = -1;
            }

            return new BigNum(signeds, dataS, lengthS, scaleS + 1);
        } else {
            return this.add(new BigNum((byte)(0x00-subtrahend.signed), subtrahend.datas, subtrahend.length, subtrahend.scale));
        }
    }

    /**
     * 
     * @param multiplicand
     * @return
     */
    public BigNum multiply(BigNum multiplicand) {
        if (multiplicand.isZero()) {
            return multiplicand;
        }
        if (multiplicand.equals("1")) {
            return this;
        }
        if (this.isZero()) {
            return this;
        }
        if (this.equals("1")) {
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
        int scale = len - ((len1 - scale1) + (len2 - scale2));
        System.out.println("scale =" + scale + "scale1=" + scale1 + "scale2" + scale2);

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
        printary(data);

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
        printary(dat);

        byte[] result = new byte[len];
        if (carry != 0) {
            // TODO:carry是最高位。
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

        return new BigNum(signed, result, len, scale);
    }

    /**
     * 
     * @param divisor
     * @param decimal_len
     * @param roundmode
     * @return
     */
    public BigNum divide(BigNum divisor, int decimal_len, int roundmode) {
        if (divisor.isZero()) {
            // 除数为零时
            throw new ArithmeticException("Division by zero");
        }
        if (divisor.equals("1")) {
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

        // 小数点位置
        int dscale = divisor.scale;
        int tscale = this.scale;
        // 被除数同步
        tscale += dlen - dscale;

        // 小数部长度
        int odecimal_len = tlen - tscale;
        if (tlen < dlen) {
            odecimal_len += dlen - tlen;
        }
        System.out.println("小数点长度:" + odecimal_len + "=" + tscale + "/" + dscale + ",tlen=" + tlen + ",dlen=" + dlen);

        int idx = 0, idx_next = 0;
        byte[] tmp = new byte[dlen];
        int len_tmp = tmp.length;
        System.arraycopy(this.datas, idx, tmp, idx, len_tmp);
//        System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
        idx_next = len_tmp;

        byte[] out = new byte[olen];
        int ido = 0;
        while(true) {
            int c = cmp_ary(tmp, len_tmp, divisor.datas);
            if (c > 0) {
                out[ido] = (byte) (out[ido] + 1);
                // shift postition
                tmp = sub(tmp, len_tmp, divisor.datas);
                System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
                System.out.println("out[ido]=" + out[ido]);
            }
            if (c == 0) {
                out[ido] = (byte) (out[ido] + 1);
                break;
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
                System.out.println("temp=" + String.valueOf(toCharary(temp, temp.length)));

                idx = idx_next;
                if (idx < this.datas.length) {
                    System.arraycopy(this.datas, idx, temp, temp.length - 1, 1);
                } else {
                    // 向小数部延长
                    if (odecimal_len <= decimal_len) {
                        temp[temp.length - 1] = 0;
                        odecimal_len ++;
                        olen ++;
                        byte[] out2 = new byte[olen];
                        System.arraycopy(out, 0, out2, 0, out.length);
                        out = out2;
                    } else {
                        // 超过指定长度结束。
                        break;
                    }

                }
                idx_next ++;
                ido ++;

                tmp = temp;
                len_tmp = tmp.length;
//                System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
            }
        }
        System.out.println("out1=" + String.valueOf(toCharary(out, out.length)));
        out = removeFirstZero(out, olen - odecimal_len);
        System.out.println("out2=" + String.valueOf(toCharary(out, out.length)));
        System.out.println(this.toString() + "/" + divisor.toString() + "=");
        return new BigNum(osigned, out, out.length, out.length - odecimal_len);
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
            System.out.println("a:" + String.valueOf(toCharary(a, lena)) + "_vs_" + String.valueOf(toCharary(b, b.length)) + "=" + c + ",offset=" + offset);
            return c;
        }

        for (int idx = 0; idx < b.length; idx ++) {
            if (a[idx + offset] > b[idx]) {
                c = 1;
                break;
            }
            if (a[idx + offset] < b[idx]) {
                c = -1;
                break;
            }
        }

        System.out.println("b:" + String.valueOf(toCharary(a, lena)) + "_vs_" + String.valueOf(toCharary(b, b.length)) + "=" + c);
        return c;
    }
    /**
     *
     * @param a
     * @param b
     * @return a - b
     */
    protected byte[] sub(byte[] a, int lena, byte[] b) {
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
//            System.out.println("□" + a[posa] + "-" + b[posb] + "=" + c2[posa]);
        }
        while (carry == 0 && posa >= 0 && a[posa] != 0) {
            c2[posa] = a[posa];
            posa --;
        }
        while (carry < 0) {
            c2[posa] = (byte) (a[posa] + carry);
//            System.out.println("□" + a[posa] + "-" + carry + "=" + c2[posa]);
            carry = 0;
            posa --;
        }

        return c2;
    }

    protected boolean isZero() {
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
        System.out.println("@removeFirstZero:in的长度=" + in.length + ",整数部的长度=" + dotpos + ",小数部长度=" + decimal_len + ",in:" + String.valueOf(toCharary(in, in.length)));
        int i = 0;
        boolean bFlag = false;
        for(i = 0;0 < dotpos && i < dotpos; i ++) {
//            System.out.println("i=" + i + "in[i]=" + in[i]);
            bFlag = true;
            if (in[i] != 0) {
                break;
            }
        }

        System.out.println("@removeFirstZero:i=" + i + ",bFlag=" + bFlag);
        if (bFlag && (i + decimal_len == in.length)) {
            // 去除个数+小数部长度=数据长度的话，不需要格式化
//            System.out.println("@removeFirstZero:return by equal");
            return in;
        }

        byte[] res;
        if (dotpos > 1 && i == dotpos) {
            // 整数部为零
            res = new byte[decimal_len + 1];
            i --;
        } else {
            if (bFlag) {
                res = new byte[decimal_len + dotpos - i];
                i --;
            } else {
                res = new byte[decimal_len + dotpos + 1];
            }
        }

        if (bFlag == false) {
            i = -1;
        }
        System.out.println("@removeFirstZero:res.length=" + res.length + ",in.length=" + in.length + ",i=" + i);
        if (res.length <= in.length) {
            System.arraycopy(in, i + 1, res, 0, res.length);
        } else {
            System.arraycopy(in, i + 1, res, res.length - in.length, in.length);
        }
        return res;
    }

    /**
     * 
     * @param n
     * @return
     */
    public BigNum mod(BigNum n) {
    	// TODO:un
        return null;
    }

    /**
     * 
     * @param o
     * @return
     */
    public BigNum pow(long n) {
    	if (n < 0) {
    		throw new ArithmeticException("Invalid operation");
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
     * 比较大小
     */
    public int compareTo(BigNum o) {
        int result = 0;
        if (this.signed > o.signed) {
            return 1;
        }
        if (this.signed < o.signed) {
            return -1;
        }
        int a , b;
        for (a = this.scale - 1, b = o.scale - 1; a >= 0 && b >= 0; a --, b --) {
            result = this.datas[a] - o.datas[b];
            if( result != 0 ) {
                break;
            }
        }
        if (result == 0) {
            if (a >= 0) {
                result = 1;
            } else if (b >= 0) {
                result = -1;
            }
        }
        if (result == 0) {
            for (a = this.scale, b = o.scale; a < this.length && b < o.length; a ++, b ++) {
                result = this.datas[a] - o.datas[b];
                if( result != 0 ) {
                    break;
                }
            }
        }
        if (result == 0) {
            if (a < this.length) {
                result = 1;
            } else if (b < o.length) {
                result = -1;
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
        for(idx = 0; idx < length; idx ++) {
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
        buf.append("[length=" + this.length + ",scale=" + this.scale + "]");
        return buf.toString();
    }

    public BigNum round(int scale, int roundmode) {
        // TODO:wait 
    	return null;
    }

    /** for DEBUG */
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

}