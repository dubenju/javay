package javay.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TestList1 {

  public static final int N=50000;

  public static List<Integer> values;

  static {
    Integer vals[]=new Integer[N];

    Random r=new Random();

    for(int i=0,currval=0;i<N;i++) {
      vals[i] =new Integer(currval);
      currval+=r.nextInt(100)+1;
    }

    values=Arrays.asList(vals);
  }

  static long timeList(List<Integer> lst) {
    long start=System.currentTimeMillis();
    for(int i=0; i<N; i++) {
      int index=Collections.binarySearch(lst, values.get(i));
      if(index!=i) {
        System.out.println("***错误***");
      }
    }
    return System.currentTimeMillis()-start;
  }
  public static void main(String args[]) {
    System.out.println("ArrayList消耗时间："+timeList(new ArrayList<Integer>(values)));
    System.out.println("LinkedList消耗时间："+timeList(new LinkedList<Integer>(values)));
  }
}
