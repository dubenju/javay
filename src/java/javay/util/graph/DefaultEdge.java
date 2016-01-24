package javay.util.graph;

/**
 * 边/弧
 * 在图论中，边（Edge，Line）是两个事物间某种特定关系的抽象化。两个事物间有联系，则这两个事物代表的顶点间就连有边，用一根直线或曲线表示。
 * @author DBJ(dubenju@126.com)
 */
public class DefaultEdge implements Edge, Comparable<DefaultEdge> {
  // TODO:Del
  public static final DefaultEdge NullEdge = new DefaultEdge();

  /** . */
  private int xi;
  private int yi;
  private int x2;
  private int y2;
  /** 起始顶点. */
  private Vertex from;
  /** 终止顶点. */
  private Vertex to;
  /** 权. */
  private int weight;

  private DefaultEdge nextEdge;

  public DefaultEdge() {
    weight = Integer.MAX_VALUE;
  }

  /**
   * 边.
   * @param from 起始顶点
   * @param to 终止顶点
   * @param weight 权
   */
  DefaultEdge(Vertex from, Vertex to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public int compareTo(DefaultEdge oe) {
    return weight - oe.weight;
  }

  /**
   * toString.
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append(from);
    buf.append("⇒");
    buf.append(to);
    buf.append("：");
    buf.append(weight);
    return buf.toString();
  }

  /**
   * getFrom.
   * @return from
   */
  public Vertex getFrom() {
    return from;
  }

  /**
   * getTo.
   * @return to
   */
  public Vertex getTo() {
    return to;
  }

  /**
   * getNextEdge.
   * @return nextEdge
   */
  public DefaultEdge getNextEdge() {
    return nextEdge;
  }

  /**
   * setNextEdge.
   * @param nextEdge Edge
   */
  public void setNextEdge(Edge nextEdge) {
    this.nextEdge = (DefaultEdge) nextEdge;
  }

  /**
   * getX1.
   * @return x
   */
  public int getX1() {
    return xi;
  }

  /**
   * setX1.
   * @param x1 int
   */
  public void setX1(int x1) {
    this.xi = x1;
  }

  /**
   * getY1.
   * @return y
   */
  public int getY1() {
    return yi;
  }

  /**
   * setY1.
   * @param y1 int
   */
  public void setY1(int y1) {
    this.yi = y1;
  }

  /**
   * getX2.
   * @return x2
   */
  public int getX2() {
    return x2;
  }

  /**
   * setX2.
   * @param x2 int
   */
  public void setX2(int x2) {
    this.x2 = x2;
  }

  /**
   * getY2.
   * @return y2
   */
  public int getY2() {
    return y2;
  }

  /**
   * getY2.
   * @param y2 int
   */
  public void setY2(int y2) {
    this.y2 = y2;
  }
}
