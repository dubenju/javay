package javay.game.minesweeper;

import java.util.Random;
public class SaoLei {
  public static void main(String args[]){
    Random r=new Random();        //产生随机数，为随机产生“地雷”做准备
    int n=15;               //行列数
    int[][] Arr=new int[n][n];

    for(int i=0;i<r.nextInt(1000);i++){//随机产生“地雷”
      Arr[r.nextInt(n)][r.nextInt(n)]=9;  //当数组内的值大于等于9时认为是地雷
    }
//下面进行判断，思路：遍历“地雷”位置，找到后对其附近的数组进行加1操作
    for(int i=0;i<n;i++){
      for(int j=0;j<Arr[i].length;j++){
        if(Arr[i][j]>8){
          if(j>0){       //保证列不越界
            Arr[i][j-1]++;
          }
          if(j<Arr[i].length-1){//保证行不越界
            Arr[i][j+1]++;
          }
          if(i>0){       //上一行
            Arr[i-1][j]++;
            if(j>0){
              Arr[i-1][j-1]++;
            }
            if(j<Arr[i].length-1){
              Arr[i-1][j+1]++;
            }
          }
          if(i<n-1){         //下一行
            Arr[i+1][j]++;
            if(j>0){
              Arr[i+1][j-1]++;
            }
            if(j<Arr[i].length-1){
              Arr[i+1][j+1]++;
            }
          }
        }
      }
    }
    for(int i=0;i<n;i++){
      for(int j=0;j<n;j++){
        if(Arr[i][j]>=9)
          System.out.print(" * ");
        else
          System.out.print(" "+Arr[i][j]+" " );
      }
      System.out.println( );
    }
  }
}
