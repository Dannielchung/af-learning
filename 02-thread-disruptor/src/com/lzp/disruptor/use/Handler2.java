package com.lzp.disruptor.use;


import com.lmax.disruptor.EventHandler;
import com.lzp.disruptor.ringbuffer.Trade;

public class Handler2 implements EventHandler<Trade> {
	  
    @Override  
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
    	System.out.println("handler2: set price");
    	event.setPrice(17.0);
    	Thread.sleep(1000);
    }  
      
}  