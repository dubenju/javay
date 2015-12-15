package javay.math;

import sun.misc.FloatingDecimal;

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

    public static final BigNum ZERO = new BigNum("0");
    public static final BigNum ONE  = new BigNum("1");
    //                                    3.14159265358979323846
    //                                    3.1415926535897932384626433832795
    public static final BigNum PI = new BigNum("3.14159265358979323846264338327950288419716939937510");
    public static final BigNum E  = new BigNum("2.71828182845904523536028747135266249775724709369995");
    public static final BigNum BYTE_MIN_VALUE = new BigNum("-128");
    public static final BigNum BYTE_MAX_VALUE = new BigNum( "127");
    public static final BigNum SHORT_MIN_VALUE = new BigNum("-32768");
    public static final BigNum SHORT_MAX_VALUE = new BigNum( "32767");
    public static final BigNum INT_MIN_VALUE = new BigNum("-2147483648");
    public static final BigNum INT_MAX_VALUE = new BigNum( "2147483647");
    public static final BigNum LONG_MIN_VALUE = new BigNum("-9223372036854775808");
    public static final BigNum LONG_MAX_VALUE = new BigNum( "9223372036854775807");

    /**
     * 构造函数
     * @param str 字符串
     */
    public BigNum(String str) {
        this(str.toCharArray(), 0 , str.toCharArray().length);
        System.out.println("★String:" + str);
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
        for(; idx < len; idx ++) {
            if(in[idx] == '.') {
                this.scale = cnt;
                continue;
            }
            cnt ++;

            dats[idy] = (byte) (in[idx] - '0');
            idy ++;
        }
//        System.out.println("aaa scale=" + this.scale);

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

        /* DEBUG:print */
        System.out.println("datas=" + String.valueOf(toCharary(datas, datas.length)));
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

    public BigNum(byte b) {
    	this(Byte.toString(b));
    }
    public BigNum(short s) {
    	this(Short.toString(s));
    }
    public BigNum(int i) {
    	this(Integer.toString(i));
    }

    public BigNum(long l) {
    	this(Long.toString(l));
    }


    public BigNum(float f) {
    	this(Float.toString(f));
    }

    public BigNum(double d) {
    	this(Double.toString(d));
    }

    /* ********************************************
     * Option
     * ********************************************/

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

            System.out.println("加法scaleS=" + scaleS + ",decS=" + decS + ",lengthS=" + lengthS);
            long a = 0;
            /* 小数部 */
            for(int idx = decS; idx > 0; idx --) {
//                System.out.println("a1=" + a);
                if (0<= (this.scale + idx) && (this.scale + idx) < this.length) {
//                    System.out.println("a=" + this.datas[this.scale + idx]);
                    a = a + this.datas[this.scale + idx];
                }
                if (0 <= (augend.scale + idx) && (augend.scale + idx) < augend.length) {
//                    System.out.println("a=" + augend.datas[augend.scale + idx]);
                    a = a + augend.datas[augend.scale + idx];
                }
//                System.out.println("a=" + a);
                dataS[1 + scaleS + idx] = (byte) (0xFF & (a % 10));
                a = a / 10;
//                System.out.println("加法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));
            }
            /* 整数部 */
            for (int idx = 0; idx <= scaleS; idx ++) {
//                System.out.println("a2=" + a);
                if (0 <= (this.scale - idx) && (this.scale - idx) < this.datas.length) {
                    a = a + this.datas[this.scale - idx];
//                    System.out.println("a=" + this.datas[this.scale - idx]);
                }
                if (0 <= (augend.scale - idx)  && (augend.scale - idx) < augend.datas.length) {
//                    System.out.println("a=" + augend.datas[augend.scale - idx]);
                    a = a + augend.datas[augend.scale - idx];
                }
//                System.out.println("a=" + a);
                if ((1 + scaleS - idx) < dataS.length) {
                	dataS[1 + scaleS - idx] = (byte) (0xFF & (a % 10));
                }
                a = a / 10;
//                System.out.println("加法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));
            }
//            System.out.println("a3=" + a);
            dataS[0] = (byte) (0xFF & a);
            System.out.println("加法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));

            scaleS ++;
            byte[] dataS1 = removeFirstZero(dataS, scaleS);
            
            return new BigNum(this.signed, dataS1, dataS1.length, dataS1.length - dataS.length + scaleS);
        } else {
        	if (this.signed < 0) {
        		System.out.println("-----");
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
    public BigNum subtract(BigNum subtrahend) {
        if (subtrahend.isZero()) {
            return this;
        }
        if (this.isZero()) {
            return new BigNum((byte)(0x00-subtrahend.signed), subtrahend.datas, subtrahend.length, subtrahend.scale);
        }
        if (this.signed == subtrahend.signed) {
            byte signeds = 0x01;
        	// 大小调整
            BigNum minuend = this;
            if (minuend.abs().compareTo(subtrahend.abs()) < 0) {
            	System.out.println("减法CHG:" + minuend.abs() + " vs " + subtrahend.abs() + "=");
            	minuend = subtrahend;
            	subtrahend = this;
            	signeds = -1;
            }

            System.out.println(minuend + " - " + subtrahend + "=");
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

            System.out.println("减法scaleS=" + scaleS + ",decS=" + decS + ",lengthS=" + lengthS);
            long a = 0;
            byte carry = 0;
            /* 小数部 */
            for(int idx = decS; idx > 0; idx --) {
//                System.out.println("a1=" + a);
                if (0 <= (minuend.scale + idx) && (minuend.scale + idx) < minuend.length) {
//                    System.out.println("a=" + minuend.datas[minuend.scale + idx]);
                    a = a + minuend.datas[minuend.scale + idx];
                }
                if (0 <= (subtrahend.scale + idx) && (subtrahend.scale + idx) < subtrahend.length) {
//                    System.out.println("a=" + subtrahend.datas[subtrahend.scale + idx]);
                    a = a - subtrahend.datas[subtrahend.scale + idx];
                }
//                System.out.println("a=" + a);
                if (a < 0) {
                    a = 10 + a;
                    carry = -1;
                }
                dataS[1 + scaleS + idx] = (byte) (0xFF & (a % 10));
                carryS[1 + scaleS + idx] = carry;
                a = a / 10;
                a = a + carry;
                carry = 0;
//                System.out.println("减法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));
            }
            /* 整数部 */
            for (int idx = 0; idx <= scaleS; idx ++) {
//                System.out.println("整数部a2=" + a);
                if ((minuend.scale - idx) >= 0 && (minuend.scale - idx) < minuend.datas.length) {
//                    System.out.println("整数部("+ (minuend.scale - idx) +")=" + minuend.datas[minuend.scale - idx]);
                    a = a + minuend.datas[minuend.scale - idx];
                }
                if ((subtrahend.scale - idx) >= 0 && (subtrahend.scale - idx) < subtrahend.datas.length) {
//                    System.out.println("整数部a=" + subtrahend.datas[subtrahend.scale - idx]);
                    a = a - subtrahend.datas[subtrahend.scale - idx];
                }
//                System.out.println("整数部a=" + a);
                if (a < 0) {
                    a = 10 + a;
                    carry = -1;
                }
//                System.out.println("整数部(1 + scaleS - idx)=" + (1 + scaleS - idx) +",dataS.length=" + dataS.length + ",a=" + a + ",carry=" + carry);
                if ((1 + scaleS - idx) < dataS.length) {
	                dataS[1 + scaleS - idx] = (byte) (0xFF & (a % 10));
	                carryS[1 + scaleS - idx] = carry;
                }
                a = a / 10;
                a = a + carry;
                carry = 0;
//                System.out.println("减法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));
            }

//            System.out.println("a3=" + a);
            if (a < 0) {
                a = 10 + a;
                carry = -1;
            }
            dataS[0] = (byte) (0xFF & a);
            carryS[0] = carry;
//            printary(carryS);
//            System.out.println("减法dataS=" + String.valueOf(toCharary(dataS, dataS.length)));
            System.out.println("减法carryS=" + String.valueOf(toCharary(carryS, carryS.length)));


//            if (carry == -1) {
//                signeds = -1;
//            }

            scaleS += 1;
            byte[] dataS1 = removeFirstZero(dataS, scaleS);

            return new BigNum(signeds, dataS1, dataS1.length, dataS1.length - dataS.length + scaleS);
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
        System.out.println("乘法小数部长度 =" + decimal_len + "scale1=" + scale1 + "scale2" + scale2);

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
//        printary(data);

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
//        printary(dat);

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

        // remove zero;
        byte[] result1 = removeFirstZero(result, decimal_len);

        return new BigNum(signed, result1, result1.length, result1.length - result.length + decimal_len);
    }

    /**
     * 除法
     * @param divisor
     * @param decimal_len
     * @param roundmode
     * @return
     */
    public BigNum divide(BigNum divisor, int decimal_len, BigNumRound roundmode) {
    	System.out.println("除法⚫⚫⚫️" + this + "÷" + divisor +"等于");
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
        System.out.println("除法⚫⚫⚫️符号" + osigned);

        // 小数点位置
        int dscale  = divisor.scale;
        int tscale  = this.scale;
        int dlen    = divisor.length;
        int tlen    = this.length;
        int ddeclen = dlen - dscale;
        int tdeclen = tlen - tscale;
        System.out.println("除法信息:" + tlen + "," + tscale  + "," + tdeclen + "/" + dlen + "," + dscale  + "," + ddeclen);

        // 被除数同步
        tscale += divisor.length - dscale;
        System.out.println("除法小数点位置:" + tscale + "/" + dscale);

        // 小数部长度
        int odecimal_len = tlen - tscale;
        System.out.println("除法⚫⚫⚫小数点长度:" + odecimal_len + "=" + tscale + "/" + dscale + ",tlen=" + tlen + ",dlen=" + dlen);
        int max_decimal_len = decimal_len;
        if (BigNumRound.HALF_EVENT.equals(roundmode)) {
        	max_decimal_len ++;
        }
        int idx = 0, idx_next = 0;
        byte[] tmp = new byte[dlen];
        int len_tmp = tmp.length;
        if (this.datas.length < tmp.length) {
        	// TODO:??
        	len_tmp = this.datas.length;
        }
        System.arraycopy(this.datas, idx, tmp, idx, len_tmp);
//        System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
        idx_next = len_tmp;

        // 位数
        int olen = tlen - dlen + 1;
        if (olen <= 0) {
        	olen = 2; // 0.0
        }
        System.out.println("除法⚫⚫⚫️长度" + olen);
        int oscale = 0;

        byte[] out = new byte[olen];
        int ido = 0;
        while(true) {
            int c = cmp_ary(tmp, len_tmp, divisor.datas);
            if (c >= 0) {
                out[ido] = (byte) (out[ido] + 1);
                // shift postition
                tmp = sub_ary(tmp, len_tmp, divisor.datas);
//                System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
//                System.out.println("out["+ ido + "]=" + out[ido]);
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
//                System.out.println("temp=" + String.valueOf(toCharary(temp, temp.length)));

                idx = idx_next;
//                System.out.println("input pos=" + idx + "tscale=" + tscale + "ido=" + ido);
                if (idx == tscale) {
                	// 小数点位置
                	oscale = ido + 1;
                	System.out.println("除法★小数点位置oscale=" + oscale );
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
                				if (odecimal_len > max_decimal_len + 10) {
                					break;
                				}
                			}
                		}
                	}
                    // 向小数部延长
//                	System.out.println(odecimal_len + "vs" + max_decimal_len);
                    if (odecimal_len <= max_decimal_len) {
                	// if (ido <= olen) {
                        temp[temp.length - 1] = 0;
                        odecimal_len ++;
                        olen ++;
                        byte[] out2 = new byte[olen];
                        System.arraycopy(out, 0, out2, 0, out.length);
                        out = out2;
                    } else {
                        // 超过指定长度结束。
                    	// banker
                        break;
                    }
                }
                idx_next ++;
                ido ++;

                tmp = temp;
                len_tmp = tmp.length;
//                System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
            }
//            System.out.println("除法out=" + String.valueOf(toCharary(out, out.length)));
        }
        
        System.out.println("除法apos=" + (oscale + decimal_len - 1) + ",val=" + out[(oscale + decimal_len - 1)]);
    	if (BigNumRound.UP.equals(roundmode)) {
    		// 远离零方向舍入,> 0 进上
    		if (out[(oscale + decimal_len - 1)] != 0) {
    			out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
    		}
    	}
    	if (BigNumRound.DOWN.equals(roundmode)) {
    		// 趋向零方向舍入,> 0 舍下
    	}
    	if (BigNumRound.CELLING.equals(roundmode)) {
    		// 向正无穷方向舍入,
    		if (osigned > 0) {
        		if (out[(oscale + decimal_len - 1)] != 0) {
        			out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
        		}
    		}
    	}
    	if (BigNumRound.FLOOR.equals(roundmode)) {
    		// 向负无穷方向舍入,
    		if (osigned < 0) {
        		if (out[(oscale + decimal_len - 1)] != 0) {
        			out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
        		}
    		}
    	}
    	if (BigNumRound.HALF_UP.equals(roundmode)) {
    		// 最近数字舍入(5进)。这是我们最经典的四舍五入。
    		if (out[(oscale + decimal_len - 1)] > 4) {
    			out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
    		}
    	}
    	if (BigNumRound.HALF_DOWN.equals(roundmode)) {
    		// 最近数字舍入(5舍)。在这里5是要舍弃的。五舍六入。
    		if (out[(oscale + decimal_len - 1)] > 5) {
    			out = add_ary(out, (oscale + decimal_len - 1), (byte) 1);
    		}
    	}
        if (BigNumRound.HALF_EVENT.equals(roundmode)) {
        	// 银行家舍入法。
        	System.out.println("除法pos=" + (oscale + decimal_len) + ",val=" + out[(oscale + decimal_len)]);
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
        System.out.println("除法out1=" + String.valueOf(toCharary(out3, out3.length)) + "小数点位置=" + oscale);
        byte[] out2 = removeFirstZero(out3, oscale);
        oscale = oscale - out3.length + out2.length;
        System.out.println("除法out2=" + String.valueOf(toCharary(out2, out2.length)) + "小数点位置=" + oscale);
        System.out.println(this.toString() + "/" + divisor.toString() + "=");
        
        // return new BigNum(osigned, out, out.length, out.length - odecimal_len - 1);
        return new BigNum(osigned, out2, out2.length, oscale);
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
//            System.out.println("a:" + String.valueOf(toCharary(a, lena)) + "_vs_" + String.valueOf(toCharary(b, b.length)) + "=" + c + ",offset=" + offset);
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
//            System.out.println("c:" + String.valueOf(toCharary(a, lena)) + "_vs_" + String.valueOf(toCharary(b, b.length)) + "=" + c + ",offset=" + offset);
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

//        System.out.println("b:" + String.valueOf(toCharary(a, lena)) + "_vs_" + String.valueOf(toCharary(b, b.length)) + "=" + c);
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
//            System.out.println("□" + a[posa] + "-" + b[posb] + "=" + c2[posa]);
        }
        while (carry == 0 && posa >= 0 && a[posa] != 0) {
            c2[posa] = a[posa];
            posa --;
        }
        while (posa >= 0 && carry < 0) {
            c2[posa] = (byte) (a[posa] + carry);
//            System.out.println("□" + a[posa] + "-" + carry + "=" + c2[posa]);
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
        System.out.println("@removeFirstZero:in的长度=" + in.length + ",整数部的长度=" + dotpos + ",小数部长度=" + decimal_len + ",in:" + String.valueOf(toCharary(in, in.length)));
        int i = 0;
        boolean bFlag = false;
        for(i = 0; 0 < dotpos && i < dotpos; i ++) {
//            System.out.println("i=" + i + "in[i]=" + in[i]);
            bFlag = true;
            if (in[i] != 0) {
            	i ++;
                break;
            }
        }

        System.out.println("@removeFirstZero:i=" + i + ",bFlag=" + bFlag);
        if (bFlag && i == 1 && (i + decimal_len == in.length)) {
            // 0.* 去除个数+小数部长度=数据长度的话，不需要格式化
            System.out.println("@removeFirstZero:return by equal");
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

//        if (bFlag == false) {
//            i = -1;
//        }
        i --;
        if (i < -1) {
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
     * @param divisor
     * @return
     */
    public BigNum mod(BigNum divisor) {
    	System.out.println("this=" + String.valueOf(toCharary(this.datas, this.datas.length)));
    	System.out.println("divisor=" + String.valueOf(toCharary(divisor.datas, divisor.datas.length)));
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
        System.out.println("长度:=" + olen + ",tlen=" + tlen + ",dlen=" + dlen);

        // 小数点位置
        int dscale = divisor.scale;
        int tscale = this.scale;
        // 被除数同步
        int off = dlen - dscale;
        tscale += off;
        System.out.println("小数点对齐长度：" + off);

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
            if (c >= 0) {
                out[ido] = (byte) (out[ido] + 1);
                // shift postition
                tmp = sub_ary(tmp, len_tmp, divisor.datas);
                System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
                System.out.println("out["+ ido + "]=" + out[ido]);
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
                		System.out.println("lead=" + lead + ",oscale=" + oscale + ",off=" + off);
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
                	System.out.println("结果长度：" + out.length + ",lent=" + lent + ",leny=" + leny);
                	break;
                }

                tmp = temp;
                len_tmp = tmp.length;
//                System.out.println("tmp=" + String.valueOf(toCharary(tmp, tmp.length)));
            }
        }
//        System.out.println("out1=" + String.valueOf(toCharary(out, out.length)));
//        out = removeFirstZero(out, olen - odecimal_len);
//        System.out.println("out2=" + String.valueOf(toCharary(out, out.length)));
//        System.out.println(this.toString() + "/" + divisor.toString() + "=");
        return new BigNum(osigned, out, out.length, out.length - odecimal_len - off);
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
     *
     * @param o
     * @return
     */
    public BigNum pow(BigNum n) {
    	if (n.compareTo(BigNum.ZERO) < 0) {
    		throw new ArithmeticException("Invalid operation");
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
    	if(this.compareTo(BigNum.ZERO) <= 0) {
    		return this;
    	}

    	BigNum result = new BigNum(this);
    	BigNum next = this.subtract(BigNum.ONE);
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
    	System.out.print(this + " vs " + o + " = ");
        int result = 0;
        if (this.signed > o.signed) {
        	System.out.println(1);
            return 1;
        }
        if (this.signed < o.signed) {
        	System.out.println(-1);
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
	        System.out.println("比较result1=" + result);
//	        if (result == 0) {
//	            if (a >= 0) {
//	                result = 1;
//	            } else if (b >= 0) {
//	                result = -1;
//	            }
//	        }
        }
        System.out.println("比较result2=" + result);
        if (result == 0) {
        	// 小数部
            for (a = this.scale, b = o.scale; 0 <= a && a < this.length && 0<= b && b < o.length; a ++, b ++) {
                result = this.datas[a] - o.datas[b];
                if( result != 0 ) {
                    break;
                }
            }
	        System.out.println("比较result3=" + result);
	        if (result == 0) {
	            if (a < this.length) {
	                result = 1;
	            } else if (b < o.length) {
	                result = -1;
	            }
	        }
        }
        System.out.println("比较result4=" + result);
        System.out.println(this.signed * result + "(" + this.signed + result + ")");
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
        //buf.append("[length=" + this.length + ",scale=" + this.scale + "]");
        System.out.println("[length=" + this.length + ",scale=" + this.scale + "]");
        if (idx == this.scale) {
        	buf.append("0");
        }
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
	public double toDouble() {
//		double res = 0.0d;
//		return res;
		return FloatingDecimal.parseDouble(this.toString());
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
        int pos_prev = pos - 1;
    	if (pos < 0 || pos > this.length) {
    		return this;
    	}
    	byte cur_val = this.datas[pos];
    	
    	byte pre_val = 0;
    	if (pos_prev >= 0 && pos_prev < this.length) {
    		pre_val = this.datas[pos_prev];
    	}
    	System.out.println("pos" + pos_prev + ",val" + pre_val);
    	System.out.println("pos" + pos + ",val" + cur_val);
    	byte nex_val = 0;
    	if (pos_next >= 0 && pos_next < this.length) {
    		nex_val = this.datas[pos_next];
    	}
    	System.out.println("pos" + pos_next + ",val" + nex_val);
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
    				System.out.println("prev" + pos_prev + ",val" + pre_val);
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

    /**
     * 手动开平方
     * 1．将被开方数的整数部分从个位起向左每隔两位划为一段，用撇号分开，分成几段，
     *    表示所求平方根是几位数；小数部分从最高位向后两位一段隔开，段数以需要的精度+1为准。
     * 2．根据左边第一段里的数，求得平方根的最高位上的数。（在右边例题中，比5小的平方数是4，所以平方根的最高位为2。）
     * 3．从第一段的数减去最高位上数的平方，在它们的差的右边写上第二段数组成第一个余数。
     * 4．把第二步求得的最高位的数乘以20去试除第一个余数，所得的最大整数作为试商。（右例中的试商即为[152/(2×20)]＝[3.8]＝3。）
     * 5．用第二步求得的的最高位数的20倍加上这个试商再乘以试商。如果所得的积小于或等于余数，试商就是平方根的第二位数；如果所得的积大于余数，就把试商减小再试，得到的第一个小于余数的试商作为平方根的第二个数。（即3为平方根的第二位。）
     * 6．用同样的方法，继续求平方根的其他各位上的数。用上一个余数减去上法中所求的积（即152－129＝23），与第三段数组成新的余数（即2325）。这时再求试商，要用前面所得到的平方根的前两位数（即23）乘以20去试除新的余数（2325），所得的最大整数为新的试商。（2325/(23×20)的整数部分为5。）
     * 7．对新试商的检验如前法。（右例中最后的余数为0，刚好开尽，则235为所求的平方根。）
     * @param scale
     * @param roundmode
     * @return
     */
    public BigNum sqrt(int scale, int roundmode) {
        if(this.compareTo(BigNum.ZERO) < 0) {
        	throw new ArithmeticException("sqrt with negative");
        }
    	return null;
    }

    public BigNum nthroot(int n, int scale, int roundmode) {
        if(this.compareTo(BigNum.ZERO) < 0) {
        	throw new ArithmeticException("sqrt with negative");
        }
        return null;
    }

    public BigNum log10() {
    	int a = this.scale - 1;

    	BigNum b = new BigNum(this.signed, this.datas, this.length, 1);
    	System.out.println("b=" + b);
    	double c = b.toDouble();
    	System.out.println("c=" + c);
    	double d = StrictMath.log10(c);
    	System.out.println("d=" + d);
    	BigNum e = new BigNum(d);
    	System.out.println("e=" + e);
    	BigNum res = new BigNum(a);
    	res = res.add(e);
    	System.out.println("res=" + res);
    	return res;
    }

    public BigNum lg () {
    	return this.log10();
    }

    public BigNum ln(int decimal, BigNumRound round) {
    	return log(BigNum.E, decimal, round);
    }

    public BigNum log(BigNum a, int decimal, BigNumRound round) {
    	BigNum t = this.log10();
    	BigNum b = a.log10();
    	return t.divide(b, decimal, round);
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
