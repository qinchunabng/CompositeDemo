package com.qin.configBean;

import com.qin.redis.RedisApi;
import com.qin.registry.BaseRegistryDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

/**
 * Created by DELL on 2017/11/13.
 */
public class Service implements Serializable,ApplicationContextAware,InitializingBean{

    private static ApplicationContext applicationContext;

    private static final long serialVersionUID = -4675249575540175102L;
    private String intf;
    private String ref;
    private String protocol;

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        BaseRegistryDelegate.registry(ref,applicationContext);


    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Service.applicationContext=applicationContext;
    }
}
