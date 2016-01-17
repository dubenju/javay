package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javay.fsm.state.State;

public class CalcultorState<T> implements State<T> {
    private static final Logger log = LoggerFactory.getLogger(CalcultorState.class);
	private int id = 1;
	private String label = "初始状态";
	private T value = null;

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
	public T getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
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
