package javay.test.java;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ClockAccuracyTest1 {
    public static void main(String args[]) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
//        int size = 4000000;
        int size = 10;

        // create an array to hold millisecond times
        // and loop to capture them
        long times[] = new long[size];
        for (int i = 0; i < size; i++) {
            times[i] = System.nanoTime();
        }

        // now display the unique times
        long time = times[0];
        long previousTime = times[0];
        long count = 0;
        Set<Long> deltas = new HashSet<Long>();
        long delta = 0;
        long minDelta = Long.MAX_VALUE;
        long maxDelta = Long.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (times[i] > time) {
                delta = time - previousTime;
                deltas.add(delta);
                if (delta > 0 && delta < minDelta) {
                    minDelta = delta;
                } else if (delta > maxDelta) {
                    maxDelta = delta;
                }

                System.out.print("raw=");
                System.out.print(time);
                System.out.print(" | formatted=");
                System.out.print(formatter.format(new Date(time)));
                System.out.print(" | frequency=");
                System.out.print(count);
                System.out.print(" | delta=");
                System.out.print(delta);
                System.out.println("ns");

                previousTime = time;
                time = times[i];
                count = 0;
            } else {
                count++;
            }
        }

        System.out.println("");
        System.out.println("Minimum delta : " + minDelta + "ns");
        System.out.println("Maximum delta : " + maxDelta + "ns");

    }
}
