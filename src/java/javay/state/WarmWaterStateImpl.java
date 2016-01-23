package javay.state;

/**
 * 温水状态实现类
 */
public class WarmWaterStateImpl implements IWaterState {
  @Override
  public void printState() {
    System.out.println("水的状态：温水！");
  }
}
