package com.qin.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by DELL on 2017/11/18.
 */
public class RedisApi {

    private static JedisPool pool;
    private static Properties prop=null;

    private static JedisPoolConfig config=null;

    static {
        InputStream inputStream=RedisApi.class.getClassLoader().getResourceAsStream("redis.properties");

        prop=new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        config=new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(prop.getProperty("MAX_TOTAL")));
        config.setMaxIdle(Integer.valueOf(prop.getProperty("MAX_IDLE")));

        config.setMaxWaitMillis(Integer.valueOf(prop.getProperty("MAX_WAIT_MILLIS")));
        config.setTestOnBorrow(Boolean.valueOf(prop.getProperty("TEST_ON_BORROW")));
        config.setTestOnReturn(Boolean.valueOf(prop.getProperty("TEST_ON_RETURN")));
        config.setTestWhileIdle(Boolean.valueOf(prop.getProperty("TEST_WHILE_IDLE")));
    }

    public static void createJedisPool(String address){
        String[] array=address.split(":");
        pool=new JedisPool(config,array[0],Integer.valueOf(array[1]),100000);
    }

    public static JedisPool getPool(){
        if(pool==null){
            pool=new JedisPool(config,prop.getProperty("REDIS_IP"),Integer.valueOf(prop.getProperty("REDIS_PORT")));
        }
        return pool;
    }


    public static void publish(String channel,String msg){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            jedis.publish(channel,msg);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public static void subscribe(JedisPubSub jedisPubSub,String... channels){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            jedis.subscribe(jedisPubSub,channels);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public static Long hdel(String key,String key1){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            return jedis.hdel(key, key1);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public static String get(String key){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            return jedis.get(key);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }


    public static boolean exists(String key){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            return jedis.exists(key);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public static String set(String key,String value){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            return jedis.set(key, value);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public static String set(String key,String value,int expire){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            jedis.expire(key, expire);
            return jedis.set(key, value);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }


    public static Long del(String key){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            return jedis.del(key);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public static Long lpush(String key,String... strings){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            return jedis.lpush(key, strings);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public static List<String> lrange(String key){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            return jedis.lrange(key, 0, -1);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public static String hmset(String key,Map<String,String> map){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            return jedis.hmset(key, map);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    public static List<String> hmget(String key,String... strings){
        Jedis jedis=null;
        try {
            jedis = getPool().getResource();
            return jedis.hmget(key, strings);
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }
}
