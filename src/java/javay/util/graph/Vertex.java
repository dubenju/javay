package javay.util.graph;

import java.util.LinkedList;

/**
 * 顶点
 * @author DBJ(dubenju@126.com)
 */
public interface Vertex {
  public int getIndex();

  public void setIndex(int index);

  public Object getValue();

  public void setAdjacencyEdges(LinkedList<Edge> adjacencyEdges);

  public boolean isVisited();

  public void setVisit(boolean visit);

  public void setX(int xi);

  public void setY(int yi);

  public int getX();

  public int getY();
}
