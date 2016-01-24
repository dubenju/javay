package javay.fsm;

/**
 * 空调Aircon。.简单的模型：
 * 遥控器有两个按钮(更多的按钮在下面的例子中引入)，power电源键和cool制冷键。
 * 空调的运行呈现3个状态，停止/Off、仅送风/FanOnly、制冷/Cool。
 * 起始状态为Off
 */
public class Aircon0 {
  // 0:off，1:FanOnly，AC
  // 0:off, 1:
  // 起始状态为Off
  private int state = 0;

  public int getState() {
    return state;
  }

  /**
   * 两个Action.
   * 按power键
   */
  public void power() {
    if (state == 0) {
      //off
      state = 1;
      System.out.println("start Fan");
    } else if ( state == 1 ) {
      //FanOnly
      state = 0;
      System.out.println("stop Fan");
    } else {
      state = 0;
      System.out.println("stop Cool");
    }
  }

  /**
   * 按制冷键.
   */
  public void cool() {
    if ( state == 0 ) {
      // off
      System.out.println("nothing");
    } else if ( state == 1 ) {
      //FanOnly
      state = 2;
      System.out.println("start Cool");
    } else {
      state = 1;
      System.out.println("stop Cool");
    }
  }
}
