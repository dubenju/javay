package javay.fsm;

import java.util.List;

import javay.fsm.state.State;
import javay.fsm.transition.Transition;

/**
 * 输入范围
 * 状态集合
 * 初始状态，是状态集合的元素
 * 状态转移
 * 最终状态，是状态集合的子集。
 * 输出 接受输入，不接受输入
 *
 * @author DBJ
 *
 */
public interface FiniteStateMachine {

	// Input
	public List<String> getInputs();
	public void setInputs(List<String> in);
	// States
	public List<State> getStates();
	public void setStates(List<State> states);
	// Initial State
	public State getInitialState();
	public void setInitialState(State initial);
	// Transition
	public List<Transition> getTransitions();
	public void setTransitions(List<Transition> transition);
	// Final State
	public List<State> getFinalStates();
	public void setFinalStates(List<State> finalStates);
	// Output
	public List<String> getOutputs();
	public void setOutputs(List<String> out);
}
