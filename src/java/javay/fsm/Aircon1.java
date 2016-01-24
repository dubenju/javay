package javay.fsm;

public class Aircon1 {
  State1 state = State1.OFF;//private改默认，删除getState()。

  public void power() {
    //按power键
    state.power(this);
  }

  public void cool() {
    //按制冷键
    state.cool(this);
  }

  /**
   * ACCtrl的代码。.
   */
  public static void main(String[] args) {
    Aircon1 ac = new Aircon1();
    System.out.println("Current State:" + ac.state.name());
    ac.cool();
    ac.power();
    ac.cool();
    ac.cool();
    ac.power();
    ac.power();
    ac.power();

  }
}
