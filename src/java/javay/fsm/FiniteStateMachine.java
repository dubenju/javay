package javay.fsm;

import javay.fsm.state.State;
import javay.fsm.transition.Transition;

import java.util.List;

/**
 * FiniteStateMachine.
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
public interface FiniteStateMachine<T> {

  // Input
  public List<String> getInputs();

  public void setInputs(List<String> in);

  // States
  public List<State<T>> getStates();

  public void setStates(List<State<T>> states);

  // Initial State
  public State<T> getInitialState();

  public void setInitialState(State<T> initial);

  // Transition
  public List<Transition<T>> getTransitions();

  public void setTransitions(List<Transition<T>> transition);

  // Final State
  public List<State<T>> getFinalStates();

  public void setFinalStates(List<State<T>> finalStates);

  // Output
  public List<String> getOutputs();

  public void setOutputs(List<String> out);

  public State<T> getCurrentState();

  public T receive(String sin, Object params);
}
