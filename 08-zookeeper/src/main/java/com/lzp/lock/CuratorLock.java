package com.lzp.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Description:
 * @Author: lzp
 * @Date: 2018/1/23 9:55
 * @Version V1.0
 */
public class CuratorLock {

    /** zookeeper地址 */
    static final String CONNECT_ADDR = "192.168.40.40:2181,192.168.40.41:2181,192.168.40.42:2181";
    /** session超时时间 */
    static final int SESSION_OUTTIME = 5000;//ms

    public static void main(String[] args) throws Exception {
        //1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //2 通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_ADDR)
                .sessionTimeoutMs(SESSION_OUTTIME)
                .retryPolicy(retryPolicy)
                .build();
        //3 建立连接
        cf.start();

        /**
         * InterProcessMutex 是线程安全的，一个JVM创建一个就好。
         * mylock为锁的根目录，我们可以针对不同业务创建不同的根目录
         */
        InterProcessMutex lock = new InterProcessMutex(cf, "/mylock");
        try {
            //阻塞方法，获取不到锁的线程会挂起
            lock.acquire();
            System.out.println("已经获取到锁");
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁，必须要放到finally里面，已确保上面方法出现异常时也能够释放锁
            lock.release();
        }

        Thread.sleep(10000);
        cf.close();
    }
}
