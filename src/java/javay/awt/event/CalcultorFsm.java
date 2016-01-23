package javay.awt.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javay.fsm.FiniteStateMachine;
import javay.fsm.state.State;
import javay.fsm.state.StateFinal;
import javay.fsm.state.StateInitial;
import javay.fsm.transition.Action;
import javay.fsm.transition.Condition;
import javay.fsm.transition.Transition;
/**
 * C¥S 1 2 3 4 5 6 7
 * 0.9 3   3 3 6 6
 * una 7   4 4 2 4
 * bin 2   5 5 2 5
 * @author dubenju
 *
 */
public class CalcultorFsm implements FiniteStateMachine<ExprInfo>, Runnable {

  private static final Logger log = LoggerFactory.getLogger(CalcultorFsm.class);
  private List<State<ExprInfo>> states;
  private State<ExprInfo> initialState;
  private List<List<Transition<ExprInfo>>> transitions;
  private State<ExprInfo> currentState;

  /**
   * CalcultorFsm.
   */
  public CalcultorFsm() {
    this.initialState = new StateInitial<ExprInfo>();
    ExprInfo value = new ExprInfo();
    this.initialState.setValue(value);
    State<ExprInfo> state1 = new CalcultorState<ExprInfo>(1, "初期状态");
    final State<ExprInfo> state2 = new CalcultorState<ExprInfo>(2, "错误状态");
    final State<ExprInfo> state3 = new CalcultorState<ExprInfo>(3, "数值1输入中");
    final State<ExprInfo> state4 = new CalcultorState<ExprInfo>(4, "得出结果");
    final State<ExprInfo> state5 = new CalcultorState<ExprInfo>(5, "操作符号输入完了");
    final State<ExprInfo> state6 = new CalcultorState<ExprInfo>(6, "第二个数值输入中");
    final State<ExprInfo> state8 = new StateFinal<ExprInfo>();

    this.states = new ArrayList<State<ExprInfo>>();
    this.states.add(this.initialState);
    this.states.add(state1);
    this.states.add(state2);
    this.states.add(state3);
    this.states.add(state4);
    this.states.add(state5);
    this.states.add(state6);
    this.states.add(state8);

    // 邻接表,逆临接表(DAG)
    this.transitions = new ArrayList<List<Transition<ExprInfo>>>(this.states.size());
    Transition<ExprInfo> tranI1
        = new CalcultorTransition(this.initialState, null, null, null, state1);

    ConditionNum num = new ConditionNum();
    ConditionOpt1 opt1 = new ConditionOpt1();
    ConditionOpt2 opt2 = new ConditionOpt2();
    ConditionEqual equal = new ConditionEqual();
    ConditionBsce bsce = new ConditionBsce();
    ConditionInit init = new ConditionInit();

    ActionNum1 anum1 = new ActionNum1();
    ActionOpt2 aopt2 = new ActionOpt2();
    ActionNum2 anum2 = new ActionNum2();
    ActionError aerr = new ActionError();
    ActionOpt1 aopt1 = new ActionOpt1();
    ActionEqual aequal = new ActionEqual();
    ActionBsce absce = new ActionBsce();
    ActionInit ainit = new ActionInit();
    //a33
    /*
     * 1:初期状态
     * 2:错误状态
     * 3:数值1输入中
     * 4:得出结果
     * 5:二元操作符输入完了
     * 6:第二个数值输入中
     * 7:一元操作符输入完了
     */
    // TODO:MR
    // TODO:add对状态机的影响
    /*
     * C¥S 1 2 3 4 5 6
     * 0.9 3 3 3 3 6 6
     * una 2 2 4 4 2 4
     * bin 2 2 5 5 2 5
     * =   2 2 4 4 2 4
     * BS  1 1 3 4   6
     * CE  1 1 3 4   6
     * C   1 1 1 1 1 1
     * MR  3 3 3 3 6 6
     */
    Transition<ExprInfo> tran123  = new CalcultorTransition(state1, null, num  , anum1 , state3);
    final Transition<ExprInfo> tran122
        = new CalcultorTransition(state1, null, opt1 , aerr  , state2);
    final Transition<ExprInfo> tran1221
        = new CalcultorTransition(state1, null, opt2 , aerr  , state2);
    final Transition<ExprInfo> tran1222
        = new CalcultorTransition(state1, null, equal, aerr  , state2);
    final Transition<ExprInfo> tran121
        = new CalcultorTransition(state1, null, bsce , absce , state1);
    final Transition<ExprInfo> tran1211
        = new CalcultorTransition(state1, null, init , ainit , state1);
    final Transition<ExprInfo> tran223
        = new CalcultorTransition(state2, null, num  , anum1 , state3);
    final Transition<ExprInfo> tran222
        = new CalcultorTransition(state2, null, opt1 , aerr  , state2);
    final Transition<ExprInfo> tran2221
        = new CalcultorTransition(state2, null, opt2 , aerr  , state2);
    final Transition<ExprInfo> tran2222
        = new CalcultorTransition(state2, null, equal, aerr  , state2);
    final Transition<ExprInfo> tran221
        = new CalcultorTransition(state2, null, bsce , absce , state1);
    final Transition<ExprInfo> tran2211
        = new CalcultorTransition(state2, null, init , ainit , state1);
    final Transition<ExprInfo> tran324
        = new CalcultorTransition(state3, null, opt1 , aopt1 , state4);
    final Transition<ExprInfo> tran325
        = new CalcultorTransition(state3, null, opt2 , aopt2 , state5);
    final Transition<ExprInfo> tran3241
        = new CalcultorTransition(state3, null, equal, aequal, state4);
    final Transition<ExprInfo> tran323
        = new CalcultorTransition(state3, null, bsce , absce , state3);
    final Transition<ExprInfo> tran321
        = new CalcultorTransition(state3, null, init , ainit , state1);
    final Transition<ExprInfo> tran423
        = new CalcultorTransition(state4, null, num  , anum1 , state3);
    final Transition<ExprInfo> tran424
        = new CalcultorTransition(state4, null, opt1 , aopt1 , state4);
    final Transition<ExprInfo> tran425
        = new CalcultorTransition(state4, null, opt2 , aopt2 , state5);
    final Transition<ExprInfo> tran4241
        = new CalcultorTransition(state4, null, equal, aequal, state4);
    final Transition<ExprInfo> tran4242
        = new CalcultorTransition(state4, null, bsce , absce , state4);
    final Transition<ExprInfo> tran421
        = new CalcultorTransition(state4, null, init , ainit , state1);
    final Transition<ExprInfo> tran522
        = new CalcultorTransition(state5, null, opt1 , aerr  , state2);
    final Transition<ExprInfo> tran5221
        = new CalcultorTransition(state5, null, opt2 , aerr  , state2);
    final Transition<ExprInfo> tran5222
        = new CalcultorTransition(state5, null, equal, aerr  , state2);
    final Transition<ExprInfo> tran526
        = new CalcultorTransition(state5, null, num  , anum2 , state6);
    final Transition<ExprInfo> tran521
        = new CalcultorTransition(state5, null, init , ainit , state1);
    final Transition<ExprInfo> tran624
        = new CalcultorTransition(state6, null, opt1 , aopt1 , state4);
    final Transition<ExprInfo> tran6241
        = new CalcultorTransition(state6, null, equal, aequal, state4);
    final Transition<ExprInfo> tran625
        = new CalcultorTransition(state6, null, opt2 , aopt2 , state5);
    final Transition<ExprInfo> tran626
        = new CalcultorTransition(state6, null, bsce , absce , state6);
    final Transition<ExprInfo> tran621
        = new CalcultorTransition(state6, null, init , ainit , state1);

    List<Transition<ExprInfo>> ti = new ArrayList<Transition<ExprInfo>>();
    ti.add(tranI1);
    this.transitions.add(ti);
    List<Transition<ExprInfo>> t1 = new ArrayList<Transition<ExprInfo>>();
    t1.add(tran123);
    t1.add(tran122);
    t1.add(tran1221);
    t1.add(tran1222);
    t1.add(tran121);
    t1.add(tran1211);
    this.transitions.add(t1);
    List<Transition<ExprInfo>> t2 = new ArrayList<Transition<ExprInfo>>();
    t2.add(tran223);
    t2.add(tran222);
    t2.add(tran2221);
    t2.add(tran2222);
    t2.add(tran221);
    t2.add(tran2211);
    this.transitions.add(t2);
    List<Transition<ExprInfo>> t3 = new ArrayList<Transition<ExprInfo>>();
    t3.add(tran324);
    t3.add(tran325);
    t3.add(tran3241);
    t3.add(tran323);
    t3.add(tran321);
    this.transitions.add(t3);
    List<Transition<ExprInfo>> t4 = new ArrayList<Transition<ExprInfo>>();
    t4.add(tran423);
    t4.add(tran425);
    t4.add(tran424);
    t4.add(tran4241);
    t4.add(tran4242);
    t4.add(tran421);
    this.transitions.add(t4);
    List<Transition<ExprInfo>> t5 = new ArrayList<Transition<ExprInfo>>();
    t5.add(tran522);
    t5.add(tran5221);
    t5.add(tran5222);
    t5.add(tran526);
    t5.add(tran521);
    this.transitions.add(t5);
    List<Transition<ExprInfo>> t6 = new ArrayList<Transition<ExprInfo>>();
    this.transitions.add(t6);
    t6.add(tran624);
    t6.add(tran6241);
    t6.add(tran625);
    t6.add(tran626);
    t6.add(tran621);
    List<Transition<ExprInfo>> t8 = new ArrayList<Transition<ExprInfo>>();
    this.transitions.add(t8);

    this.currentState = this.initialState;
    this.currentState = tran(this.currentState);
  }

