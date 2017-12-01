package com.qin.rmi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qin.configBean.Service;
import com.qin.invoke.InvokeHandler;
import com.qin.util.ClassUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 生产者
 * Created by DELL on 2017/11/20.
 */
public class SoaRmiImpl extends UnicastRemoteObject implements SoaRmi {

    private static final long serialVersionUID = 4258102088787102105L;

    protected SoaRmiImpl() throws RemoteException {
    }

    @Override
    public String invoke(String param) throws RemoteException {
        try {
            return new InvokeHandler().invokeService(param);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
