package javay.test.java;

public class TestStack {
	private int count = 0;
	public void recursion() {
		count ++;
		recursion();
	}

	public static void main(String[] args) {
		TestStack proc = new TestStack();
		try {
			proc.recursion();
		} catch (Throwable e) {
			System.out.println("deep of stack is " + proc.count);
			e.printStackTrace();
		}
	}

}
