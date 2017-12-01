package com.qin.registry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qin.configBean.Protocol;
import com.qin.configBean.Registry;
import com.qin.configBean.Service;
import com.qin.redis.RedisApi;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis注册中心处理类
 * Created by DELL on 2017/11/18.
 */
public class RedisRegistry implements BaseRegistry {

    public boolean registry(String param, ApplicationContext applicationContext) {
        try {
            Protocol protocol = applicationContext.getBean(Protocol.class);
            Map<String, Service> serviceMap = applicationContext.getBeansOfType(Service.class);
            Registry registry = applicationContext.getBean(Registry.class);
            RedisApi.createJedisPool(registry.getAddress());
            for (Map.Entry<String, Service> entry : serviceMap.entrySet()) {
                if (entry.getValue().getRef().equals(param)) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("protocol", JSON.toJSONString(protocol));
                    jsonObject.put("service", JSON.toJSONString(entry.getValue()));

                    JSONObject ipport = new JSONObject();
                    ipport.put(protocol.getHost() + ":" + protocol.getPort(), jsonObject);
                    lpush(ipport, param);
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getRegistry(String id, ApplicationContext applicationContext) {
        Registry registry=applicationContext.getBean(Registry.class);
        RedisApi.createJedisPool(registry.getAddress());
        return RedisApi.lrange(id);
    }


    private void lpush(JSONObject ipport,String key){
        String dataJson=ipport.toJSONString();
        if(RedisApi.exists(key)){
            Set<String> keys = ipport.keySet();
            String ipportstr="";
            for(String str:keys){
                ipportstr=str;
            }
            //拿redis里面key对应的内容
            List<String> registryInfo=RedisApi.lrange(key);
            List<String> newRegistry=new ArrayList<String>();

            boolean isold=false;

            for(String node:registryInfo){
                JSONObject jsonObject=JSON.parseObject(node);
                if(jsonObject.containsKey(ipportstr)){
                    isold=true;
                    newRegistry.add(ipport.toJSONString());
                }else{
                    newRegistry.add(node);
                }
            }

            if(isold){
                if(newRegistry.size()>0){
                    RedisApi.del(key);
                    RedisApi.lpush(key,newRegistry.toArray(new String[newRegistry.size()]));
                }else{
                    RedisApi.lpush(key,ipport.toJSONString());

                    //动态添加节点
                    RedisApi.publish("channel_"+key,dataJson);
                }
            }else{
                RedisApi.lpush(key,dataJson);
            }
        }else{
            RedisApi.lpush(key,dataJson);
        }
    }
}
