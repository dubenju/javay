package javay.state;

public class Man2 {
  private State state;
  public String sayHello() {
    return state.sayHello();
  }
  public String sayGoodbye() {
    return state.sayGoodbye();
  }

  public static void main(String[] args) {
    Man2 one = new Man2();
    one.state  =new FriendState();//isHappy = true
    System.out.println(one.sayHello());
    System.out.println(one.sayGoodbye());
  }
}
