package javay.fsm.transition;

import javay.fsm.state.State;

/**
 * 源状态
 * 警戒条件
 * 触发器
 * 动作
 * 目标状态
 * @author DBJ
 *
 */
public interface Transition {
	// 源状态
	public State getFrom();
	public void setFrom(State from);
	// 警戒条件
	public Condition getCondition();
	public void setCondition(Condition condition);
	// 触发器
	// 动作
	// 目标状态
	public State getTo();
	public void setTo(State to);
}
