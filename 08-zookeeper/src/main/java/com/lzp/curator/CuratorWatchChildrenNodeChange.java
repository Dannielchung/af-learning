package com.lzp.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Description:
 * @Author: lzp
 * @Date: 2018/1/21 14:04
 * @Version V1.0
 */
public class CuratorWatchChildrenNodeChange {

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

        //4 建立一个PathChildrenCache缓存，第三个参数是否为接收节点数据内容
        PathChildrenCache cache = new PathChildrenCache(cf, "/super", false);
        //5 在初始化的时候就进行缓存监听
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            /**
             * 新建、修改、删除
             */
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED :" + event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED :" + event.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED :" + event.getData().getPath());
                        break;
                    default:
                        break;
                }
            }
        });

        //创建本身节点不发生变化
        cf.create().forPath("/super", "init".getBytes());

        //添加子节点
        Thread.sleep(1000);
        cf.create().forPath("/super/c1", "c1内容".getBytes());
        Thread.sleep(1000);
        cf.create().forPath("/super/c2", "c2内容".getBytes());

        //修改子节点
        Thread.sleep(1000);
        cf.setData().forPath("/super/c1", "c1更新内容".getBytes());

        //删除子节点
        Thread.sleep(1000);
        cf.delete().forPath("/super/c2");

        //删除本身节点
        Thread.sleep(1000);
        cf.delete().deletingChildrenIfNeeded().forPath("/super");

        Thread.sleep(Integer.MAX_VALUE);
    }
}
