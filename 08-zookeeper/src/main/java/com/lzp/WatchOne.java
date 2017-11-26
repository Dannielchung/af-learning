package com.lzp;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @Description: 一次性监控数据改变
 * @Author: lzp
 * @Date: 2017/11/25 17:15
 * @Version V1.0
 */
public class WatchOne {
    private static final String CONNECT_STRING = "192.168.40.40:2181";
    private int sessionTimeout = 50 * 1000;
    private ZooKeeper zk;
    private static final String PATH = "/lzpNode";

    private static final Logger logger = Logger.getLogger(WatchOne.class);

    public ZooKeeper startZK() throws IOException {
        // String connectString, int sessionTimeout, Watcher watcher
        return new ZooKeeper(CONNECT_STRING, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

    public void createNode(String nodePath,String nodeValue) throws KeeperException, InterruptedException {
        //String path, byte[] data, List<ACL> acl, CreateMode createMode
        zk.create(nodePath,nodeValue.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public String getNode(final String nodePath) throws KeeperException, InterruptedException {
        //String path, Watcher watcher, Stat stat\
        String retValue = null;
        byte[] data = zk.getData(nodePath, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    triggerValue(nodePath);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, new Stat());
        retValue = new String(data);
        return retValue;
    }

    public void triggerValue(String nodePath) throws KeeperException, InterruptedException {
        String retValue = null;

        byte[] byteArray = zk.getData(nodePath,false, new Stat());
        retValue = new String(byteArray);

        logger.debug("********************triggerValue*: "+retValue);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        WatchOne watchOne = new WatchOne();

        watchOne.setZk(watchOne.startZK());

        if(watchOne.getZk().exists(PATH,false) == null) {
            watchOne.createNode(PATH,"AAAA");
            logger.debug("*************main result: "+watchOne.getNode(PATH));
        }

        Thread.sleep(Long.MAX_VALUE);
    }


    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }
}
