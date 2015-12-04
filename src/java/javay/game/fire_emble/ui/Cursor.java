package javay.game.fire_emble.ui;

import java.awt.Graphics2D;
import java.awt.Image;

import javay.game.fire_emble.util.Consts;
import javay.game.fire_emble.util.ImageManager;

public class Cursor {

    private int x;
    private int y;
    private Image img;
    private boolean chosen; //角色是否被选中


    public Cursor(int x,int y){
        this.x = x;
        this.y = y;
        img = ImageManager.getInstance().getCursor();
    }

    public void drawSelf(Graphics2D g2d) {
        // int offset = Consts.HCS;
    	int offset = 0;
        int horizon = x * Consts.HCS + offset;
        int vertical = y * Consts.VCS;

        g2d.drawImage(img,horizon,vertical, null);
    }

    public void move(int direction){
        switch(direction){
            case 1:
                x += 1;
                return;
            case -1:
                x -= 1;
                return;
            case 2:
                y += 1;
                return;
            case -2:
                y -= 1;
                return;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
}
