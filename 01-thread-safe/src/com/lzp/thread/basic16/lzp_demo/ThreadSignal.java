package com.lzp.thread.basic16.lzp_demo;

/**
 * 循环打印出A-->B-->C  100次
 */
public class ThreadSignal {
    private int count = 1;

    public  void printA() throws Exception {
        synchronized (this) {
            while(count != 1) {
                wait();
            }
            System.out.println("A");
            Thread.sleep(500);
            count = 2;
            notifyAll();
        }
    }

    public  void printB() throws Exception{
        synchronized (this) {
            while(count != 2) {
                wait();
            }
            System.out.println("B");
            Thread.sleep(500);
            count = 3;
            notifyAll();
        }
    }

    public  void printC() throws Exception{
        synchronized (this) {
            while(count != 3) {
                wait();
            }
            System.out.println("C");
            Thread.sleep(500);
            count = 1;
            notifyAll();
        }
    }

    public static void main(String[] args) throws  Exception {
        final ThreadSignal signal = new ThreadSignal();

        new Thread(new Runnable() {
            @Override
            public void run(){
                for (int i = 1 ;i <= 100;i++) {
                    try {
                        signal.printA();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1 ;i <= 100;i++) {
                    try {
                        signal.printB();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        for (int i = 1 ;i <= 100;i++) {
            signal.printC();
        }
    }

}
