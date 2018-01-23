package com.lzp.atomic;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * @Description: 基于zookeeper的分布式计数器
 * @Author: lzp
 * @Date: 2018/1/23 15:05
 * @Version V1.0
 */
public class CuratorAtomicInteger {

    /** zookeeper地址 */
    static final String CONNECT_ADDR = "192.168.40.40:2181,192.168.40.41:2181,192.168.40.42:2181";
    /** session超时时间 */
    static final int SESSION_OUTTIME = 5000;//ms

    public static void main(String[] args) throws Exception{
        //1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        //2 通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_ADDR)
                .sessionTimeoutMs(SESSION_OUTTIME)
                .retryPolicy(retryPolicy)
                .build();
        //3 开启连接
        cf.start();

        DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(cf, "/super", new RetryNTimes(3, 1000));
        AtomicValue<Integer> value = atomicInteger.add(1);
        System.out.println(value.succeeded());
        System.out.println(value.preValue());
        System.out.println(value.postValue());

    }
}
