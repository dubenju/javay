package javay.thread;

import java.util.LinkedList;

/**
 * 仓库类Storage实现缓冲区
 */
class Storage01
{
    // 仓库最大存储量
    private final int MAX_SIZE = 100;

    // 仓库存储的载体
    private LinkedList<Object> list = new LinkedList<Object>();

    // 生产num个产品
    public void produce(String name, int num) {
        // 同步代码段
        synchronized (list) {
            // 如果仓库剩余容量不足
            while (list.size() + num > MAX_SIZE) {
                System.out.println(name + "【要生产的产品数量】:" + num + "/t【库存量】:" + list.size() + "/t暂时不能执行生产任务!");
                try {
                    // 由于条件不满足，生产阻塞
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 生产条件满足情况下，生产num个产品
            for (int i = 1; i <= num; ++i) {
                list.add(new Object());
            }
            System.out.println(name + "【已经生产产品数】:" + num + "/t【现仓储量为】:" + list.size());

            list.notifyAll();
        }
    }

    // 消费num个产品
    public void consume(String name, int num) {
        // 同步代码段
        synchronized (list) {
            // 如果仓库存储量不足
            while (list.size() < num) {
                System.out.println(name + "【要消费的产品数量】:" + num + "/t【库存量】:" + list.size() + "/t暂时不能执行生产任务!");
                try {
                    // 由于条件不满足，消费阻塞
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 消费条件满足情况下，消费num个产品
            for (int i = 1; i <= num; ++i) {
                list.remove();
            }
            System.out.println(name + "【已经消费产品数】:" + num + "/t【现仓储量为】:" + list.size());

            list.notifyAll();
        }
    }

    // get/set方法
    public LinkedList<Object> getList() {
        return list;
    }

    public void setList(LinkedList<Object> list) {
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
class Producer01 extends Thread {
    // 每次生产的产品数量
    private int num;
    private String nm;
    // 所在放置的仓库
    private Storage01 storage;

    // 构造函数，设置仓库
    public Producer01(Storage01 storage, String name) {
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

    public Storage01 getStorage() {
        return storage;
    }

    public void setStorage(Storage01 storage) {
        this.storage = storage;
    }
}
/**
 * 消费者类Consumer继承线程类Thread
 */
class Consumer01 extends Thread {
    // 每次消费的产品数量
    private int num;
    private String nm;
    // 所在放置的仓库
    private Storage01 storage;

    // 构造函数，设置仓库
    public Consumer01(Storage01 storage, String name) {
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

    public Storage01 getStorage() {
        return storage;
    }

    public void setStorage(Storage01 storage) {
        this.storage = storage;
    }
}
/**
 * 测试类Test
 *
 *
 */
public class Test01 {
    public static void main(String[] args) {
        // 仓库对象
        Storage01 storage = new Storage01();

        // 生产者对象
        Producer01 p1 = new Producer01(storage, "p1");
        Producer01 p2 = new Producer01(storage, "p2");
        Producer01 p3 = new Producer01(storage, "p3");
        Producer01 p4 = new Producer01(storage, "p4");
        Producer01 p5 = new Producer01(storage, "p5");
        Producer01 p6 = new Producer01(storage, "p6");
        Producer01 p7 = new Producer01(storage, "p7");

        // 消费者对象
        Consumer01 c1 = new Consumer01(storage, "C1");
        Consumer01 c2 = new Consumer01(storage, "C2");
        Consumer01 c3 = new Consumer01(storage, "C3");

        // 设置生产者产品生产数量
        p1.setNum(10);
        p2.setNum(10);
        p3.setNum(10);
        p4.setNum(10);
        p5.setNum(10);
        p6.setNum(10);
//        p7.setNum(80);

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
