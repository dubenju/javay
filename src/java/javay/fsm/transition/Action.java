package javay.fsm.transition;

public interface Action<T> {
	public T doAction(T in);
}
