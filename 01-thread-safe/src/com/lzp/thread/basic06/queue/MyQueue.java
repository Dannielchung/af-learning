package com.lzp.thread.basic06.queue;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟BlockingQueue数据结构
 */
public class MyQueue {

    //1、需要一个集合装元素
    private LinkedList list = new LinkedList<Object>();
    //2、计数器
    private AtomicInteger count = new AtomicInteger(0);
    //3、容器上限和下限
    private final int minSize = 0;
    private final int maxSize;
    //4、构造方法指定容器的容量大小
    public MyQueue(int size) {
        this.maxSize = size;
    }
    //5、准备对象锁
    final Object lock = new Object();
    //6、put方法
    public void put(Object obj) {
        synchronized (lock) {
            while(count.get() == this.maxSize){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(obj);
            count.incrementAndGet();
            System.out.println("新加入的元素是："+obj);
            //容器有元素了，必须通知其他线程(唤醒)
            lock.notify();
        }
    }

    //7、take方法
    public Object take() {
        Object reObj = null;
        synchronized (lock){
            // 判断,没有元素，则进入等待
            while(count.get() == this.minSize) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            reObj = list.removeFirst();
            System.out.println("新移除的元素是："+reObj);
            count.decrementAndGet();
            //唤醒另外的线程
            lock.notify();
        }
        return reObj;
    }

    //8、获取容器的元素个数
    public int size() {
        return this.count.get();
    }

    public static void main(String[] args) {
        final MyQueue m = new MyQueue(5);
        m.put("a");
        m.put("b");
        m.put("c");
        m.put("d");
        m.put("e");
        System.out.println("当前元素个数：" + m.size());
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                m.put("h");
                m.put("i");
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Object t1 = m.take();
                    //System.out.println("被取走的元素为：" + t1);
                    Thread.sleep(1000);
                    Object t2 = m.take();
                    //System.out.println("被取走的元素为：" + t2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2");

        t1.start();

        try {
            //睡两秒
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();

    }
}
