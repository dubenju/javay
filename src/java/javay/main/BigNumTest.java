/**
 *
 */
package javay.main;

import javay.math.BigNum;
import javay.math.BigNumRound;
import javay.math.MathBn;
import javay.swing.CalcultorConts;

/**
 * @author DBJ
 *
 */
public class BigNumTest {
	public void testbyte() throws Exception {
		byte b = Byte.MIN_VALUE;
		BigNum testa = BigNum.BYTE_MIN_VALUE;
		while(b != Byte.MAX_VALUE) {
			testa = testa.add(BigNum.ONE);
			b ++;
			if (b != testa.toByte()) {
				System.out.println(b + "<>" + testa);
				break ;
			} else {
				System.out.println(b + "==" + testa);
			}
		}
	}
	public void testshort() throws Exception {
		short b = Short.MIN_VALUE;
		BigNum testa = BigNum.SHORT_MIN_VALUE;
		while(b != Short.MAX_VALUE) {
			testa = testa.add(BigNum.ONE);
			b ++;
			if (b != testa.toShort()) {
				System.out.println(b + "<>" + testa);
				throw new Exception(b + "<>" + testa);
				// break ;
			} else {
				System.out.println(b + "==" + testa);
			}
		}
	}
	public void testint() throws Exception {
		int b = Integer.MIN_VALUE;
		BigNum testa = BigNum.INT_MIN_VALUE;
		while(b != Integer.MAX_VALUE) {
			testa = testa.add(BigNum.ONE);
			b ++;
			if (b != testa.toInt()) {
				System.out.println(b + "<>" + testa);
				throw new Exception(b + "<>" + testa);
				// break ;
			} else {
				System.out.println(b + "==" + testa);
			}
		}
	}
	public void testlong() throws Exception {
		long b = Long.MIN_VALUE;
		BigNum testa = BigNum.LONG_MIN_VALUE;
		while(b != Long.MAX_VALUE) {
			testa = testa.add(BigNum.ONE);
			b ++;
			if (b != testa.toLong()) {
				System.out.println(b + "<>" + testa);
				throw new Exception(b + "<>" + testa);
				// break ;
			} else {
				System.out.println(b + "==" + testa);
			}
		}
	}
	public void testSubtract() throws Exception {
//		byte b = Byte.MAX_VALUE;
//		BigNum testa = BigNum.BYTE_MAX_VALUE;
//		while(b != Byte.MIN_VALUE) {
//			testa = testa.subtract(BigNum.ONE);
//			b --;
//			if (b != testa.toByte()) {
//				System.out.println(b + "<>" + testa);
//				break ;
//			} else {
//				System.out.println(b + "==" + testa);
//			}
//		}
		BigNum s = BigNum.PI.subtract(new BigNum("0.1"));
		System.out.println(s);
	}
	public void print99() throws Exception {
		for(int i = 1; i <= 9; i ++) {
			for (int j = 1; j <= 9; j ++) {
				BigNum val = new BigNum("" + i).multiply(new BigNum("" + j));
				if ((i * j) != val.toLong()) {
					System.out.println((i * j) + "<>" + val.toLong());
					throw new Exception((i * j) + "<>" + val.toLong());
				} else {
					System.out.println((i * j) + "==" + val.toLong());
				}
			}
			System.out.println();
		}
	}
	public void testDivide() throws Exception {
//		byte b = Byte.MAX_VALUE;
//		BigNum testa = BigNum.BYTE_MAX_VALUE;
//		while(b != Byte.MIN_VALUE) {
//			BigNum testc = testa.divide(new BigNum("2"), 0, 0);
//			byte c = (byte) (b / 2);
//			testa = testa.subtract(BigNum.ONE);
//			b --;
//			if (c != testc.toByte()) {
//				System.out.println(c + "<>" + testc);
//				throw new Exception(c + "<>" + testc);
////				break ;
//			} else {
//				System.out.println(c + "==" + testc);
//			}
//		}
		BigNum bg15 = new BigNum("15.0");
		BigNum bg180 = new BigNum("180.0");
		BigNum r = bg15.divide(bg180, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
		System.out.println(r);
		
		BigNum bg104508 = new BigNum("10.4508");
		BigNum bg2 = new BigNum("2.0");
		r = bg104508.divide(bg2, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
		System.out.println("r=" + r);
		
		BigNum bg30 = new BigNum("30.0");
		r = bg30.divide(bg180, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
		System.out.println("r=" + r);
		
		BigNum bg45 = new BigNum("45.0");
		r = bg45.divide(bg180, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
		System.out.println("r=" + r);
	}

	public void testMod() {
		BigNum test324 = new BigNum("3.24");
		BigNum test03 = new BigNum("0.3");
		BigNum test = test324.mod(test03);
		System.out.println(test);

		BigNum test3 = new BigNum("3");
		test = test3.mod(test3);
		System.out.println("3 % 3 = " + test);

		BigNum test324a = new BigNum("324");
		test = test324a.mod(test3);
		System.out.println("324 % 3 = " + test);

		test324 = new BigNum("3.24");
		BigNum test003 = new BigNum("0.03");
		test = test324.mod(test003);
		System.out.println("3.24 % 0.03 = " + test);
	}

	public void test003() {
		BigNum test003 = new BigNum("0.03");
		System.out.println("0.03=" + test003);
	}
	public void testCmp() {
		BigNum test = new BigNum(0);
		test.test_cmp_ary();
	}

	public void testDouble() {
		double a = 15.3;
		BigNum b = new BigNum(a);
		System.out.println(a + "=" + b.toString() + "," + b.toDouble() + "------------------------>");
		BigNum c = b.log10();
		System.out.println("<---------------------log10(" + b + ")=" + c + "," + Math.log10(a));

		BigNum e = b.ln(3, BigNumRound.HALF_EVENT);
		System.out.println("<---------------------ln(" + b + ")=" + e + "," + Math.log1p(a));
	}
	
	public void testRD() {
		for (int i = 0; i <= 180; i += 15) {
			BigNum r = MathBn.toRadians(new BigNum(i));
			BigNum d = MathBn.toDegrees(r);
			System.out.println(i + "=" + r + "," + d);
		}
	}
	public void testRound() {
		
		BigNum data = new BigNum("1.2345");
//		data.test_add_ary();
		BigNum dat = data.round(1, BigNumRound.UP);
		System.out.println(dat);
		dat = data.round(1, BigNumRound.DOWN);
		System.out.println(dat);
		dat = data.round(1, BigNumRound.CELLING);
		System.out.println(dat);
		dat = data.round(1, BigNumRound.FLOOR);
		System.out.println(dat);
		dat = data.round(1, BigNumRound.HALF_UP);
		System.out.println(dat);
		dat = data.round(1, BigNumRound.HALF_DOWN);
		System.out.println(dat);
		dat = data.round(1, BigNumRound.HALF_EVENT);
		System.out.println(dat);
		data = new BigNum("5.2254");
		dat = data.round(2, BigNumRound.HALF_EVENT);
		System.out.println(dat);
		data = new BigNum("5.215");
		dat = data.round(2, BigNumRound.HALF_EVENT);
		System.out.println(dat);
		data = new BigNum("5.225");
		dat = data.round(2, BigNumRound.HALF_EVENT);
		System.out.println(dat);
		data = new BigNum("5.214");
		dat = data.round(2, BigNumRound.HALF_EVENT);
		System.out.println(dat);
	}
	public void testToRadians() {
		for (int degree = 0; degree <= 360; degree += 15) {
            if (degree % 30 == 0 || degree % 45 == 0) {
            	BigNum d = new BigNum(degree);
            	BigNum r1 = MathBn.toRadians(d);
                double r2     = Math.toRadians(degree);
                System.out.println(String.format("%3d : %s - %13.10f = %13.10f", degree, r1, r2, r1.toDouble() - r2));
                System.out.println();
            }
        }
	}
	public void testSin() {
        for (int degree = 0; degree <= 360; degree += 15) {
            if (degree % 30 == 0 || degree % 45 == 0) {
            	BigNum d = new BigNum(degree);
            	BigNum radian = MathBn.toRadians(d);
            	BigNum d1     = MathBn.sin(radian);
                double d2     = Math.sin(radian.toDouble());
                System.out.println(String.format("%3d : %s - %13.10f = %13.10f", degree, d1, d2, d1.toDouble() - d2));
                System.out.println("****************************************************************");
            }
        }
	}
	public void testMultiply() {
		BigNum a = new BigNum("0.25");
		BigNum b = a.multiply(BigNum.PI);
		System.out.println("0.25 * PI =" + b);
		BigNum n4 = new BigNum("4.0");
		BigNum c = BigNum.PI.divide(n4, CalcultorConts.DECIMAL_LEN, BigNumRound.HALF_EVENT);
		System.out.println(c);
		a = new BigNum("0.5");
		BigNum d = a.multiply(BigNum.PI);
		System.out.println("0.5 * PI =" + d);
	}
	public void testAdd() {
		BigNum s = BigNum.PI.add(BigNum.PI);
		System.out.println("2pi=" + s);
	}
//    public static BigNum pi = new BigNum("3.14159265358979323846264338327950288419716939937510");
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
    	BigNumTest proc = new BigNumTest();
//    	proc.testbyte();
////    	proc.testshort();
////    	proc.testint();
////    	proc.testlong();
//    	proc.testSubtract();
//    	proc.print99();
//    	proc.testMultiply();
//    	proc.testDivide();
////    	proc.test003();
//    	proc.testCmp();
//    	proc.testMod();
//    	proc.testDouble();
//    	proc.testRD();
//    	proc.testRound();
//    	proc.testToRadians();
//    	proc.testSubtract();
//    	proc.testAdd();
    	proc.testSin();

//        System.out.println("byte" + Byte.MIN_VALUE + "...＋" + Byte.MAX_VALUE); // 8
//        System.out.println("short" + Short.MIN_VALUE + "...＋" + Short.MAX_VALUE); // 16
//        System.out.println("int" + Integer.MIN_VALUE + "...＋" + Integer.MAX_VALUE); // 32
//        System.out.println("long" + Long.MIN_VALUE + "...＋" + Long.MAX_VALUE); // 64
//        System.out.println(BigNumRound.UP.ordinal());

//        BigNum test1 = new BigNum("0");
//        System.out.println("---------------");
//        BigNum test2 = new BigNum("1");
//        System.out.println("---------------");
//        BigNum test3 = new BigNum("9");
//        System.out.println("---------------");
//        BigNum test4 = new BigNum("10");
//        for(int i = 0; i <=100; i ++) {
//            // System.out.println("---------------");
//            BigNum t = new BigNum("" + i);
//        }

//        BigNum test1 = new BigNum("1");
//        BigNum test2 = new BigNum("2");
//        BigNum test3 = test1.add(test2);
//        System.out.println(test3);
//    	BigNum test40 = new BigNum("40");
//    	System.out.println(test40.abs() + "," + test40.toByte());
//      	BigNum test4 = new BigNum("4");
//      	System.out.println(test4.abs());
//      	if (test40.abs().compareTo(test4.abs()) < 0) {
//      		System.out.println("<");
//      	}
//      	BigNum test36 = test40.subtract(test4);
//      	System.out.println(test36);
//        BigNum test20 = new BigNum("2.0");
//        BigNum test12 = test1.multiply(test2);
//        System.out.println(test12);

//        BigNum test3 = new BigNum("3");
//        BigNum test203 = test2.multiply(test3);
//        System.out.println("2=" + test2 + ",*3=" + test3 + ",=" + test203);
//        BigNum test4 = new BigNum("4");
//
//        BigNum test43 = test4.subtract(test3);
//        System.out.println(test43);
//        System.out.println("---------------");
//        BigNum test4 = new BigNum("126.0257");
//        System.out.println(test4.toString());
//        System.out.println("---------------");
//        BigNum test5 = new BigNum("37");
//        System.out.println(test5.toString());
//        System.out.println("---------------");
//        BigNum test6 = new BigNum("+655.36");
//        System.out.println("---------------");
//        BigNum test7 = new BigNum("65537");
//        System.out.println("---------------");
//        BigNum test8 = test4.add(test5);
//        System.out.println(test8.toString());
//        System.out.println("---------------");
//        BigNum test9 = test4.multiply(test5);
//        System.out.println(test9.toString());
//        System.out.println("---------------");
//        System.out.println(test4.compareTo(test5));
//        System.out.println("---------------");
//        BigNum test9 = test4.divide(test5, 0, 0);
//        System.out.println(test9.toString());
//        System.out.println("---------------");
//        System.out.println(new BigNum("0.01260257").divide(test5, 0, 0));
//        System.out.println(new BigNum("0.1260257").divide(test5, 0, 0));
//        System.out.println(new BigNum("1.260257").divide(test5, 0, 0));
//        System.out.println("---------------");
//        System.out.println(new BigNum("12.60257").divide(test5, 0, 0));
//        System.out.println("---------------");
//        System.out.println(new BigNum("126.0257").divide(test5, 0, 0));
//        System.out.println(new BigNum("1260.257").divide(test5, 0, 0));
//        System.out.println(new BigNum("12602.57").divide(test5, 0, 0));
//        System.out.println(new BigNum("126025.7").divide(test5, 0, 0));
//        System.out.println(new BigNum("1260257.0").divide(test5, 0, 0));
//        System.out.println(new BigNum("12602570.0").divide(test5, 0, 0));
//
//        char[][] a = new char[2][3];
//        a[0][0] = 'a';
//        a[0][1] = 'b';
//        a[0][2] = 'c';
//        a[1][0] = 'A';
//        a[1][1] = 'B';
//        a[1][2] = 'C';
//
//        for(int y = 0; y < a[0].length; y ++) {
//            //System.out.print("y=" + y + "a=" + a.length + "a[0]=" + a[0].length);
//            for (int x = 0; x < a.length; x ++) {
//                System.out.print("a[" + x + "][" + y + "]=" + a[x][y]);
//            }
//            System.out.println();
//        }
//        System.out.println("End.");

//        System.out.println(new BigNum("10").divide(new BigNum("3"), 1, 0));

//        BigNum test121p = new BigNum("+12.0");
//        System.out.println(test121p);
//        BigNum test120p = new BigNum("+12.");
//        System.out.println(test120p);
//        BigNum test12p = new BigNum("+12");
//        System.out.println(test12p);
//        BigNum test121 = new BigNum("12.0");
//        System.out.println(test121);
//        BigNum test120 = new BigNum("12.");
//        System.out.println(test120);
//        BigNum test12 = new BigNum("12");
//        System.out.println(test12);
//        BigNum test121n = new BigNum("-12.0");
//        System.out.println(test121n);
//        BigNum test120n = new BigNum("-12.");
//        System.out.println(test120n);
//        BigNum test12n = new BigNum("-12");
//        System.out.println(test12n);

//        BigNum test23 = new BigNum("23");
//        System.out.println(test23);
//        BigNum value = test12.add(test23);
//        System.out.println(value);

//        // BigNum test744 = new BigNum("6006");
//        BigNum test744 = new BigNum("9223372036854775808");
//        // BigNum test744 = new BigNum("22222222222222222222");
//        BigNum test2744 = new BigNum("9223372036854775808");
//        System.out.println(test744.divide(test2744, 0, 0));
    }

}
/*
访问控制
private	私有的
protected	受保护的
public	公共的

类、方法和变量修饰符
abstract	声明抽象
class	类
extends	扩允,继承
final	终极,不可改变的
implements	实现
interface	接口
native	本地
new	新,创建
static	静态
strictfp	严格,精准
synchronized	线程,同步
transient	短暂
volatile	易失

程序控制语句
break	跳出循环
continue	继续
return	返回
do	运行
while	循环
if	如果
else	否则
for	循环
instanceof	实例
switch	根据值选择执行
case	定义一个值以供switch选择
default	默认

错误处理
assert	断言表达式是否为真
catch	捕捉异常
finally	有没有异常都执行
throw	抛出一个异常对象
throws	声明一个异常可能被抛出
try	捕获异常

包相关
import	引入
package	包

基本类型
boolean	布尔型
byte	字节型
char	字符型
double	双精度浮点
float	单精度浮点
int	整型
long	长整型
short	短整型
null	空

变量引用
super	父类,超类
this	本类
void	无返回值

保留关键字
goto	是关键字，但不能使用
*/