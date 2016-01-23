package javay.state;

public class ConcreteStateA implements State2 {
  @Override
  public void handle(String sampleParameter) {

    System.out.println("ConcreteStateA handle ：" + sampleParameter);
  }

}
