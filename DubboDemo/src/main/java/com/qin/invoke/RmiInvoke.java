package com.qin.invoke;

import com.alibaba.fastjson.JSONObject;
import com.qin.loadbalance.NodeInfo;
import com.qin.rmi.RmiUtil;
import com.qin.rmi.SoaRmi;

import java.rmi.RemoteException;

/**
 * Created by DELL on 2017/11/15.
 */
public class RmiInvoke extends Invoke {

    public String invoke(Invocation invocation) throws Exception{
        try {
            NodeInfo nodeInfo=getNodeInfo(invocation);
            JSONObject sendParam=getParams(invocation);

            RmiUtil rmiUtil=new RmiUtil();
            SoaRmi soaRmi=rmiUtil.startRmiClient(nodeInfo,"soarmi");
            try {
                return soaRmi.invoke(sendParam.toJSONString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return null;
        } catch (Exception e) {
            
            throw e;
        }
    }
}
