package javay.test.java;

public class Reader03 {

  public static void main(String[] args) {
    O[] ary = {new P1(), new P2(), new P3(), new P4()};
    for(O a : ary) {
      a.play();
    }

  }

}
interface O {
  public static final int MODE = 1;
  void play();
}
class P1 implements O {
  public void play() {System.out.println("P1 MODE" + MODE);}
}
class P2 implements O {
  public static final int MODE = 2;
  public void play() {System.out.println("P2 MODE" + MODE);}
}
class P3 extends P1 {
  public static final int MODE = 3;
}
class P4 extends P2 {
  public void play() {System.out.println("P4 MODE" + MODE);}
}
