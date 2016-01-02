package javay.state;

public class Context {
	//持有一个State类型的对象实例
    private State2 state;

    public void setState(State2 state) {
        this.state = state;
    }
    /**
     * 用户感兴趣的接口方法
     */
    public void request(String sampleParameter) {
        //转调state来处理
        state.handle(sampleParameter);
    }
}
