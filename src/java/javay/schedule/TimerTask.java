package javay.schedule;

import java.util.Timer;

public class TimerTask {

	public static void main(String[] args) {
        TaskA taskA = new TaskA();
        Timer timer = new Timer();
        timer.schedule(taskA, 5 * 1000, 5 * 1000);

        //Date date = DateUtil.parse("2014-12-04 16:50:00");
        //timer.schedule(taskA, date , 5 * 1000);
        //timer.scheduleAtFixedRate(taskA, date, 5 * 1000);
	}

}
