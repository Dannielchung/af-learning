package com.lzp.thread.basic02.sync;
/**
 * synchronized异常
 *
 * 	对于web应用程序，异常释放锁的情况，如果不及时处理，很可能对你的应用程序业务逻辑产生严重的错误，
 *  比如你现在执行一个队列任务，很多对象都去在等待第一个对象正确执行完毕再去释放锁，
 *  但是第一个对象由于异常的出现，导致业务逻辑没有正常执行就释放了锁，那么可想而知后续的对象执行的都是错误的逻辑。
 *
 */
public class SyncException {

	private int i = 0;
	public synchronized void operation(){
		while(true){
			try {
				i++;
				Thread.sleep(100);
				System.out.println(Thread.currentThread().getName() + " , i = " + i);
				if(i == 20){
					Integer.parseInt("a"); //此处抛出异常
					// throw new RuntimeException();
				}
			} catch (Exception e) {//InterruptedException  跑出此异常，结束
				e.printStackTrace();
				System.out.println("log info i = "+i);
				// throw new RuntimeException();  //到此结束
				//continue;  //继续

				/*
				 * 如果  catch (InterruptedException e)  则线程到此结束   （打断异常）
				 * 如果  throw new RuntimeException();  则线程到此结束
				 * 如果  catch (Exception e)   则线程继续
				 *
				 * 场景：多个业务操作一起执行，其中有个业务失败跑出异常，则希望其他业务可以继续进行。
				 */
			}
		}
	}
	
	public static void main(String[] args) {
		
		final SyncException se = new SyncException();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				se.operation();
			}
		},"t1");
		t1.start();
	}
	
	
}
