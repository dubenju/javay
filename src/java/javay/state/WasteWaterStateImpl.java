package javay.state;

/**
 * 沸水状态实现类
 */
public class WasteWaterStateImpl implements IWaterState {
	@Override
	public void printState() {
		System.out.println("水的状态：沸水！");
	}
}
