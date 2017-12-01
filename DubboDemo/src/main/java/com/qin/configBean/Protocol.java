package com.qin.configBean;

import com.qin.netty.NettyUtil;
import com.qin.rmi.RmiUtil;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;

/**
 * Created by DELL on 2017/11/13.
 */
public class Protocol implements Serializable,InitializingBean{

    private static final long serialVersionUID = -1552573075992529224L;
    private String name;
    private String host;
    private String port;
    private String contextpath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getContextpath() {
        return contextpath;
    }

    public void setContextpath(String contextpath) {
        this.contextpath = contextpath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if("rmi".equalsIgnoreCase(name)){
            RmiUtil rmiUtil=new RmiUtil();
            rmiUtil.startRmiServer(host,port,"soarmi");
        }else if("netty".equalsIgnoreCase(name)){
            //启动一个线程来启动netty，防止netty阻塞阻止应用启动
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        NettyUtil.startServer(port);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
