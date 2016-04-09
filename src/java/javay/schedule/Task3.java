package javay.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Task3 {

	public static void main(String[] args) {
	    Runnable runnable = new Runnable() {
	        public void run() {
//	          long a = System.currentTimeMillis(), b = 0;
	          // task to run goes here
	          System.out.println("Hello !!");
	        }
	    };
	    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
	}

}
