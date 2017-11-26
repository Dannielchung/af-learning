package com.lzp;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @Description: zkClient基本api使用
 * @Author: lzp
 * @Date: 2017/11/25 17:15
 * @Version V1.0
 */
public class HelloZK {
    private static final String CONNECT_STRING = "192.168.40.40:2181";
    private int sessionTimeout = 50 * 1000;
    private ZooKeeper zk;
    private static final String PATH = "/lzpNode";

    private static final Logger logger = Logger.getLogger(HelloZK.class);

    public ZooKeeper startZK() throws IOException {
        // String connectString, int sessionTimeout, Watcher watcher
        return new ZooKeeper(CONNECT_STRING,sessionTimeout,null);
    }

    public void stopZK() throws InterruptedException {
        if(zk != null) {
            zk.close();
        }
    }

    public void createNode(String nodePath,String nodeValue) throws KeeperException, InterruptedException {
        //String path, byte[] data, List<ACL> acl, CreateMode createMode
        zk.create(nodePath,nodeValue.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public String getNode(String nodePath) throws KeeperException, InterruptedException {
        //String path, Watcher watcher, Stat stat
        return new String(zk.getData(nodePath,false,new Stat()));
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        HelloZK hello = new HelloZK();

        ZooKeeper zk = hello.startZK();

        hello.setZk(zk);

        if(zk.exists(PATH,false) == null) {
            hello.createNode(PATH,"lzplzp");
            String result = hello.getNode(PATH);
            logger.debug("*************hello result: "+result);
        }else {
            logger.debug("*************i have this node ");
        }

        hello.stopZK();
    }


    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }
}
