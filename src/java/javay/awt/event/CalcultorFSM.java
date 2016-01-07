package javay.awt.event;

import java.util.ArrayList;
import java.util.List;

import javay.fsm.FiniteStateMachine;
import javay.fsm.state.State;
import javay.fsm.state.StateFinal;
import javay.fsm.state.StateInitial;
import javay.fsm.transition.Condition;
import javay.fsm.transition.Transition;
/**
 * C¥S 1 2 3 4 5 6 7 
 * 0.9 3   3 3 6 6  
 * una 7   4 5 4 4  
 * bin 2   5 5 5 5
 * @author dubenju
 *
 */
public class CalcultorFSM implements FiniteStateMachine, Runnable {

	private List<State> states;
	private State initialState;
	private List<List<Transition>> transitions;
	private State currentState;

	public CalcultorFSM() {
		this.initialState = new StateInitial();
		State state1 = new CalcultorState(1, "初期状态");
		State state2 = new CalcultorState(2, "错误状态");
		State state3 = new CalcultorState(3, "数值输入中");
		State state4 = new CalcultorState(4, "得出结果");
		State state5 = new CalcultorState(5, "操作符号输入完了");
		State state6 = new CalcultorState(6, "第二个数值输入中");
		State state7 = new CalcultorState(7, "");
		State state8 = new StateFinal();

		this.states = new ArrayList<State>();
		this.states.add(this.initialState);
		this.states.add(state1);
		this.states.add(state2);
		this.states.add(state3);
		this.states.add(state4);
		this.states.add(state5);
		this.states.add(state6);
		this.states.add(state7);
		this.states.add(state8);

		// 邻接表,逆临接表(DAG)
		this.transitions = new ArrayList<List<Transition>>(this.states.size());
		Transition tran_i_1 = new CalcultorTransition(this.initialState, null, null, null, state1);

		ConditionNum num = new ConditionNum();
		ConditionOpt1 opt1 = new ConditionOpt1();
		ConditionOpt2 opt2 = new ConditionOpt2();
		// 初期状态 + 0.9 = 数值输入中
		Transition tran_1_3 = new CalcultorTransition(state1, null, num, null, state3);
		// 初期状态 + OPT = 操作符号输入完了
		Transition tran_1_5 = new CalcultorTransition(state1, null, null, null, state5);
		// 数值输入中 + OPT or = = 得出结果
		Transition tran_3_4 = new CalcultorTransition(state3, null, null, null, state4);
		// 得出结果 + 0.9 = 数值输入中
		Transition tran_4_3 = new CalcultorTransition(state4, null, num, null, state3);
		// 得出结果 + OPT = 操作符号输入完了
		Transition tran_4_5 = new CalcultorTransition(state4, null, null, null, state5);
		// 得出结果 + = 
		Transition tran_4_7 = new CalcultorTransition(state4, null, null, null, state7);
		// 操作符号输入完了 + OPT1 = 得出结果
		Transition tran_5_4 = new CalcultorTransition(state5, null, null, null, state4);
		// 操作符号输入完了 + 0.9 = 第二个数值输入中
		Transition tran_5_6 = new CalcultorTransition(state5, null, num, null, state6);
		// 第二个数值输入中 + OPT1 = 得出结果
		Transition tran_6_4 = new CalcultorTransition(state6, null, null, null, state4);
		// 第二个数值输入中 + OPT2 = 得出结果
		Transition tran_6_5 = new CalcultorTransition(state6, null, null, null, state5);
		//  + = 得出结果
		Transition tran_7_5 = new CalcultorTransition(state7, null, null, null, state5);

		List<Transition> ti = new ArrayList<Transition>();
		ti.add(tran_i_1);
		this.transitions.add(ti);
		
		List<Transition> t1 = new ArrayList<Transition>();
		t1.add(tran_1_3);
		t1.add(tran_1_5);
		this.transitions.add(t1);
		List<Transition> t2 = new ArrayList<Transition>();
		this.transitions.add(t2);
		List<Transition> t3 = new ArrayList<Transition>();
		t3.add(tran_3_4);
		this.transitions.add(t3);
		List<Transition> t4 = new ArrayList<Transition>();
		t4.add(tran_4_3);
		t4.add(tran_4_5);
		t4.add(tran_4_7);
		this.transitions.add(t4);
		List<Transition> t5 = new ArrayList<Transition>();
		t5.add(tran_5_4);
		t5.add(tran_5_6);
		this.transitions.add(t5);
		List<Transition> t6 = new ArrayList<Transition>();
		this.transitions.add(t6);
		t6.add(tran_6_4);
		t6.add(tran_6_5);
		List<Transition> t7 = new ArrayList<Transition>();
		t7.add(tran_7_5);
		this.transitions.add(t7);

		this.currentState = this.initialState;
	}

	@Override
	public List<String> getInputs() {
		return null;
	}

	@Override
	public void setInputs(List<String> in) {
	}

	@Override
	public List<State> getStates() {
		return null;
	}

	@Override
	public void setStates(List<State> states) {
	}

	@Override
	public State getInitialState() {
		return this.initialState;
	}

	@Override
	public void setInitialState(State initial) {
		this.initialState = initial;
	}

	@Override
	public List<Transition> getTransitions() {
		return null;
	}

	@Override
	public void setTransitions(List<Transition> transition) {
	}

	@Override
	public List<State> getFinalStates() {
		return null;
	}

	@Override
	public void setFinalStates(List<State> finalStates) {
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
	public State getCurrentState() {
		return this.currentState;
	}

	@Override
	public String receive(String s) {
		State cur = this.getCurrentState();
		// get all condition
		List<Transition> trans = this.transitions.get(this.states.indexOf(cur));
		// check it
		Transition tran = null;
		for (Transition t : trans) {
			Condition cond = t.getCondition();
			if (cond != null && cond.isGuard(s)) {
				tran = t;
				break;
			}
		}
		// get next state
		if (tran != null) {

		}
		// make output
		String out = "";
		return out;
	}
}
