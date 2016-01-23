package javay.test.fsm;

import javay.awt.event.CalcultorFsm;

public class TestFSM {

  public static void main(String[] args) {
    CalcultorFsm fsm = new CalcultorFsm();
    new Thread(fsm).start();
  }

}
