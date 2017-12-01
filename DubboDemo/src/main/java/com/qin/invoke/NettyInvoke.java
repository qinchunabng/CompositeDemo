package com.qin.invoke;

import com.alibaba.fastjson.JSONObject;
import com.qin.loadbalance.NodeInfo;
import com.qin.netty.NettyUtil;

/**
 * Created by DELL on 2017/11/15.
 */
public class NettyInvoke extends Invoke {

    public String invoke(Invocation invocation) throws Exception{
        try {
            NodeInfo nodeInfo=getNodeInfo(invocation);
            JSONObject sendParam=getParams(invocation);

            try {
                return NettyUtil.sendMsg(nodeInfo.getHost(),nodeInfo.getPort(),sendParam.toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } catch (Exception e) {
            throw e;
        }
    }
}
