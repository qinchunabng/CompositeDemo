package com.qin.loadbalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询的负载均衡算法
 * Created by DELL on 2017/11/19.
 */
public class RoundRobinLoadBalance extends LoadBalance {

    private static AtomicInteger index=new AtomicInteger(0);

    public NodeInfo doSelect(List<String> registryInfo) {
        synchronized (RoundRobinLoadBalance.class){
            //此处操作分为两步，为了保证多线程环境下安全，需要加锁
            if(index.intValue()>=registryInfo.size()){
                index.set(0);
            }
        }
        String registry=registryInfo.get(index.intValue());
        index.incrementAndGet();
        return getNodeInfo(registry);
    }
}
