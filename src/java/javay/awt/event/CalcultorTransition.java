package javay.awt.event;

import javay.fsm.state.State;
import javay.fsm.transition.Action;
import javay.fsm.transition.Condition;
import javay.fsm.transition.Transition;
import javay.fsm.transition.Trigger;

public class CalcultorTransition implements Transition {

	private State from;
	private State to;
	private Trigger trigger;
	private Condition condition;
	private Action action;

	public CalcultorTransition(State from, Trigger trigger, Condition condition, Action action, State to) {
		this.from = from;
		this.trigger = trigger;
		this.condition = condition;
		this.action = action;
		this.to = to;
	}

	@Override
	public State getFrom() {
		return this.from;
	}

	@Override
	public void setFrom(State from) {
		this.from = from;
	}

	@Override
	public Condition getCondition() {
		return this.condition;
	}

	@Override
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	@Override
	public State getTo() {
		return this.to;
	}

	@Override
	public void setTo(State to) {
		this.to = to;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

}
