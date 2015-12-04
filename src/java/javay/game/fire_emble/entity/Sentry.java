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

public class Sentry {
	private Location from;
	private Location to;
	private boolean bFlag = true;
    private List<Location> routes;
    private Image img;
    private int x; //以像素为单位
    private int y;
    private int[][] map;

	public Sentry(int[][] map, Location from, Location to) {
		this.from = from;
		this.to = to;

    	AStar apf = new AStar(map);
    	List<AStarNode> path = apf.find(from.getX(), from.getY(), to.getX(), to.getY());
    	this.routes = new ArrayList<Location>();
    	Location par = null;
    	for (AStarNode nd : path) {
    		Location l = new Location(nd.getX(), nd.getY());
    		l.setMovedSteps(nd.getG());
    		l.setEvalRemainSteps(nd.getH());
    		l.setTotalEvalSteps(nd.getF());
    		l.setPrevious(par);
    		this.routes.add(l);
    		par = l;
    	}
    	img = ImageManager.getInstance().getArmy();
        x = from.getX() * Consts.HCS; // + Consts.HCS;
        y = from.getY() * Consts.VCS;
        this.map = map;
	}

    public void drawSelf(Graphics2D g2d) {

        g2d.drawImage(img, x, y, null);
        Color col = g2d.getColor();
        g2d.setColor(Color.GRAY);
        g2d.drawRect(x, y, 30, 30);
        g2d.setColor(col);

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
    public void moveForward(){
    	while(true) {
	    	int index = routes.size() - 1;
		    Location loc = null;
		    for(; index >= 0; index --) {
		        loc = routes.get(index);
		        // TODO:moving
		        this.setLocationX(loc.getX());
		        this.setLocationY(loc.getY());
		        System.out.println("locationA:" + loc + (this.routes.size() + 1) + "(" + this.x + "," + this.y + ")(" + this.to.getX() * Consts.VCS + "," + this.to.getY() * Consts.VCS + ")");
		    }
		    for(index = 0; index < routes.size(); index ++) {
		        loc = routes.get(index);
		        // TODO:moving
		        this.setLocationX(loc.getX());
		        this.setLocationY(loc.getY());
		        System.out.println("locationB:" + loc + (this.routes.size() + 1) + "(" + this.x + "," + this.y + ")(" + this.from.getX() * Consts.VCS + "," + this.from.getY() * Consts.VCS + ")");
		    }
    	}
	}

	public void setLocationY(int nextY){
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

}
