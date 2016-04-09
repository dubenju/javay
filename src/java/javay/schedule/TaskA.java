package javay.schedule;

import java.util.Date;
import java.util.TimerTask;

public class TaskA extends TimerTask {

	@Override
	public void run() {
//		System.out.println(DateUtil.formatFullDate(new Date()));
		System.out.println(new Date());
	}

}
