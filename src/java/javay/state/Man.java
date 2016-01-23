package javay.state;

public class Man {
  private boolean isHappy;//典型的flag，两种状态
  public String sayHello() {
    String greeting="";
    if(isHappy){
      greeting ="你好，我的朋友";
    }else{
      greeting ="好";
    }
    return greeting;
  }
  public String sayGoodbye() {
    return isHappy? "再抱抱！":"再见，再也不见";
  }

  public static void main(String[] args) {
    Man one = new Man();
    one.isHappy  =true;//false
    System.out.println(one.sayHello());
    System.out.println(one.sayGoodbye());
  }
}
