package com.lzp.thread.basic11.master_worker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {

    //1、需要一个乘任务的容器
    private ConcurrentLinkedQueue<Task> workQueues = new ConcurrentLinkedQueue<>();

    //2、需要一个容器，去装worker线程
    private HashMap<String, Thread> workers = new HashMap<>();

    //3、需要一个容器，去装所有worker的结果
    private ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

    //4、构造方法，指定worker线程的数量并构造worker线程
    public Master(Worker worker, int workerCount) {
        worker.setWorkQueues(workQueues);
        worker.setResultMap(resultMap);
        for (int i = 1; i <= workerCount; i++) {
            workers.put("子节点" + i, new Thread(worker));
        }
    }

    //5、需要一个提交方法
    public void submit(Task task) {
        workQueues.add(task);
    }

    //6、需要一个开始方法(启动应用程序，让所有worker工作)
    public void execute() {
        for (Map.Entry<String, Thread> me : workers.entrySet()) {
            me.getValue().start();
        }
    }


    //7、需要一个判断worker是否执行完成的方法
    public boolean isComplete() {
        for (Map.Entry<String, Thread> me : workers.entrySet()) {
            if (me.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    //8、需要一个方法 返回最终的结果集
    public int getResult() {
        int ret = 0;
        for (Map.Entry<String, Object> me : resultMap.entrySet()) {
            ret += (Integer) me.getValue();
        }
        return ret;
    }


}
