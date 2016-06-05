package javay.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class CircleTest {

    public static void main(String[] args) {
        //ArrayList test
        Date bdate = new Date();
        System.out.println("list result:"+getLastNumByList(10000, 7));
        Date edate = new Date();
        System.out.println("list cost:"+(edate.getTime()-bdate.getTime())+" 毫秒");

        //LinkedList test
        bdate = new Date();
        System.out.println("linkedlist result:"+getLastNumByLinkedList(10000, 7));
        edate = new Date();
        System.out.println("linkedlist cost:"+(edate.getTime()-bdate.getTime())+" 毫秒");


    }

    public static int getLastNumByList(int totleNum,int stepNum){
        long st = System.currentTimeMillis();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i =0;i<totleNum;i++){
            list.add(i);
        }
        long ed = System.currentTimeMillis();
        System.out.println("A=" + (ed - st) );
        int nowNum =0;
        while(list.size()>1){
            for(int i=0;i<stepNum;i++){
                nowNum ++;
                if(nowNum > list.size()){
                    nowNum = 1;
                }
            }
            list.remove(nowNum-1);
//            list.add(nowNum-1);
        }
        long ed2 = System.currentTimeMillis();
        System.out.println("A=" + (ed2 - ed) );
        return list.get(0);

    }
    public static int getLastNumByLinkedList(int totleNum,int stepNum){
    	long st = System.currentTimeMillis();
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        for(int i =0;i<totleNum;i++){
            linkedList.add(i);
        }
        long ed = System.currentTimeMillis();
        System.out.println("L=" + (ed - st) );
        int nowNum =0;
        while(linkedList.size()>1){
            for(int i=0;i<stepNum;i++){
                nowNum ++;
                if(nowNum > linkedList.size()){
                    nowNum = 1;
                }
            }
             linkedList.remove(nowNum-1);
//            linkedList.add(nowNum-1);
        }
        long ed2 = System.currentTimeMillis();
        System.out.println("L=" + (ed2 - ed) );
        return linkedList.get(0);

    }
}
