package javay.state;

/**
 * 冰水状态实现类
 */
public class IceWaterStateImpl implements IWaterState {
  @Override
  public void printState() {
    System.out.println("水的状态：冰水！");
  }
}
