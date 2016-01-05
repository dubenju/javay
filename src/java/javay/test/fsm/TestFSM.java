package javay.test.fsm;

import javay.awt.event.CalcultorFSM;

public class TestFSM {

	public static void main(String[] args) {
		CalcultorFSM fsm = new CalcultorFSM();
		new Thread(fsm).start();
	}

}
