package com.lzp.disruptor.use;


import com.lmax.disruptor.EventHandler;
import com.lzp.disruptor.ringbuffer.Trade;

public class Handler3 implements EventHandler<Trade> {
    @Override  
    public void onEvent(Trade event, long sequence,  boolean endOfBatch) throws Exception {  
    	System.out.println("handler3: name: " + event.getName() + " , price: " + event.getPrice() + ";  instance: " + event.toString());
    }  
}
