package javay.fsm.state;

import javay.fsm.state.State;

public class StateFinal implements State {
	private int id = Integer.MAX_VALUE;
	private String labe = "最终状态";
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getLabel() {
		return this.labe;
	}

}
