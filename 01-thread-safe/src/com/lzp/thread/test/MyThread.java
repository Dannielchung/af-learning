package com.lzp.thread.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: lzp
 * @Date: 2017/11/5 21:51
 * @Version V1.0
 */
public class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        FutureTask<Integer> futureTask = new FutureTask<>(myThread);
        new Thread(futureTask).start();
        try {
            Integer result = futureTask.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
