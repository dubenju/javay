/**
 *
 */
package javay.test.util;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class PriorityQueueExample {

  // Comparator
  public static Comparator<PriorityQueueModel> idComparator = new Comparator<PriorityQueueModel>() {

    @Override
    public int compare(PriorityQueueModel c1, PriorityQueueModel c2) {
      return (int) (c1.getId() - c2.getId());
    }
  };

  public static void main(String[] args) {

    // 优先队列示例
    Queue<Integer> seqPriorityQueue = new PriorityQueue<>(7);
    Random rand = new Random();
    for(int i = 0; i < 7; i++) {
      seqPriorityQueue.add(new Integer(rand.nextInt(100)));
    }
    seqPriorityQueue.add(new Integer(0));
    seqPriorityQueue.add(new Integer(-1));

    int size = seqPriorityQueue.size(); // note : here
    for(int i = 0; i < size; i ++) {
      Integer in = seqPriorityQueue.poll(); // poll
      System.out.println("SEQ:" + in);
    }
    System.out.println("size=" + seqPriorityQueue.size() + "\n");

    // 优先队列使用示例
    Queue<PriorityQueueModel> 动车1号 = new PriorityQueue<>(10, idComparator);
    Random 检票机 = new Random();
    for(int i = 0; i < 7; i++) {
      int id = 检票机.nextInt(100000);
      动车1号.add(new PriorityQueueModel(id, "座号" + id));
    }
    System.out.println("\n乘车信息：");

    PriorityQueueModel mdl = null;
    int len = 动车1号.size();
    int i = 0;
    while(true){
      PriorityQueueModel model = 动车1号.peek(); // poll
      i ++;
      if(model == null || i > len) {
        break;
      }
      System.out.println(model.getName());
      mdl = model;
    }

    if (mdl != null) {
      System.out.println("------");
      mdl.setId(1);
      while(true){
        PriorityQueueModel model = 动车1号.poll(); // poll
        if(model == null) {
          break;
        }
        System.out.println(model.getName());
      }
    } else {
      System.out.println("null");
    }

  }
}
