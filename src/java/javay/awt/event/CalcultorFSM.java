package javay.awt.event;

import javay.fsm.FiniteStateMachine;
import javay.fsm.state.State;

public class CalcultorFSM implements FiniteStateMachine {

	public CalcultorFSM() {
		State state1 = new CalcultorState(1, "初期状态");
		State state2 = new CalcultorState(2, "错误状态");
		State state3 = new CalcultorState(3, "数值输入中");
		State state4 = new CalcultorState(4, "得出结果");
		State state5 = new CalcultorState(5, "操作符号输入完了");
		State state6 = new CalcultorState(6, "第二个数值输入中");
		State state7 = new CalcultorState(7, "");
	}
}
