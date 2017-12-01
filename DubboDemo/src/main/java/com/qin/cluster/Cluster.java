package com.qin.cluster;

import com.qin.configBean.Reference;
import com.qin.invoke.Invocation;

/**
 * Created by DELL on 2017/11/22.
 */
public abstract class Cluster {

    public abstract String invoke(Invocation invocation) throws Exception;

    /**
     * 移除服务节点信息
     * @param invocation
     */
    protected void removeCluster(Invocation invocation){
        String node = invocation.getNodeInfo();
        Reference.getRegistryInfo().remove(node);
    }
}
