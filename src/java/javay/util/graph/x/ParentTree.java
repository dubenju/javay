/**
 *
 */
package javay.util.graph.x;

/**
 * @author 本挙
 *
 */
public class ParentTree {

  PTreeNode[] nodes;
  int numPartions;
  public ParentTree(int numNodes) {
    nodes=new PTreeNode[numNodes];
    numPartions=numNodes;
    for(int i=0;i<numNodes;i++) {
      nodes[i]=new PTreeNode();
      nodes[i].parentIndex=-1;//means root
    }
  }

  /**
   * use path compress
   * @param i
   * @return
   */
  public int find(int i) {
    if(nodes[i].parentIndex==-1) {
      return i;
    } else {
      nodes[i].setParent(find(nodes[i].parentIndex));//compress the path
      return nodes[i].parentIndex;
    }
  }

  public int numPartions() {
    return numPartions;
  }
  public boolean union(int i,int j) {
    int pi=find(i);
    int pj=find(j);
    if(pi!=pj) {
      if(nodes[pi].numChildren>nodes[pj].numChildren) {
        nodes[pj].setParent(pi);
        nodes[pj].numChildren+=nodes[pi].numChildren;
        nodes[pi].numChildren=0;

      } else {
        nodes[pi].setParent(pj);
        nodes[pi].numChildren+=nodes[pj].numChildren;
        nodes[pj].numChildren=0;
      }
      numPartions--;
      return true;
    }
    return false;
  }
}
