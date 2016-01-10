package javay.awt.event;

import java.util.ArrayList;
import java.util.List;

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
public class CalcultorFSM implements FiniteStateMachine<ExprInfo>, Runnable {

	private List<State<ExprInfo>> states;
	private State<ExprInfo> initialState;
	private List<List<Transition<ExprInfo>>> transitions;
	private State<ExprInfo> currentState;

	public CalcultorFSM() {
		this.initialState = new StateInitial<ExprInfo>();
		ExprInfo value = new ExprInfo();
		this.initialState.setValue(value);
		State<ExprInfo> state1 = new CalcultorState<ExprInfo>(1, "初期状态");
		State<ExprInfo> state2 = new CalcultorState<ExprInfo>(2, "错误状态");
		State<ExprInfo> state3 = new CalcultorState<ExprInfo>(3, "数值1输入中");
		State<ExprInfo> state4 = new CalcultorState<ExprInfo>(4, "得出结果");
		State<ExprInfo> state5 = new CalcultorState<ExprInfo>(5, "操作符号输入完了");
		State<ExprInfo> state6 = new CalcultorState<ExprInfo>(6, "第二个数值输入中");
		State<ExprInfo> state8 = new StateFinal<ExprInfo>();

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
		Transition<ExprInfo> tran_i_1 = new CalcultorTransition(this.initialState, null, null, null, state1);

		ConditionNum num = new ConditionNum();
		ConditionOpt1 opt1 = new ConditionOpt1();
		ConditionOpt2 opt2 = new ConditionOpt2();
		ConditionEqual equal = new ConditionEqual();

		ActionNum1 anum1 = new ActionNum1();
		ActionOpt2 aopt2 = new ActionOpt2();
		ActionNum2 anum2 = new ActionNum2();
		ActionError aerr = new ActionError();
		ActionOpt1 aopt1 = new ActionOpt1();
		ActionEqual aequal = new ActionEqual();
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
		/*
		 * C¥S 1 2 3 4 5 6
		 * 0.9 3 3 3 3 6 6
		 * una 2 2 4 4 2 4
		 * bin 2 2 5 5 2 5
		 * =   2 2 4 4 2 4
		 */
		Transition<ExprInfo> tran_1_3  = new CalcultorTransition(state1, null, num  , anum1 , state3);
		Transition<ExprInfo> tran_1_2  = new CalcultorTransition(state1, null, opt1 , aerr  , state2);
		Transition<ExprInfo> tran_1_21 = new CalcultorTransition(state1, null, opt2 , aerr  , state2);
		Transition<ExprInfo> tran_1_22 = new CalcultorTransition(state1, null, equal, aerr  , state2);
		Transition<ExprInfo> tran_2_3  = new CalcultorTransition(state2, null, num  , anum1 , state3);
		Transition<ExprInfo> tran_2_2  = new CalcultorTransition(state2, null, opt1 , aerr  , state2);
		Transition<ExprInfo> tran_2_21 = new CalcultorTransition(state2, null, opt2 , aerr  , state2);
		Transition<ExprInfo> tran_2_22 = new CalcultorTransition(state2, null, equal, aerr  , state2);
		Transition<ExprInfo> tran_3_4  = new CalcultorTransition(state3, null, opt1 , aopt1 , state4);
		Transition<ExprInfo> tran_3_5  = new CalcultorTransition(state3, null, opt2 , aopt2 , state5);
		Transition<ExprInfo> tran_3_41 = new CalcultorTransition(state3, null, equal, aequal, state4);
		Transition<ExprInfo> tran_4_3  = new CalcultorTransition(state4, null, num  , anum1 , state3);
		Transition<ExprInfo> tran_4_4  = new CalcultorTransition(state4, null, opt1 , aopt1 , state4);
		Transition<ExprInfo> tran_4_5  = new CalcultorTransition(state4, null, opt2 , aopt2 , state5);
		Transition<ExprInfo> tran_4_41 = new CalcultorTransition(state4, null, equal, aequal, state4);
		Transition<ExprInfo> tran_5_2  = new CalcultorTransition(state5, null, opt1 , aerr  , state2);
		Transition<ExprInfo> tran_5_21 = new CalcultorTransition(state5, null, opt2 , aerr  , state2);
		Transition<ExprInfo> tran_5_22 = new CalcultorTransition(state5, null, equal, aerr  , state2);
		Transition<ExprInfo> tran_5_6  = new CalcultorTransition(state5, null, num  , anum2 , state6);
		Transition<ExprInfo> tran_6_4  = new CalcultorTransition(state6, null, opt1 , aopt1 , state4);
		Transition<ExprInfo> tran_6_41 = new CalcultorTransition(state6, null, equal, aequal, state4);
		Transition<ExprInfo> tran_6_5  = new CalcultorTransition(state6, null, opt2 , aopt2 , state5);

		List<Transition<ExprInfo>> ti = new ArrayList<Transition<ExprInfo>>();
		ti.add(tran_i_1);
		this.transitions.add(ti);
		List<Transition<ExprInfo>> t1 = new ArrayList<Transition<ExprInfo>>();
		t1.add(tran_1_3);
		t1.add(tran_1_2);
		t1.add(tran_1_21);
		t1.add(tran_1_22);
		this.transitions.add(t1);
		List<Transition<ExprInfo>> t2 = new ArrayList<Transition<ExprInfo>>();
		t2.add(tran_2_3);
		t2.add(tran_2_2);
		t2.add(tran_2_21);
		t2.add(tran_2_22);
		this.transitions.add(t2);
		List<Transition<ExprInfo>> t3 = new ArrayList<Transition<ExprInfo>>();
		t3.add(tran_3_4);
		t3.add(tran_3_5);
		t3.add(tran_3_41);
		this.transitions.add(t3);
		List<Transition<ExprInfo>> t4 = new ArrayList<Transition<ExprInfo>>();
		t4.add(tran_4_3);
		t4.add(tran_4_5);
		t4.add(tran_4_4);
		t4.add(tran_4_41);
		this.transitions.add(t4);
		List<Transition<ExprInfo>> t5 = new ArrayList<Transition<ExprInfo>>();
		t5.add(tran_5_2);
		t5.add(tran_5_21);
		t5.add(tran_5_22);
		t5.add(tran_5_6);
		this.transitions.add(t5);
		List<Transition<ExprInfo>> t6 = new ArrayList<Transition<ExprInfo>>();
		this.transitions.add(t6);
		t6.add(tran_6_4);
		t6.add(tran_6_41);
		t6.add(tran_6_5);
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
	public ExprInfo receive(String s, Object params) {
		System.out.print(">>> receive begin ---");
		State<ExprInfo> cur = this.getCurrentState();
		System.out.print("cur ★" + cur + ",s=" + s);

		ExprInfo val = cur.getValue();
		// get all condition
		List<Transition<ExprInfo>> trans = this.transitions.get(this.states.indexOf(cur));
		// check it
		Transition<ExprInfo> tran = null;
		for (Transition<ExprInfo> t : trans) {
			Condition cond = t.getCondition();
			if (cond != null && cond.isGuard(s)) {
				tran = t;
				break;
			}
		}
		// get next state
		if (tran != null) {
			Action<ExprInfo> action = tran.getAction();
			ExprInfo str = val;
			str.setInput(s);
			if (action != null) {
				str = action.doAction(val, params);
			}
			State<ExprInfo> to = tran.getTo();
			to.setValue(str);
			System.out.print(" to ★" + to);
			this.currentState = to;
		} else {
			System.out.println("!!!WARNING!!! This state's transition is undefined." + cur);
			val.append(s);
			cur.setValue(val);
			System.out.print(" to ⭐︎" + cur);
		}
		// make output
		ExprInfo out = this.currentState.getValue();
		System.out.println("--- receive  end  >>>" + out);
		return out;
	}
}