  private State<ExprInfo> tran(State<ExprInfo> from) {
    List<Transition<ExprInfo>> trans = this.transitions.get(this.states.indexOf(from));
    State<ExprInfo> res = from;
    for (Transition<ExprInfo> t : trans) {
      Condition cond = t.getCondition();
      if (cond == null) {
        res = t.getTo();
        res.setValue(from.getValue());
        break;
      }
    }
    return res;
  }

  @Override
  public List<String> getInputs() {
    return null;
  }

  @Override
  public void setInputs(List<String> in) {
  }

  @Override
  public List<State<ExprInfo>> getStates() {
    return null;
  }

  @Override
  public void setStates(List<State<ExprInfo>> states) {
  }

  @Override
  public State<ExprInfo> getInitialState() {
    return this.initialState;
  }

  @Override
  public void setInitialState(State<ExprInfo> initial) {
    this.initialState = initial;
  }

  @Override
  public List<Transition<ExprInfo>> getTransitions() {
    return null;
  }

  @Override
  public void setTransitions(List<Transition<ExprInfo>> transition) {
  }

  @Override
  public List<State<ExprInfo>> getFinalStates() {
    return null;
  }

  @Override
  public void setFinalStates(List<State<ExprInfo>> finalStates) {
  }

  @Override
  public List<String> getOutputs() {
    return null;
  }

