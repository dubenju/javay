package javay.awt.event;

import javay.fsm.state.State;

public class CalcultorState implements State {
	private int id = 1;
	private String label = "初始状态";
	public CalcultorState() {
		
	}
	public CalcultorState(int id, String label) {
		this.id = id;
		this.label = label;
	}
	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	/**
	 * @param labe the labe to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
