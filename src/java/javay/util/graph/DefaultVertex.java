package javay.util.graph;

import java.util.LinkedList;

/**
 * 顶点
 * @author DBJ(dubenju@126.com)
 */
public class DefaultVertex implements Vertex {

	private int x;
	private int y;
	private int index;
	private Object value;
	private boolean visit;

	private LinkedList<Edge> adjacencyEdges;

	public DefaultVertex(Object v) {
		this.index = -1;
		this.value = v;
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
			return this.index > -1 && this.index == ((DefaultVertex) obj).index ? true : this.value.equals(((DefaultVertex) obj).value);
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
		buf.append(this.x);
		buf.append(",");
		buf.append(this.y);
		buf.append(")");
		return buf.toString();
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return adjacencyEdges
	 */
	public LinkedList<Edge> getAdjacencyEdges() {
		return adjacencyEdges;
	}

	/**
	 * @param adjacencyEdges セットする adjacencyEdges
	 */
	public void setAdjacencyEdges(LinkedList<Edge> adjacencyEdges) {
		this.adjacencyEdges = adjacencyEdges;
	}

	/**
	 * @return visit
	 */
	public boolean isVisited() {
		return visit;
	}

	/**
	 * @param visit
	 */
	public void setVisit(boolean visit) {
		this.visit = visit;
	}

	/**
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
}
