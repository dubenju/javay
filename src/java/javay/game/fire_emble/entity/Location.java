package javay.game.fire_emble.entity;

public class Location {

    private int x;
    private int y;
    private int movedSteps; //g值
    private int evalRemainSteps; //h值
    private int totalEvalSteps; //f值
    private Location previous;

    public Location(int x,int y){
        this.x = x;
        this.y = y;
    }

    public Location(Location location){
        this.x = location.x;
        this.y = location.y;
        this.movedSteps = location.movedSteps;
        this.evalRemainSteps = location.evalRemainSteps;
        this.totalEvalSteps = location.totalEvalSteps;
        this.previous = location.previous;
    }

    public boolean equals(Object o){
        if(o instanceof Location){
            Location l = (Location) o;
            if(l.x == this.x && l.y == this.y){
                return true;
            }
        }
        return false;
    }

    public String toString(){
        String desc = "["+x+","+y+"]<" + this.movedSteps + "+" + this.evalRemainSteps + "=" + this.totalEvalSteps + ">";
        return desc;
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

    public int getMovedSteps() {
        return movedSteps;
    }

    public void setMovedSteps(int movedSteps) {
        this.movedSteps = movedSteps;
    }

    public int getEvalRemainSteps() {
        return evalRemainSteps;
    }

    public void setEvalRemainSteps(int evalRemainSteps) {
        this.evalRemainSteps = evalRemainSteps;
    }

    public int getTotalEvalSteps() {
        return totalEvalSteps;
    }

    public void setTotalEvalSteps(int totalEvalSteps) {
        this.totalEvalSteps = totalEvalSteps;
    }

    public Location getPrevious() {
        return previous;
    }

    public void setPrevious(Location previous) {
        this.previous = previous;
    }
}
