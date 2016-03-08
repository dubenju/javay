package javay.test.java;

/*
 * 用移位运算代替乘除法能够显著提高运算效率

比如运算2乘以8
a=2*8；
可以写为 
a= 2<<3,
表示2左移三位
另外 整数不是2的幂的数也可以利用这种方式
比如
b=b*9
可以看做b=b*（8+1）=b*8+b
从而 b=b<<3 +b
从而
b=b*7改为 b=b<<3-b

java中有三种移位运算符

<<      :     左移运算符，num << 1,相当于num乘以2

>>      :     右移运算符，num >> 1,相当于num除以2

>>>    :     无符号右移，忽略符号位，空位都以0补齐
忽略了符号位扩展，0补最高位  无符号右移运算符>>> 只是对32位和64位的值有意义


任意给定一个32位无符号整数n，求其对应的二进制数长度。先举几个例子解释一下什么是二进制数的长度，比如8 = 1000，则长度是4， 7 = 0111，长度为3。所以一个二进制数的长度也即最高位1的下标值+1（下标从0开始）。题目很简单，下面提供三种方法

普通方法
递归法
二分搜索+查表
 */
public class TestShift {

	public static void main(String[] args) {
		TestShiftProc proc = new TestShiftProc();
		proc.testa();
		proc.testb();
	}

}
class TestShiftProc {
	
	public static int MAX_CNT = 100000000;
	public void testa() {
		int a = 25 , b = 3;
		long st = System.currentTimeMillis();
		int c = a;
		for (int i = 0; i < MAX_CNT; i ++) {
			a = c % b;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int mod" + ed + "ms");
	}
	public void testb() {
		int a = 25 , b = 3;
		long st = System.currentTimeMillis();
		int c = a;
		int d = 0;
		for (int i = 0; i < MAX_CNT; i ++) {
			d = (c / b) * b;
			a = c - d;
		}
		long ed = System.currentTimeMillis() - st;
		System.out.println(a + "int mod" + ed + "ms");
	}
}
