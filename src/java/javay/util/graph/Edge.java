/**
 *
 */
package javay.util.graph;

/**
 * 边/弧
 * @author DBJ(dubenju@126.com)
 */
public interface Edge {
  public int getX1();
  public void setX1(int x);
  public int getY1();
  public void setY1(int y);
  public int getX2();
  public void setX2(int x2);
  public int getY2();
  public void setY2(int y2);
  public Vertex getFrom();
  public Vertex getTo();
  public int getWeight();
  public DefaultEdge getNextEdge();
  public void setNextEdge(Edge nextEdge);
}
