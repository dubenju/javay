/**
 *
 */
package javay.util.graph.x;

/**
 * @author 本挙
 *
 */
public class PTreeNode {
  int parentIndex = -1;
  int numChildren = 0;//only make sense in root

  void setParent(int i) {
    parentIndex=i;
  }
}
