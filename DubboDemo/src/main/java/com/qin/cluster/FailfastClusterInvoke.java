package com.qin.cluster;

import com.qin.invoke.Invocation;
import com.qin.invoke.Invoke;

/**
 * Created by DELL on 2017/11/22.
 */
public class FailfastClusterInvoke extends Cluster {
    @Override
    public String invoke(Invocation invocation) throws Exception {
        Invoke invoke = invocation.getInvoke();
        try {
            return invoke.invoke(invocation);
        } catch (Exception e) {
            e.printStackTrace();
            //调用失败移除服务节点信息
            removeCluster(invocation);
            throw e;
        }
    }
}
