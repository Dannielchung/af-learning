package com.lzp.thread.basic11.master_worker;

public class MyWorker extends Worker {

    @Override
    public Object handle(Task input) {
        Object reObj = null;
        try {
            // 这里：是处理task任务，可能是数据加工，可能是查数据库...
            Thread.sleep(500);
            reObj = input.getPrice();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reObj;
    }

}
