package javay.awt.event;

import java.util.ArrayList;
import java.util.List;

import javay.fsm.FiniteStateMachine;
import javay.fsm.state.State;
import javay.fsm.state.StateFinal;
import javay.fsm.state.StateInitial;
import javay.fsm.transition.Transition;

public class CalcultorFSM implements FiniteStateMachine, Runnable {

	private List<State> states;
	private State initialState;
	private List<Transition> transitions;
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

		this.transitions = new ArrayList<Transition>();
		Transition tran_i_1 = new CalcultorTransition(this.initialState, null, null, null, state1);
		Transition tran_1_3 = new CalcultorTransition(state1, null, null, null, state3);
		Transition tran_1_5 = new CalcultorTransition(state1, null, null, null, state5);
		Transition tran_3_4 = new CalcultorTransition(state3, null, null, null, state4);
		Transition tran_4_3 = new CalcultorTransition(state4, null, null, null, state3);
		Transition tran_4_5 = new CalcultorTransition(state4, null, null, null, state5);
		Transition tran_4_7 = new CalcultorTransition(state4, null, null, null, state7);
		Transition tran_5_4 = new CalcultorTransition(state5, null, null, null, state4);
		Transition tran_5_6 = new CalcultorTransition(state5, null, null, null, state6);
		Transition tran_6_4 = new CalcultorTransition(state6, null, null, null, state4);
		Transition tran_6_5 = new CalcultorTransition(state6, null, null, null, state5);
		Transition tran_7_5 = new CalcultorTransition(state7, null, null, null, state5);

		this.transitions.add(tran_i_1);
		this.transitions.add(tran_1_3);
		this.transitions.add(tran_1_5);
		this.transitions.add(tran_3_4);
		this.transitions.add(tran_4_3);
		this.transitions.add(tran_4_5);
		this.transitions.add(tran_4_7);
		this.transitions.add(tran_5_4);
		this.transitions.add(tran_5_6);
		this.transitions.add(tran_6_4);
		this.transitions.add(tran_6_5);
		this.transitions.add(tran_7_5);

		this.currentState = this.initialState;
	}

	@Override
	public List<String> getInputs() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setInputs(List<String> in) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public List<State> getStates() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setStates(List<State> states) {
		// TODO 自動生成されたメソッド・スタブ

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
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setTransitions(List<Transition> transition) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public List<State> getFinalStates() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setFinalStates(List<State> finalStates) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public List<String> getOutputs() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void setOutputs(List<String> out) {
		// TODO 自動生成されたメソッド・スタブ

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
}
