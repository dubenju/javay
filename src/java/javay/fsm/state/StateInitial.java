package javay.fsm.state;

import javay.fsm.state.State;

public class StateInitial implements State {
	private int id = 0;
	private String labe = "初始状态";
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getLabel() {
		return this.labe;
	}

}
