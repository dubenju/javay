package javay.schedule;

public class Task1 {

	public static void main(String[] args) {
		System.out.println(Long.MAX_VALUE);
		System.out.println("" + (12 * 31 * 24 * 60 * 60 * 1000));
		// run in a second
		final long timeInterval = 1000;
		Runnable runnable = new Runnable() {
		  public void run() {
		    long a = System.currentTimeMillis(), b = 0;
		    while (true) {
		      b = System.currentTimeMillis();
		      // ------- code for task to run
		      System.out.println("Hello !!" + "(" + ( b - a) + ")ms");
		      // ------- ends here
		      a = System.currentTimeMillis();
		      try {
		        Thread.sleep(timeInterval);
		      } catch (InterruptedException e) {
		        e.printStackTrace();
		      }
		    }
		  }
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
