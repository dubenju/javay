package javay.astar;

/**
 * A*'s Node
 * @author DBJ(dubenju@126.com)
 */
public class AStarNode {

  /**
   * x坐标.
   */
  private int x;

  /**
   * y坐标.
   */
  private int y;

  /**
   * g值 起始点到当前点的合计.
   */
  private int g;

  /**
   * h值 当前点到目标点的合计.
   */
  private int h;

  /**
   * f值=g+h.
   */
  private int f;

  /**
   * 父.
   */
  private AStarNode parent = null;

  /**
   * 地形.
   */
  private Terrain terrain = null;

  /**
   * 状态.
   */
  private int status;

  /**
   * 构造函数.
   * @param xn x坐标
   * @param yn y坐标
   * @param terrain 地形
   * @param status 状态
   */
  public AStarNode(int xn, int yn, int terrain) {
    this(xn, yn, terrain, AStarConstants.NOTE_STATUS_NONE);
  }

  /**
   * 构造函数.
   * @param xn x坐标
   * @param yn y坐标
   * @param terrain 地形
   * @param status 状态
   */
  public AStarNode(int xn, int yn, int terrain, int status) {
    this.x = xn;
    this.y = yn;
    this.terrain = new Terrain(terrain);
    this.status = status;
  }

  /**
   * AStarNode.
   * @param xn x坐标
   * @param yn y坐标
   * @param parent 父
   */
  public AStarNode(int xn, int yn, AStarNode parent) {
    this.x = xn;
    this.y = yn;
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

  public void setF(int fn) {
    this.f = fn;
  }

  public int getG() {
    return g;
  }

  public void setG(int gn) {
    this.g = gn;
  }

  public int getH() {
    return h;
  }

  public void setH(int hn) {
    this.h = hn;
  }

  public AStarNode getParent() {
    return parent;
  }

  public void setParent(AStarNode parent) {
    this.parent = parent;
  }

  /**
   * getTerrain.
   * @return terrain
   */
  public Terrain getTerrain() {
    return terrain;
  }

  /**
   * setTerrain.
   * @param terrain Terrain
   */
  public void setTerrain(Terrain terrain) {
    this.terrain = terrain;
  }

  /**
   * getStatus.
   * @return status int
   */
  public int getStatus() {
    return status;
  }

  /**
   * setStatus.
   * @param status int
   */
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * 在contains的indexOf中使用.
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    boolean blRes = false;
    if (obj instanceof AStarNode) {
      blRes = (this.x == ((AStarNode) obj).x && this.y == ((AStarNode) obj).y);
    }
    return blRes;
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
