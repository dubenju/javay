package javay.state;

/**
 * 测试Main方法
 */
public class TestMain {
  public static void main(String [] args){
    IWaterState state = null;
    WaterContext context = new WaterContext();
    for(int i = 0; i < 3; i ++) {
      try {
        context.setState(i);
        state = context.getState();
        state.printState();
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
