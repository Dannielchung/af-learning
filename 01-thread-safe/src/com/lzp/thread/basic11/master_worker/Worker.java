package com.lzp.thread.basic11.master_worker;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements Runnable {
    private ConcurrentLinkedQueue<Task> workQueues;
    private ConcurrentHashMap<String, Object> resultMap;

    public void setWorkQueues(ConcurrentLinkedQueue<Task> workQueues) {
        this.workQueues = workQueues;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void run() {
        while (true) {
            Task input = this.workQueues.poll();
            if (input == null) break;

            //真正的处理业务逻辑
            Object output = handle(input);

            this.resultMap.put(Integer.toString(input.getId()), output);
        }
    }

//    private Object handle(Task input) {
//        Object reObj = null;
//        try {
//            // 这里：是处理task任务，可能是数据加工，可能是查数据库...
//            Thread.sleep(500);
//            reObj = input.getPrice();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return reObj;
//    }

    public Object handle(Task input) {
        return null;
    }
}
