package javay.test.thread;

public class TestThread extends Thread{

	public static void main(String[] args) {
		TestThread t = new TestThread();
		t.start();
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
//		super.run();
		System.out.println(this.getId());
		System.out.println(this.getName());
		System.out.println("优先级:" + this.getPriority());
		System.out.println(this.getState());
	}

}
