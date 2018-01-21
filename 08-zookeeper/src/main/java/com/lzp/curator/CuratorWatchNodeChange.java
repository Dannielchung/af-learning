package com.lzp.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Description:
 * @Author: lzp
 * @Date: 2018/1/21 14:04
 * @Version V1.0
 */
public class CuratorWatchNodeChange {

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

        //4 建立一个cache缓存
        //ublic NodeCache(CuratorFramework client, String path, boolean dataIsCompressed)
        //CuratorFramework client：连接
        //String path : 节点的路径
        //boolean dataIsCompressed: 是否压缩数据
        final NodeCache cache = new NodeCache(cf, "/super", false);
        //public void  start(boolean buildInitial) throws Exception
        //boolean buildInitial:是否构建的时候初始化
        cache.start(true);
        cache.getListenable().addListener(new NodeCacheListener() {
            /**
             * 触发事件为创建节点和更新节点，在删除节点的时候并不触发此操作。
             */
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("路径为：" + cache.getCurrentData().getPath());
                System.out.println("数据为：" + new String(cache.getCurrentData().getData()));
                System.out.println("状态为：" + cache.getCurrentData().getStat());
                System.out.println("---------------------------------------");

            }
        });

        Thread.sleep(1000);
        cf.create().forPath("/super", "123".getBytes());

        Thread.sleep(1000);
        cf.setData().forPath("/super", "456".getBytes());

        Thread.sleep(1000);
        cf.delete().forPath("/super");

        Thread.sleep(Integer.MAX_VALUE);
    }
}
