package javay.game.fire_emble.listener;

import javay.game.fire_emble.ui.Cursor;
import javay.game.fire_emble.entity.Character;
import javay.game.fire_emble.entity.Location;
import javay.game.fire_emble.entity.Sentry;

public class MoveThread extends Thread {
    private Character c;
    private Location lo;
    private int[][] map;
    private Cursor cursor;
    private Sentry sentry;

    public MoveThread(Character c, Location lo, int[][] map, Cursor cursor) {
        this.c = c;
        this.lo = lo;
        this.map = map;
        this.cursor = cursor;
    }
    public MoveThread(Sentry c) {
    	sentry = c;
    }
    public void run(){
    	if (c != null) {
    		c.moveForward(map, lo);
    	}
        if (this.cursor != null) {
        	cursor.setChosen(false);
        }
    	if (sentry != null) {
    		sentry.moveForward();
    	}
    }
}
