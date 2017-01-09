package javay.thread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 仓库类Storage实现缓冲区
 */
class Storage02 {
    // 仓库最大存储量
    private final int MAX_SIZE = 100;

    // 仓库存储的载体
    private LinkedList<Object> list = new LinkedList<Object>();

    // 锁
    private final Lock lock = new ReentrantLock();

    // 仓库满的条件变量
    private final Condition full = lock.newCondition();
    // 仓库空的条件变量
    private final Condition empty = lock.newCondition();

    // 生产num个产品
    public void produce(String name, int num) {
        // 获得锁
        lock.lock();

        // 如果仓库剩余容量不足
        while (list.size() + num > MAX_SIZE) {
            System.out.println(name + "【要生产的产品数量】:" + num + "/t【库存量】:" + list.size() + "/t暂时不能执行生产任务!");
            try {
                // 由于条件不满足，生产阻塞
                full.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 生产条件满足情况下，生产num个产品
        for (int i = 1; i <= num; ++i) {
            list.add(new Object());
        }
        System.out.println(name + "【已经生产产品数】:" + num + "/t【现仓储量为】:" + list.size());

        // 唤醒其他所有线程
        full.signalAll();
        empty.signalAll();

        // 释放锁
        lock.unlock();
    }

    // 消费num个产品
    public void consume(String name, int num) {
        // 获得锁
        lock.lock();

        // 如果仓库存储量不足
        while (list.size() < num) {
            System.out.println(name + "【要消费的产品数量】:" + num + "/t【库存量】:" + list.size() + "/t暂时不能执行生产任务!");
            try {
                // 由于条件不满足，消费阻塞
                empty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 消费条件满足情况下，消费num个产品
        for (int i = 1; i <= num; ++i) {
            list.remove();
        }
        System.out.println(name + "【已经消费产品数】:" + num + "/t【现仓储量为】:" + list.size());

        // 唤醒其他所有线程
        full.signalAll();
        empty.signalAll();

        // 释放锁
        lock.unlock();
    }

    // set/get方法
    public int getMAX_SIZE() {
        return MAX_SIZE;
    }

    public LinkedList<Object> getList() {
        return list;
    }

    public void setList(LinkedList<Object> list) {
        this.list = list;
    }
}
/**
 * 生产者类Producer继承线程类Thread
 *
 */
class Producer02 extends Thread {
    // 每次生产的产品数量
    private int num;
    private String nm;
    // 所在放置的仓库
    private Storage02 storage;

    // 构造函数，设置仓库
    public Producer02(Storage02 storage, String name) {
        this.storage = storage;
        this.nm = name;
    }

    // 线程run函数
    public void run() {
        produce(num);
    }

    // 调用仓库Storage的生产函数
    public void produce(int num) {
        storage.produce(this.nm, num);
    }

    // get/set方法
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Storage02 getStorage() {
        return storage;
    }

    public void setStorage(Storage02 storage) {
        this.storage = storage;
    }
}
/**
 * 消费者类Consumer继承线程类Thread
 */
class Consumer02 extends Thread {
    // 每次消费的产品数量
    private int num;
    private String nm;
    // 所在放置的仓库
    private Storage02 storage;

    // 构造函数，设置仓库
    public Consumer02(Storage02 storage, String name) {
        this.storage = storage;
        this.nm = name;
    }

    // 线程run函数
    public void run() {
        consume(num);
    }

    // 调用仓库Storage的生产函数
    public void consume(int num) {
        storage.consume(this.nm, num);
    }

    // get/set方法
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Storage02 getStorage() {
        return storage;
    }

    public void setStorage(Storage02 storage) {
        this.storage = storage;
    }
}
public class Test02 {
	   public static void main(String[] args) {
	        // 仓库对象
	        Storage02 storage = new Storage02();

	        // 生产者对象
	        Producer02 p1 = new Producer02(storage, "p1");
	        Producer02 p2 = new Producer02(storage, "p2");
	        Producer02 p3 = new Producer02(storage, "p3");
	        Producer02 p4 = new Producer02(storage, "p4");
	        Producer02 p5 = new Producer02(storage, "p5");
	        Producer02 p6 = new Producer02(storage, "p6");
	        Producer02 p7 = new Producer02(storage, "p7");

	        // 消费者对象
	        Consumer02 c1 = new Consumer02(storage, "C1");
	        Consumer02 c2 = new Consumer02(storage, "C2");
	        Consumer02 c3 = new Consumer02(storage, "C3");

	        // 设置生产者产品生产数量
	        p1.setNum(10);
	        p2.setNum(10);
	        p3.setNum(10);
	        p4.setNum(10);
	        p5.setNum(10);
	        p6.setNum(10);
//	        p7.setNum(80);

	        // 设置消费者产品消费数量
	        c1.setNum(50);
	        c2.setNum(20);
	        c3.setNum(30);

	        // 线程开始执行
	        c1.start();
	        c2.start();
	        c3.start();
	        p1.start();
	        p2.start();
	        p3.start();
	        p4.start();
	        p5.start();
	        p6.start();
	        p7.start();
	   }
}
