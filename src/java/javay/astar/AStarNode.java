/**
 * A star
 */
package javay.astar;

/**
 * A*'s Node
 * @author DBJ(dubenju@126.com)
 */
public class AStarNode {

    /**
     * x坐标
     */
    private int x;

    /**
     * y坐标
     */
    private int y;

    /**
     * g值 起始点到当前点的合计
     */
    private int g;

    /**
     * h值 当前点到目标点的合计
     */
    private int h;

    /**
     * f值=g+h
     */
    private int f;

    /**
     * 父
     */
    private AStarNode parent = null;

    /**
     * 地形
     */
    private Terrain terrain = null;

    /**
     * 状态
     */
    private int status;

    /**
     * 构造函数
     * @param x x坐标
     * @param y y坐标
     * @param terrain 地形
     * @param status 状态
     */
    public AStarNode(int x, int y, int terrain) {
        this(x, y, terrain, AStarConstants.NOTE_STATUS_NONE);
    }

    /**
     * 构造函数
     * @param x x坐标
     * @param y y坐标
     * @param terrain 地形
     * @param status 状态
     */
    public AStarNode(int x, int y, int terrain, int status) {
        this.x = x;
        this.y = y;
        this.terrain = new Terrain(terrain);
        this.status = status;
    }

    /**
     * AStarNode
     * @param x x坐标
     * @param y y坐标
     * @param parent 父
     */
    public AStarNode(int x, int y, AStarNode parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public AStarNode getParent() {
        return parent;
    }

    public void setParent(AStarNode parent) {
        this.parent = parent;
    }

    /**
     * @return terrain
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * @param terrain
     */
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    /**
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 在contains的indexOf中使用
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        boolean bRes = false;
        if (obj instanceof AStarNode) {
            bRes = (this.x == ((AStarNode) obj).x && this.y == ((AStarNode) obj).y);
        }
        return bRes;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("(");
        buf.append(this.getX());
        buf.append(",");
        buf.append(this.getY());
        buf.append(")<G:");
        buf.append(this.getG());
        buf.append("+H:");
        buf.append(this.getH());
        buf.append("=F:");
        buf.append(this.getF());
        buf.append(">S=[");
        buf.append(this.getStatus());
        buf.append("]");
        return buf.toString();
    }
}
