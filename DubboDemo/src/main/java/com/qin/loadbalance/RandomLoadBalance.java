package com.qin.loadbalance;

import java.util.List;
import java.util.Random;

/** 随机的负载均衡算法
 * Created by DELL on 2017/11/19.
 */
public class RandomLoadBalance extends LoadBalance {

    public NodeInfo doSelect(List<String> registryInfo) {
        Random random=new Random();
        int index = random.nextInt(registryInfo.size());
        String registry=registryInfo.get(index);

        return getNodeInfo(registry);
    }
}
