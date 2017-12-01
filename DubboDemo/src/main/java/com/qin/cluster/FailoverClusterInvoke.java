package com.qin.cluster;

import com.qin.configBean.Reference;
import com.qin.invoke.Invocation;
import com.qin.invoke.Invoke;

/**
 * Created by DELL on 2017/11/22.
 */
public class FailoverClusterInvoke extends Cluster {
    @Override
    public String invoke(Invocation invocation) throws Exception {
        Reference reference=invocation.getReference();
        Integer retries=reference.getRetries();

        for(int i=0;i<retries;i++){
            Invoke invoke=invocation.getInvoke();
            try {
                return invoke.invoke(invocation);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        //调用失败移除服务节点信息
        removeCluster(invocation);
        throw new RuntimeException("retries "+retries+"次，全部失败！");
    }
}
