package com.lzp.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Description:
 *   问题1：那如果NODE0拿到了锁，而在执行锁内业务的时候 服务器挂了之类的行为，那那个NODE不是永远不会被删除，那后面的节点不是一直阻塞在那了吗？
 *              这是个零时节点，如果服务器没有收到客户端的心跳连接，则服务器会依据sessionid，将该节点删除的
 * @Author: lzp
 * @Date: 2018/1/23 9:55
 * @Version V1.0
 */
public class CuratorLockThread02 {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            //启动10个线程模拟多个客户端
            JvmLock jl = new JvmLock(i);
            new Thread(jl).start();
            //这里加上300毫秒是为了让线程按顺序启动，不然有可能4号线程比3号线程先启动了，这样测试就不准了。
            Thread.sleep(100);
        }
    }

    public static class JvmLock implements Runnable {

        /** zookeeper地址 */
        static final String CONNECT_ADDR = "192.168.40.40:2181,192.168.40.41:2181,192.168.40.42:2181";
        /** session超时时间 */
        static final int SESSION_OUTTIME = 5000;//ms

        private int num;

        public JvmLock(int num) {
            this.num = num;
        }

        @Override
        public void run() {
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
            InterProcessMutex lock = new InterProcessMutex(cf,"/mylock", new NoFairLockDriver());
            try {
                System.out.println("我是第" + num + "号线程，我开始获取锁");
                lock.acquire();
                System.out.println("我是第" + num + "号线程，我已经获取锁");
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //释放锁，必须要放到finally里面，已确保上面方法出现异常时也能够释放锁
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cf.close();
        }
    }
}
