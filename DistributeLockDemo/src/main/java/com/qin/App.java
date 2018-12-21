package com.qin;

import com.qin.lock.DistributeLock;
import com.qin.lock.ReadWriteLock;
import com.qin.lock.ZKReadWriteLock;

import java.util.concurrent.CountDownLatch;

/**
 * Hello world!
 *
 */
public class App 
{
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main( String[] args ) throws Exception {
//        ZooKeeper zooKeeper = new ZooKeeper("192.168.0.170:2191,192.168.0.170:2192,192.168.0.170:2193", 1000, watchedEvent -> {
//            if (Watcher.Event.KeeperState.SyncConnected == watchedEvent.getState()) {
//                connectedSemaphore.countDown();
//            }
//        });
//
//        connectedSemaphore.await();
//
//        Stat stat = zooKeeper.exists("/test", e->{
//            System.out.println(e);
//        });
//
//        System.out.println(stat);

        ReadWriteLock readWriteLock = new ZKReadWriteLock("192.168.0.170:2191,192.168.0.170:2192,192.168.0.170:2193");

        CountDownLatch countDownLatch = new CountDownLatch(100);

        for(int i=0;i<50;i++){
            int finalI = i;
            new Thread(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DistributeLock readLock = readWriteLock.readLock();

                try {
                    readLock.lock();
                    System.out.println("Read lock "+ finalI);
                    readLock.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            countDownLatch.countDown();
        }

        for(int i=50;i<100;i++){
            int finalI = i;
            new Thread(()->{
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DistributeLock writeLock = readWriteLock.writeLock();

                try {
                    writeLock.lock();
                    System.out.println("Wirte lock "+ finalI);
                    writeLock.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            countDownLatch.countDown();
        }
        System.in.read();
    }
}
