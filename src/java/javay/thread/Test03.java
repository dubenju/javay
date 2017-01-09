package javay.thread;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 仓库类Storage实现缓冲区
 *
 */
class Storage03 {
    // 仓库最大存储量
    private final int MAX_SIZE = 100;

    // 仓库存储的载体
    private LinkedBlockingQueue<Object> list = new LinkedBlockingQueue<Object>(100);

    // 生产num个产品
    public void produce(String name, int num) {
        // 如果仓库剩余容量为0
        if (list.size() == MAX_SIZE) {
            System.out.println(name + "【库存量】:" + MAX_SIZE + "/t暂时不能执行生产任务!");
        }

        // 生产条件满足情况下，生产num个产品
        for (int i = 1; i <= num; ++i) {
            try {
                // 放入产品，自动阻塞
                list.put(new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + "【现仓储量为】:" + list.size());
    }

    // 消费num个产品
    public void consume(String name, int num) {
        // 如果仓库存储量不足
        if (list.size() == 0) {
            System.out.println(name + "【库存量】:0/t暂时不能执行生产任务!");
        }

        // 消费条件满足情况下，消费num个产品
        for (int i = 1; i <= num; ++i) {
            try {
                // 消费产品，自动阻塞
                list.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name + "【现仓储量为】:" + list.size());
    }

    // set/get方法
    public LinkedBlockingQueue<Object> getList() {
        return list;
    }

    public void setList(LinkedBlockingQueue<Object> list) {
        this.list = list;
    }

    public int getMAX_SIZE() {
        return MAX_SIZE;
    }
}
/**
 * 生产者类Producer继承线程类Thread
 *
 */
class Producer03 extends Thread {
    // 每次生产的产品数量
    private int num;
    private String nm;
    // 所在放置的仓库
    private Storage03 storage;

    // 构造函数，设置仓库
    public Producer03(Storage03 storage, String name) {
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

    public Storage03 getStorage() {
        return storage;
    }

    public void setStorage(Storage03 storage) {
        this.storage = storage;
    }
}
/**
 * 消费者类Consumer继承线程类Thread
 */
class Consumer03 extends Thread {
    // 每次消费的产品数量
    private int num;
    private String nm;
    // 所在放置的仓库
    private Storage03 storage;

    // 构造函数，设置仓库
    public Consumer03(Storage03 storage, String name) {
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

    public Storage03 getStorage() {
        return storage;
    }

    public void setStorage(Storage03 storage) {
        this.storage = storage;
    }
}
public class Test03 {
    public static void main(String[] args) {
        // 仓库对象
        Storage03 storage = new Storage03();

        // 生产者对象
        Producer03 p1 = new Producer03(storage, "p1");
        Producer03 p2 = new Producer03(storage, "p2");
        Producer03 p3 = new Producer03(storage, "p3");
        Producer03 p4 = new Producer03(storage, "p4");
        Producer03 p5 = new Producer03(storage, "p5");
        Producer03 p6 = new Producer03(storage, "p6");
        Producer03 p7 = new Producer03(storage, "p7");

        // 消费者对象
        Consumer03 c1 = new Consumer03(storage, "C1");
        Consumer03 c2 = new Consumer03(storage, "C2");
        Consumer03 c3 = new Consumer03(storage, "C3");

        // 设置生产者产品生产数量
        p1.setNum(10);
        p2.setNum(10);
        p3.setNum(10);
        p4.setNum(10);
        p5.setNum(10);
        p6.setNum(10);
        p7.setNum(80);

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
