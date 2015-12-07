package javay.main;

public class TestMod {

	/**
	 * 对负数进行取模运算，其结果的符号总是与被除数的符号保持一致。
	 * truncate除法 && floor除法
	 * 在大多数编程语言中，如果整数a不是整 数b的整数倍数的话，那么a、b做除法产生的实际结果的小数部分将会被截除，这个过程称为截尾(truncation)。如果除法的结果是正数的话，那么 一般的编程语言都会把结果趋零截尾，也就是说，直接把商的小数部分去除。但是如果除法的结果是负数的话，不同的语言通常采用了两种不同的截尾方法:一种是 趋零截尾(truncate toward zero)，另一种是趋负无穷截尾(truncate toward negative infinity);相应的，两种除法分别被称为truncate除法和floor除法。
	 * 事实上，可以认为不管除法的结果是正是负，truncate除法都是趋零结尾;而floor除法都是趋负无穷结尾。
	 * 取模运算
	 * 取模运算实际上是计算两数相除以后的余数。假设q是a、b相除产生的商 (quotient)，r是相应的余数(remainder)，那么在几乎所有的计算系统中，都满足a=b*q+r，其中|r|<|a|。因此r有 两个选择，一个为正，一个为负;相应的，q也有两个选择。如果a、b都是正数的话，那么一般的编程语言中，r为正数;或者如果a、b都是负数的话，一般r 为负数。但是如果a、b一正一负的话，不同的语言则会根据除法的不同结果而使得r的结果也不同，并且一般r的计算方法都会满足r=a-(a/b)*b。
	 * (1)C/Java语言
	 * C/Java语言除法采用的是趋零截尾(事实上，C89 对于除数或被除数之一为负数情况的结果是未定义的;C99才正式确定了趋零截尾)，即truncate除法。它们的取模运算符是%，并且此运算符只接受整 型操作数。一个规律是，取模运算的结果的符号与第一个操作数的符号相同(或为0)。因此(-11)%5=-11-[(-11)/5]*5=-11-(-2)*5=-1。
	 * (2)C++语言
	 * C++语言的截尾方式取决于特定的机器。如果两个操作数均为正，那么取模运算的结果也为正数(或为0);如果两个操作数均为负数，那么取模运算的结果为负数(或为0);如果只有一个操作数为负数，那么取模运算的结果是取决于特定实现的。
	 * (3)Python语言
	 * Python语言除法采用的是趋负无穷截尾，即floor除法。它的取模运算符也是%，并且此运算符可以接受浮点操作数。一个类似的规律是，取模运算的结果的符号与第二个操作数的符号相同。因此(-11)%5=-11-[(-11)/5]*5=-11-(-3)*5=4。
	 * 这里需要注意的是，Python 3.x中”/”运算符的意义发生了变化，”/”产生的结果将不会再进行截尾;相应的”//”运算符的结果才会进行截尾。
	 * (4)Common Lisp
	 * Common Lisp的特殊操作符(special operator)”/”的结果是分数，因此不会存在截尾的问题。但是Common Lisp提供了TRUNCATE函数和FLOOR函数分别对应上述的两种除法。相应的，Common Lisp的REM函数类似于C/Java语言中的取模运算;而MOD函数类似于Python语言中的取模运算。
	 * 首先，我觉得任何一种严谨的语言都应该满足 a/b + a%b = a 这个表达式，这应该能解释为什么不同语言中出现不同规范。毕竟 -1/10=0、-1%10=9 这两个更容易被人接受的结果是不能相容的（或者说是不满足上面那个式子的）。想要使前一个成立就必须更改后一个式子的结果，反之亦然。二者不可兼得，否则就会出现a/b + a%b != a 这个数学上的错误结论。
	 * 接下来就是在编程过程中怎么去处理了。一种方式是在用之前先去搞清楚你所用的语言支持那一种规范。另一种我觉得更好一点，就是将负数转换为整数，避免这种潜在的不确定性。如果这样做更麻烦那就只能采取第一种方法啦。
	 */
	public void test() {
		byte b = 34;
		byte a = 3;
		int c = b % a;
		System.out.println(b + "%" + a + "=" + c);

		b = 34;
		a = -3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);

		b = -34;
		a = 3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = -34;
		a = -3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
	}
	public void test2() {
		double b = 3.4;
		double a = 0.3;
		double c = b % a;
		System.out.println(b + "%" + a + "=" + c);

		b = 3.4;
		a = -0.3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);

		b = -3.4;
		a = 0.3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = -3.4;
		a = -0.3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = 3.4;
		a = 0.3;
		c = b / a;
		System.out.println(b + "/" + a + "=" + c);
	}
	public void test3() {
		float b = 3.4f;
		float a = 0.3f;
		float c = b % a;
		System.out.println(b + "%" + a + "=" + c);

		b = 3.4f;
		a = -0.3f;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);

		b = -3.4f;
		a = 0.3f;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = -3.4f;
		a = -0.3f;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = 3.4f;
		a = 0.3f;
		c = b / a;
		System.out.println(b + "/" + a + "=" + c);
	}

	public void test4() {
		double b = 3.24;
		double a = 3;
		double c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = 6;
		a = 3.5;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = 3.24;
		a = 0.3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = 3.24;
		a = 0.03;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = 32.4;
		a = 3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = 324;
		a = 3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
	}
	public void test5() {
		long b = 324;
		long a = 300;
		long c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = 60;
		a = 35;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = 34;
		a = 3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		b = 324;
		a = 30;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
		
		
		b = 324;
		a = 3;
		c = b % a;
		System.out.println(b + "%" + a + "=" + c);
	}
	public static void main(String[] args) {
		TestMod proc = new TestMod();
		proc.test();
		proc.test2();
		proc.test3();
		proc.test4();
		proc.test5();
	}

}
