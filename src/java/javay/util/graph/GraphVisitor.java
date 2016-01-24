package javay.util.graph;

/**
 * @author @author DBJ(dubenju@126.com)
 */
public interface GraphVisitor {

  public void visit(Graph gr, Edge edge, Vertex vertex);
}
