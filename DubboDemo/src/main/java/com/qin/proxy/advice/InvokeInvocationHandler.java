package com.qin.proxy.advice;

import com.qin.cluster.Cluster;
import com.qin.configBean.Reference;
import com.qin.invoke.Invocation;
import com.qin.invoke.Invoke;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 这是一个advice，在这个advice里面进行了rpc调用
 * rpc：http、rmi、netty
 * Created by DELL on 2017/11/15.
 */
public class InvokeInvocationHandler implements InvocationHandler {

    private Invoke invoke;
    private Reference reference;

    public InvokeInvocationHandler(Invoke invoke, Reference reference) {
        this.invoke = invoke;
        this.reference = reference;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation=new Invocation();
        invocation.setMethod(method);
        invocation.setParams(args);
        invocation.setReference(reference);
        invocation.setInvoke(invoke);

        Cluster cluster=reference.getClusterInvoke();
        return cluster.invoke(invocation);
    }
}
