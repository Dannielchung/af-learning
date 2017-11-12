package com.lzp.thread.basic01.safe;

/**
 * 线程安全概念：当多个线程访问某一个类(对象或方法)时，这个类(对象或方法)始终都能表现出正确的行为，那么这个类(对象或方法)就是线程安全的。
 * <p>
 * synchronized： 可以在任意对象及方法上加锁，而加锁的这段代码成为"互斥区"或"临界区"
 */
public class MyThread01 extends Thread {

    private int count = 5;

    // synchronized加锁
    public synchronized void run() {
        count--;
        System.out.println(this.currentThread().getName() + " count=" + count);
    }

    /**
     * 分析：当多个线程访问thread的run方法时，以排队的方式进行处理(这里排队时按照cpu分配的先后顺序而定的)
     * 一个线程想要执行执行synchronized修饰的方法里的代码：
     * 1：尝试获得锁
     * 2：如果拿到锁，执行synchronized代码体的内容
     * 如果拿不到锁，这个线程会不断的尝试获得这把锁，直到拿到为止。
     * 而且是多个线程同时去竞争这把锁(产生锁竞争问题)。
     * <p>
     * 锁竞争问题：
     * 假如n个线程，当第一个线程释放锁的时候，剩下n-1个线程竞争这把锁
     * 结果会导致这个程序的cpu使用率达到一个很高的值，最后程序会很慢，甚至宕机。
     * 应该避免锁竞争。
     */
    public static void main(String[] args) {
        MyThread01 thread = new MyThread01();

        Thread t1 = new Thread(thread, "t1");
        Thread t2 = new Thread(thread, "t2");
        Thread t3 = new Thread(thread, "t3");
        Thread t4 = new Thread(thread, "t4");
        Thread t5 = new Thread(thread, "t5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}
