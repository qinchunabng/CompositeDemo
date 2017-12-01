package com.qin.invoke;

import com.qin.configBean.Reference;
import com.qin.loadbalance.NodeInfo;

import java.lang.reflect.Method;

/**
 * Created by DELL on 2017/11/15.
 */
public class Invocation {

    private Method method;

    private Object[] params;

    private Reference reference;

    private Invoke invoke;

    private String nodeInfo;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public Invoke getInvoke() {
        return invoke;
    }

    public void setInvoke(Invoke invoke) {
        this.invoke = invoke;
    }

    public String getNodeInfo() {
        return nodeInfo;
    }

    public void setNodeInfo(String nodeInfo) {
        this.nodeInfo = nodeInfo;
    }
}
