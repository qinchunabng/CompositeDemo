package com.qin.invoke;

import com.alibaba.fastjson.JSONObject;
import com.qin.configBean.Reference;
import com.qin.http.HttpRequest;
import com.qin.loadbalance.LoadBalance;
import com.qin.loadbalance.NodeInfo;

import java.util.List;

/**
 * Http的调用过程
 * Created by DELL on 2017/11/15.
 */
public class HttpInvoke extends Invoke {

    public String invoke(Invocation invocation) throws Exception{
        try {
            NodeInfo nodeInfo=getNodeInfo(invocation);
            JSONObject sendParam=getParams(invocation);

            String url="http://"+nodeInfo.getHost()+":"+nodeInfo.getPort()+nodeInfo.getContextPath();

            //调用对端生产者服务
            String result= HttpRequest.sendPost(url,sendParam.toJSONString());
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}
