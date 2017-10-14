package com.lzp.thread.basic01.safe;
/**
 * 业务整体需要使用完整的synchronized，保持业务的原子性。
 * MyThread04 脏读
 */
public class MyThread04 {

	private String username = "bjsxt";
	private String password = "123";
	
	public synchronized void setValue(String username, String password){
		this.username = username;
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.password = password;
		
		System.out.println("setValue最终结果：username = " + username + " , password = " + password);
	}

	// synchronized
	public synchronized void getValue(){
		System.out.println("getValue方法得到：username = " + this.username + " , password = " + this.password);
	}

	public static void main(String[] args) throws Exception{
		/**
		 * 场景1：setValue 加synchronized，getValue不加synchronized  结果：会出现线程安全问题(脏读)
		 * 场景2：setValue 加synchronized，getValue加synchronized  结果：等执行setValue，才会执行 getValue，设值取值一致。
		 *
		 * 分析：
		 * 		在我们对一个对象的方法加锁的时候，需要考虑业务的整体性，
		 * 		即为setValue和getValue方法同时加锁synchronized，保证业务的原子性，
		 * 		不然会出现业务错误(也从侧面保证业务的一致性)
		 *
		 */
		final MyThread04 dr = new MyThread04();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				dr.setValue("z3", "456");
			}
		});
		t1.start();
		Thread.sleep(1000);
		
		dr.getValue();
	}
	
	
	
}
