package com.lzp;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

import java.io.IOException;
import java.util.List;

/**
 * @Description: 每次监控子节点的改变
 * @Author: lzp
 * @Date: 2017/11/25 17:15
 * @Version V1.0
 */
public class WatchChildrenNode {
    private static final String CONNECT_STRING = "192.168.40.40:2181";
    private int sessionTimeout = 50 * 1000;
    private ZooKeeper zk;
    private static final String PATH = "/lzpNode";

    private static final Logger logger = Logger.getLogger(WatchChildrenNode.class);

    public ZooKeeper startZK() throws IOException {
        // String connectString, int sessionTimeout, Watcher watcher
        return new ZooKeeper(CONNECT_STRING, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged && watchedEvent.getPath().equals(PATH)) {
                    showChildren(PATH);
                }else {
                    showChildren(PATH);
                }
            }
        });
    }

    public void showChildren(String nodePath) {
        List<String> children = null;
        try {
            children = zk.getChildren(nodePath, true);
            logger.debug("*************showChildNode: "+children);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void createNode(String nodePath,String nodeValue) throws KeeperException, InterruptedException {
        //String path, byte[] data, List<ACL> acl, CreateMode createMode
        zk.create(nodePath,nodeValue.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        WatchChildrenNode watchChildrenNode = new WatchChildrenNode();

        watchChildrenNode.setZk(watchChildrenNode.startZK());

        Thread.sleep(Long.MAX_VALUE);
    }


    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

}
