package com.qin.configBean;

import com.qin.registry.BaseRegistry;
import com.qin.registry.RedisRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by DELL on 2017/11/13.
 */
public class Registry implements Serializable,ApplicationContextAware,InitializingBean{

    private ApplicationContext applicationContext;


    private static final long serialVersionUID = 5808322446689059116L;
    private String protocol;
    private String address;

    private static Map<String,BaseRegistry> registryMap=new ConcurrentHashMap<>();

    static {
        registryMap.put("redis",new RedisRegistry());
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String addrress) {
        this.address = addrress;
    }

    public void afterPropertiesSet() throws Exception {

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public Map<String,BaseRegistry> getRegistryMap(){
        return Registry.registryMap;
    }
}
