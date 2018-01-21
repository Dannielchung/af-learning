package com.lzp.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @Description:
 * @Author: lzp
 * @Date: 2018/1/21 11:01
 * @Version V1.0
 */
public class CuratorBase {

    /** zookeeper地址 */
    static final String CONNECT_ADDR = "192.168.40.40:2181,192.168.40.41:2181,192.168.40.42:2181";
    /** session超时时间 */
    static final int SESSION_OUTTIME = 5000;//ms

    public static void main(String[] args) throws Exception{
        //1 重试策略：初试时间为1S，重试10次
        RetryPolicy retry = new ExponentialBackoffRetry(1000, 10);
        //2 通过工厂创建连接
        CuratorFramework cf = CuratorFrameworkFactory.builder()
                .connectString(CONNECT_ADDR)
                .sessionTimeoutMs(SESSION_OUTTIME)
                .retryPolicy(retry)
                .build();
        //3 开启连接
        cf.start();

//        System.out.println(ZooKeeper.States.CONNECTED);
//        System.out.println(cf.getState());

        // 新加、删除
        //4 建立节点 指定节点类型(不加withMode默认为持久类型节点)、路径、数据内容
//        cf.create().creatingParentsIfNeeded()
//                .withMode(CreateMode.PERSISTENT)
//                .forPath("/super/c1","c1内容".getBytes());
//        //5 删除节点
//        cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");


        //读取、修改
        // 创建节点
//        cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c1","c1内容".getBytes());
//        cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c2","c2内容".getBytes());
//        //读取节点
//        String ret1 = new String(cf.getData().forPath("/super/c2"));
//        System.out.println(ret1);
//        //修改节点
//        cf.setData().forPath("/super/c2","c2更新内容".getBytes());
//        String ret2 = new String(cf.getData().forPath("/super/c2"));
//        System.out.println(ret2);

        //绑定回调函数
//        ExecutorService pool = Executors.newCachedThreadPool();
//        cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
//                .inBackground(new BackgroundCallback() {
//                    @Override
//                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
//                        System.out.println("code:"+ curatorEvent.getResultCode());
//                        System.out.println("type:"+ curatorEvent.getType());
//                        System.out.println("当前线程:"+ Thread.currentThread().getName());
//                    }
//                },pool)
//                .forPath("/super/c3","c3内容".getBytes());


        //读取子节点getChildren方法和判断节点是否存在checkExists方法
        List<String> list = cf.getChildren().forPath("/super");
        for(String s : list) {
            System.out.println(s);
        }
        //checkExists()  不存在节点，返回的stat为null。存在则返回的stat结构体信息
        Stat stat = cf.checkExists().forPath("/super");
        System.out.println(stat);

        cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
        Thread.sleep(Integer.MAX_VALUE);
        cf.close();
    }
}
