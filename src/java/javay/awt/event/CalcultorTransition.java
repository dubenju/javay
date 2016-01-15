package javay.awt.event;

import javay.fsm.state.State;
import javay.fsm.transition.Action;
import javay.fsm.transition.Condition;
import javay.fsm.transition.Transition;
import javay.fsm.transition.Trigger;

public class CalcultorTransition implements Transition<ExprInfo> {

	private State<ExprInfo> from;
	private State<ExprInfo> to;
	@SuppressWarnings("unused")
	private Trigger trigger;
	private Condition condition;
	private Action<ExprInfo> action;

	public CalcultorTransition(State<ExprInfo> from, Trigger trigger, Condition condition, Action<ExprInfo> action, State<ExprInfo> to) {
		this.from = from;
		this.trigger = trigger;
		this.condition = condition;
		this.action = action;
		this.to = to;
	}

	@Override
	public State<ExprInfo> getFrom() {
		return this.from;
	}

	@Override
	public void setFrom(State<ExprInfo> from) {
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
	public State<ExprInfo> getTo() {
		return this.to;
	}

	@Override
	public void setTo(State<ExprInfo> to) {
		this.to = to;
	}

	/**
	 * @return the action
	 */
	public Action<ExprInfo> getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action<ExprInfo> action) {
		this.action = action;
	}

}
