package com.qin.loadbalance;

/**
 * Created by DELL on 2017/11/19.
 */
public class NodeInfo {

    private String host;

    private String port;

    private String contextPath;

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

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
