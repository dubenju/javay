package javay.state;

public class FriendState implements State {
	@Override public String sayHello(){
        return "你好，我的朋友";
    }
    @Override public String sayGoodbye(){
        return "再抱抱！";
    }
}
