package com.lzp.thread.basic01.safe;

/**
 * 对象锁的同步和异步问题
 *
 *	同步(synchronized)：同步的概念就是共享，如果不是共享资源，就没有必要进行同步。
 *	异步(asynchronized)：异步的概念就是独立，相互之间不受到任何制约。
 *						比如：学习http的时候，在页面发起ajax请求，我们还可以继续浏览或操作页面的内容，二者之间没有任何关系。
 *
 *	同步的目的：为了线程安全
 *	对于线程安全来说，需要满足两个特性：原子性(同步) 和 可见性
 */
public class MyThread03 {

	public synchronized void method1(){
		try {
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/** synchronized */
	public void method2(){
			System.out.println(Thread.currentThread().getName());
	}
	
	public static void main(String[] args) {
		
		final MyThread03 mo = new MyThread03();
		
		/**
		 * 场景1：method1方法加synchronized，method2不加synchronized 结果：t1和t2都能及时访问对应的方法
		 * 场景2：method1方法加synchronized，method2加synchronized   结果：访问t1线程后，睡4秒，才t2线程访问method2
		 *
		 * 分析：
		 * 		t1线程先持有object对象的Lock锁，t2线程可以以异步的方式调用对象中的非synchronized修饰的方法
		 * 		t1线程先持有object对象的Lock锁，t2线程如果在这个时候调用对象中的同步（synchronized）方法则需等待，也就是同步
		 */
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				mo.method1();
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				mo.method2();
			}
		},"t2");
		
		t1.start();
		t2.start();
		
	}
	
}
