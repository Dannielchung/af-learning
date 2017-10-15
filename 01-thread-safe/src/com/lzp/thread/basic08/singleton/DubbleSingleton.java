package com.lzp.thread.basic08.singleton;

public class DubbleSingleton {

	private volatile static DubbleSingleton ds;

	private DubbleSingleton(){}
	
	public static DubbleSingleton getIntance(){
		if(ds == null){
			synchronized (DubbleSingleton.class) {
				if(ds == null){
					ds = new DubbleSingleton();
				}
			}
		}
		return ds;
	}
	
}
