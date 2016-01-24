package javay.util.graph;

/**
 * 距离
 * @author DBJ(dubenju@126.com)
 */
public class Distance implements Comparable<Distance> {
  public int preV;
  public int curV;
  public int distance;

  /**
   * Distance.
   * @param ov int
   */
  public Distance(int ov) {
    this.curV = ov;
    this.preV = -1;
    this.distance = Integer.MAX_VALUE;
  }

  public int compareTo(Distance od) {
    return distance - od.distance;
  }
}
