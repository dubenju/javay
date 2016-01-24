package javay.fsm.transition;

import javay.fsm.state.State;

/**
 * Transition.
 * 源状态
 * 警戒条件
 * 触发器
 * 动作
 * 目标状态
 * @author DBJ
 *
 */
public interface Transition<T> {
  // 源状态
  public State<T> getFrom();

  public void setFrom(State<T> from);

  // 警戒条件
  public Condition getCondition();

  public void setCondition(Condition condition);

  // 触发器
  // 动作
  public Action<T> getAction();

  public void setAction(Action<T> action);

  // 目标状态
  public State<T> getTo();

  public void setTo(State<T> to);
}