  @Override
  public void setOutputs(List<String> out) {
  }

  @Override
  public void run() {
    System.out.println("--- run begin ---");
    System.out.println("--- run  end  ---");
  }

  @Override
  public State<ExprInfo> getCurrentState() {
    return this.currentState;
  }

  @Override
  public ExprInfo receive(String sin, Object params) {
    log.debug(">>> receive begin ---");
    State<ExprInfo> cur = this.getCurrentState();
    System.out.print("cur ★" + cur + ",sin=" + sin);

    ExprInfo val = cur.getValue();
    // get all condition
    List<Transition<ExprInfo>> trans = this.transitions.get(this.states.indexOf(cur));
    // check it
    Transition<ExprInfo> tran = null;
    for (Transition<ExprInfo> t : trans) {
      Condition cond = t.getCondition();
      if (cond != null && cond.isGuard(sin)) {
        tran = t;
        break;
      }
    }
    // get next state
    if (tran != null) {
      Action<ExprInfo> action = tran.getAction();
      ExprInfo str = val;
      str.setInput(sin);
      if (action != null) {
        @SuppressWarnings("unchecked")
        Map<String, String> context = (Map<String, String>) params;
        context.put("from", String.valueOf(cur.getId()));
        str = action.doAction(val, context);
        context.remove("from");
      }
      State<ExprInfo> to = tran.getTo();
      to.setValue(str);
      System.out.print(" to ★" + to);
      this.currentState = to;
    } else {
      System.out.println("!!!WARNING!!! This state's transition is undefined." + cur);
      val.append(sin);
      cur.setValue(val);
      System.out.print(" to ☆" + cur);
    }
    // make output
    ExprInfo out = this.currentState.getValue();
    System.out.println("--- receive  end  >>>" + out);
    return out;
  }
}
