package com.lzp.thread.basic11.master_worker;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        Master master = new Master(new MyWorker(), Runtime.getRuntime().availableProcessors());
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            Task task = new Task();
            task.setId(i);
            task.setName("任务" + i);
            task.setPrice(random.nextInt(1000));
            master.submit(task);
        }

        master.execute();

        long start = System.currentTimeMillis();
        while (true) {
            if (master.isComplete()) {
                long end = System.currentTimeMillis() - start;
                int result = master.getResult();
                System.out.println("最终结果：" + result + ",最终耗时：" + end);
                break;
            }
        }
    }

}
