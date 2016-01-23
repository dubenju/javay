/**
 *
 */
package javay.util.graph;

/**
 * @author @author DBJ(dubenju@126.com)
 */
public interface GraphVisitor {

  public void visit(Graph g, Edge edge, Vertex vertex);
}
