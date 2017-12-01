package com.qin.redis;

import com.qin.configBean.Reference;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by DELL on 2017/11/22.
 */
public class RedisServer extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        System.out.println(message);
        //动态添加服务节点信息
        Reference.getRegistryInfo().add(message);
    }
}
