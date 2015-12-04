package javay.util.heap;

/**
 * 堆
 * @author DBJ(dubenju@126.com)
 */
public interface Heap<E> {
	public void build();
	public void insert(E element);
	public void update();
	public void decreaseKey();
	public E get();
	public void  findMin();
	public void  findMax();
	public void heapify();
	public E delete();
	public void merge();
	public boolean isEmpty();
}
