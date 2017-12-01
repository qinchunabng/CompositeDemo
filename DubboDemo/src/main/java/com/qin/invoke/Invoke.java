package com.qin.invoke;

import com.alibaba.fastjson.JSONObject;
import com.qin.configBean.Reference;
import com.qin.loadbalance.LoadBalance;
import com.qin.loadbalance.NodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/11/15.
 */
public abstract class Invoke {

    /**
     * 返回json，用json的方式进行通信
     * @return
     */
    public abstract String invoke(Invocation invocation) throws Exception;

    /**
     * 获取服务节点信息
     * @param invocation
     * @return
     */
    protected NodeInfo getNodeInfo(Invocation invocation){
        Reference reference=invocation.getReference();
        List<String> registryInfo=new ArrayList<>(reference.getRegistryInfo());
        String protocol=reference.getProtocol();
        //TODO 根据reference配置的协议去过滤服务
        //负载均衡算法
        String loadbalance=reference.getLoadbalance();
        LoadBalance loadBalance=reference.getLoadBalance(loadbalance);
        NodeInfo nodeInfo=loadBalance.doSelect(registryInfo);
        invocation.setNodeInfo(loadBalance.getNodeInfo());
        return nodeInfo;
    }

    protected JSONObject getParams(Invocation invocation){
        JSONObject sendParam=new JSONObject();
        sendParam.put("methodName",invocation.getMethod().getName());
        sendParam.put("methodParams",invocation.getParams());
        sendParam.put("serviceId",invocation.getReference().getId());
        sendParam.put("paramTypes",invocation.getMethod().getParameterTypes());
        return sendParam;
    }
}
