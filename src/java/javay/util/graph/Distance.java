/**
 *
 */
package javay.util.graph;

/**
 * 距离
 * @author DBJ(dubenju@126.com)
 */
public class Distance implements Comparable<Distance> {
  public int preV;
  public int curV;
  public int distance;

  public Distance(int v) {
    this.curV = v;
    this.preV = -1;
    this.distance = Integer.MAX_VALUE;
  }

  public int compareTo(Distance o) {
    return distance - o.distance;
  }
}
