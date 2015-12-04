package javay.game.fire_emble.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javay.game.fire_emble.util.Consts;
import javay.game.fire_emble.util.ImageManager;
import javay.astar.AStar;
import javay.astar.AStarNode;

public class Character {

    private Location lo;
    private List<Location> routes;
    private Image img;
    private boolean blink; // true:闪烁
    private int count;
    private int x; //以像素为单位
    private int y;

    public Character(Location lo){
        this.lo = lo;
        this.routes = new ArrayList<Location>();
        this.routes.add(new Location(this.lo));

        img = ImageManager.getInstance().getArmy();
        x = lo.getX() * Consts.HCS; // + Consts.HCS;
        y = lo.getY() * Consts.VCS;
    }

    public void drawSelf(Graphics2D g2d) {

        if(!blink) {
            g2d.drawImage(img, x, y, null);
            Color col = g2d.getColor();
            g2d.setColor(Color.GRAY);
            g2d.drawRect(x, y, 30, 30);
            g2d.setColor(col);
        } else {
            int times = 10;
        	if(count <= times){
                g2d.drawImage(img, x, y, null);
            }
            count ++;
            if(count == times * 2){
                count = 0;
            }
        }
        drawRoute(g2d);
    }

    private void drawRoute(Graphics2D g2d) {
    	if (this.routes.size() >= 2) {
    		int i = 0;
    		Location pt1 = this.routes.get(i);
    		for(i ++; i < this.routes.size(); i ++) {
    			Location pt2 = this.routes.get(i);
    			// do something.
    			Color col = g2d.getColor();
    			Stroke stroke = g2d.getStroke();
    		    float lineWidth = 3.0f;
    		    g2d.setStroke(new BasicStroke(lineWidth));
    			g2d.setColor(Color.YELLOW);
    			g2d.drawLine(pt1.getX()  * Consts.VCS + Consts.HCS * 1 / 2, pt1.getY()  * Consts.VCS + Consts.HCS * 1 / 2, pt2.getX()  * Consts.VCS + Consts.HCS * 1 / 2, pt2.getY() * Consts.VCS+ Consts.HCS * 1 / 2);
    			g2d.setStroke(stroke);
    			g2d.setColor(col);
    			pt1 = pt2;
    		}
    	}
    }

    public void moveForward(int[][] map, Location dest){
    	AStar apf = new AStar(map);
    	List<AStarNode> path = apf.find(this.getLo().getX(), this.getLo().getY(), dest.getX(), dest.getY());
    	List<Location> paths = new ArrayList<Location>();
    	Location par = null;
    	for (AStarNode nd : path) {
    		Location l = new Location(nd.getX(), nd.getY());
    		l.setMovedSteps(nd.getG());
    		l.setEvalRemainSteps(nd.getH());
    		l.setTotalEvalSteps(nd.getF());
    		l.setPrevious(par);
    		paths.add(l);
    		par = l;
    	}

        int index = paths.size() - 1;
        Location loc = null;
        //System.out.println("---------");
        for(; index >= 0; index --) {
            loc = paths.get(index);

            System.out.println("location:" + loc + (this.routes.size() + 1));
            // TODO:moving
            this.setLocationX(loc.getX());
            this.setLocationY(loc.getY());

            this.routes.add(new Location(this.lo));
        }
    }

    public void setLocationY(int nextY){
        lo.setY(nextY);
        int yPixel = nextY * Consts.VCS;
        if(yPixel >= y){
            while(y < yPixel){
                y += 2;
                try{
                    Thread.sleep(20);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        } else {
            while(y > yPixel) {
                y -= 2;
                try{
                    Thread.sleep(20);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void setLocationX(int nextX){
        lo.setX(nextX);
        int xPixel = nextX * Consts.VCS ;// + Consts.HCS;
        if(xPixel >= x){
            while(x < xPixel){
                x += 2;
                try{
                    Thread.sleep(20);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        } else {
            while(x > xPixel){
                x -= 2;
                try{
                    Thread.sleep(20);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public Location getLo() {
        return lo;
    }

    public void setLo(Location lo) {
        this.lo = lo;
    }

    public void setBlink(boolean blink) {
        this.blink = blink;
    }
}
