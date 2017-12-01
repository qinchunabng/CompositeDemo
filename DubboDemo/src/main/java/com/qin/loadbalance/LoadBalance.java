package com.qin.loadbalance;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.List;

/**
 * Created by DELL on 2017/11/19.
 */
public abstract class LoadBalance {

    private String nodeInfo;

    public abstract NodeInfo doSelect(List<String> registryInfo);

    public String getNodeInfo(){
        return this.nodeInfo;
    }

    protected NodeInfo getNodeInfo(String registry){
        nodeInfo=registry;

        JSONObject jsonObject=JSONObject.parseObject(registry);
        Collection values = jsonObject.values();
        JSONObject node=null;
        for(Object value:values){
            node=JSONObject.parseObject(value.toString());
        }

        JSONObject protocol=node.getJSONObject("protocol");
        NodeInfo nodeInfo=new NodeInfo();
        nodeInfo.setHost(protocol.get("host")!=null?protocol.getString("host"):"");
        nodeInfo.setPort(protocol.get("port")!=null?protocol.getString("port"):"");
        nodeInfo.setContextPath(protocol.get("contextpath")!=null?protocol.getString("contextpath"):"");
        return nodeInfo;
    }
}
