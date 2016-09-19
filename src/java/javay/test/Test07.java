package javay.test;

public class Test07 {
	public static void main(String... args)  {

		try {

		try {

		throw new AaException("origin");

		} catch (AaException e) {

		Throwable se = new AaException("new");

		se.initCause(e);

		throw se;//eclipse这里报错，不知道为什么

		}

		} catch (Exception t) {

		System.out.print(t.getMessage());

		}

		}

}
