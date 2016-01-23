package javay.test.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

public class TestIntersection {
  public boolean hasIntersectionByHashMap(String[] array1, String[] array2) {
    HashMap<String, String> map = new HashMap<String, String>();
    for(String str : array2) { // for each
      map.put(str, str);
    }
    for (String str : array1) { // for each
      if (map.get(str) != null) {
        return true;
      }
    }
    return false;
  }
  public boolean hasIntersectionByTreeMap(String[] array1, String[] array2) {
    TreeMap<String, String> map = new TreeMap<String, String>();
    for(String str : array2) { // for each
      map.put(str, str);
    }
    for (String str : array1) { // for each
      if (map.get(str) != null) {
        return true;
      }
    }
    return false;
  }
  public boolean hasIntersectionByIndexOf(String[] array1, String[] array2) {
    String map = Arrays.toString(array2); // for each
    for (String str : array1) { // for each
      if (map.indexOf("" + str + "") > -1) {
        // 有缺陷，必要时加特殊字符
        return true;
      }
    }
    return false;
  }
  public boolean hasIntersectionBySortBS(String[] array1, String[] array2) {
    Arrays.sort(array2); // for each
    for (String str : array1) { // for each
      if (Arrays.binarySearch(array2, str) > -1) {
        return true;
      }
    }
    return false;
  }
  public boolean hasIntersectionByPQueue(String[] array1, String[] array2) {
    PriorityQueue<String> queue = new PriorityQueue<String>(array2.length);
    for(String str : array2) { // for each
      queue.offer(str);
    }
    for (String str : array1) { // for each
      if (queue.contains(str)) { // for each
        return true;
      }
    }
    return false;
  }
//  public boolean hasIntersectionByPQueue2(String[] array1, String[] array2) {
//    PriorityQueue<String> queue = new PriorityQueue<String>(array2.length);
//    for(String str : array2) { // for each
//      queue.offer(str);
//    }
//    for (String str : array1) { // for each
//      if (Collections.binarySearch(queue, str) > -1) {
//        return true;
//      }
//    }
//    return false;
//  }
  public boolean hasIntersectionByTreeSet(String[] array1, String[] array2) {
    TreeSet<String> set = new TreeSet<String>();
    for(String str : array2) { // for each
      set.add(str);
    }
    for (String str : array1) { // for each
      if (set.contains(str)) { // for each
        return true;
      }
    }
    return false;
  }
  public boolean hasIntersectionByHashSet(String[] array1, String[] array2) {
    HashSet<String> set = new HashSet<String>();
    for(String str : array2) { // for each
      set.add(str);
    }
    for (String str : array1) { // for each
      if (set.contains(str)) {  // hash code loop
        return true;
      }
    }
    return false;
  }
  public boolean hasIntersectionByHashSet2(String[] array1, String[] array2) {
    HashSet<String> set = new HashSet<String>();
    HashSet<String> set1 = new HashSet<String>();
    for(String str : array2) { // for each
      set.add(str);
    }
    int sa2 = set.size();

    for (String str : array1) { // for each
      set1.add(str);
    }
    int sa1 = set1.size();
    set.addAll(set1);      // for each
    int sall = set.size();
    // System.out.println("@hasIntersectionByHashSet2[" + sa1 + "," + sa2 +"," + sall + "]");
    return sall == (sa1 + sa2) ? false : true;
  }

  public static void main(String[] args) {
    int nMax = 4000000;
    String[] b = new String[nMax];
    for (int i = 0; i < nMax ; i ++) {
      b[i] = String.valueOf(i);
    }

    String[] t = new String[1];
    t[0] = String.valueOf(nMax - 1) + "x";
    TestIntersection proc = new TestIntersection();

    long a = System.currentTimeMillis();
    System.out.println("HashMap:" + proc.hasIntersectionByHashMap(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("TreeMap:" + proc.hasIntersectionByTreeMap(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("IndexOf:" + proc.hasIntersectionByIndexOf(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("PQueue :" + proc.hasIntersectionByPQueue(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("TreeSet:" + proc.hasIntersectionByTreeSet(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("HashSet:" + proc.hasIntersectionByHashSet(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("HashSet2" + proc.hasIntersectionByHashSet2(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("SortBS :" + proc.hasIntersectionBySortBS(t, b) + "," + (System.currentTimeMillis() - a));

    for (int i = (nMax - 1); i >= 0 ; i --) {
      b[nMax - i - 1] = String.valueOf(i);
    }
    System.out.println();

    a = System.currentTimeMillis();
    System.out.println("HashMap:" + proc.hasIntersectionByHashMap(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("TreeMap:" + proc.hasIntersectionByTreeMap(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("IndexOf:" + proc.hasIntersectionByIndexOf(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("PQueue :" + proc.hasIntersectionByPQueue(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("TreeSet:" + proc.hasIntersectionByTreeSet(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("HashSet:" + proc.hasIntersectionByHashSet(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("HashSet2" + proc.hasIntersectionByHashSet2(t, b) + "," + (System.currentTimeMillis() - a));

    a = System.currentTimeMillis();
    System.out.println("SortBS :" + proc.hasIntersectionBySortBS(t, b) + "," + (System.currentTimeMillis() - a));
  }
}
