package javay.state;

public class ConcreteStateB implements State2 {

  @Override
  public void handle(String sampleParameter) {

    System.out.println("ConcreteStateB handle ：" + sampleParameter);
  }

}
