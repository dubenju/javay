package javay.fsm;

public class ACCtrl {

	public static void main(String[] args) {
        Aircon0 ac = new Aircon0();//power() cool()
        System.out.println("Current State:" + ac.getState());
        ac.cool();
        ac.power();
        ac.cool();
        ac.cool();
        ac.power();
        ac.power();
	}

}
