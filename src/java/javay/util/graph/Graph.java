/**
 *
 */
package javay.util.graph;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 图
 * @author DBJ(dubenju@126.com)
 */
public interface Graph {
  public ArrayList<Vertex> getVertexes();
  public ArrayList<LinkedList<Edge>> getAdjacencyList();
  public int size();
//  public int edgeNum();
  public boolean isEdge(Edge edge);
  public void setEdge(Vertex from,Vertex to, int weight);
  /**
   * 返回指定节点的边的链表里存的第一条边
   * @param vertex
   * @return
   */
  public Edge firstEdge(Vertex vertex);

  /**
   * 深度优先遍历
   * @param visitor
   */
  public void depthFirstTraversal(GraphVisitor visitor);
  /**
   * 广度优先遍历
   * @param visitor
   */
  public void breathFirstTraversal(GraphVisitor visitor);
  public boolean kosaraju(GraphVisitor visitor);
  public LinkedList<LinkedList<Integer>> tarjan(GraphVisitor visitor);
  public LinkedList<LinkedList<Integer>> Gabow(GraphVisitor visitor);

  public void topologySort(Vertex[] sortedVertexes);
  public void topologySort_byDFS(Vertex[] sortedVertexes);
}
