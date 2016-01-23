/**
 *
 */
package javay.test.util;

/**
 * @author DBJ
 *
 */
public class PriorityQueueModel {
  private int id;
  private String name;

  public PriorityQueueModel(int i, String n){
    this.id = i;
    this.name = n;
    System.out.println("创建 " + n);
  }

  public int getId() {
    return id;
  }
  public void setId(int i) {
    this.id = i;
  }

  public String getName() {
    return name;
  }
}
