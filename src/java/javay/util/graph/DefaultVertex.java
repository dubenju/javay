package javay.util.graph;

import java.util.LinkedList;

/**
 * 顶点
 * @author DBJ(dubenju@126.com)
 */
public class DefaultVertex implements Vertex {

  private int xi;
  private int yi;
  private int index;
  private Object value;
  private boolean visit;

  private LinkedList<Edge> adjacencyEdges;

  /**
   * DefaultVertex.
   * @param vo Object
   */
  public DefaultVertex(Object vo) {
    this.index = -1;
    this.value = vo;
    this.visit = false;
    this.adjacencyEdges = new LinkedList<Edge>();
  }

  @Override
  public int getIndex() {
    return this.index;
  }

  @Override
  public Object getValue() {
    return this.value;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DefaultVertex) {
      return this.index > -1 && this.index == ((DefaultVertex) obj).index
          ? true : this.value.equals(((DefaultVertex) obj).value);
    }
    return super.equals(obj);
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append(this.index);
    buf.append(":");
    buf.append(this.value);
    buf.append(":");
    buf.append(this.visit);
    buf.append("(");
    buf.append(this.xi);
    buf.append(",");
    buf.append(this.yi);
    buf.append(")");
    return buf.toString();
  }

  @Override
  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * getAdjacencyEdges.
   * @return adjacencyEdges
   */
  public LinkedList<Edge> getAdjacencyEdges() {
    return adjacencyEdges;
  }

  /**
   * setAdjacencyEdges.
   * @param adjacencyEdges セットする adjacencyEdges
   */
  public void setAdjacencyEdges(LinkedList<Edge> adjacencyEdges) {
    this.adjacencyEdges = adjacencyEdges;
  }

  /**
   * isVisited.
   * @return visit
   */
  public boolean isVisited() {
    return visit;
  }

  /**
   * setVisit.
   * @param visit boolean
   */
  public void setVisit(boolean visit) {
    this.visit = visit;
  }

  /**
   * getX.
   * @return x
   */
  public int getX() {
    return xi;
  }

  /**
   * setX.
   * @param x int
   */
  public void setX(int xi) {
    this.xi = xi;
  }

  /**
   * getY.
   * @return y
   */
  public int getY() {
    return yi;
  }

  /**
   * setY.
   * @param y int
   */
  public void setY(int yi) {
    this.yi = yi;
  }
}
