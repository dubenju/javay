package javay.awt.event;

import javay.fsm.state.State;

public class CalcultorState implements State {
	private int id = 1;
	private String label = "初始状态";
	private String value = "";

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
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(this.id);
		buf.append(":");
		buf.append(this.label);
		buf.append("[");
		buf.append(this.value);
		buf.append("]");
		return buf.toString();
	}
}
