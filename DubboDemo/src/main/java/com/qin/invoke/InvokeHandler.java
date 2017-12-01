package com.qin.invoke;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qin.configBean.Service;
import com.qin.util.ClassUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by DELL on 2017/11/20.
 */
public class InvokeHandler {

    public String invokeService(String param) throws InvocationTargetException, IllegalAccessException {
        JSONObject requestParam= JSON.parseObject(param);
        String serviceId=requestParam.getString("serviceId");
        String methodName=requestParam.getString("methodName");
        JSONArray paramTypes=requestParam.getJSONArray("paramTypes");
        JSONArray methodParams=requestParam.getJSONArray("methodParams");

        Object[] objs=null;
        if(methodParams!=null){
            objs=new Object[methodParams.size()];
            for(int i=0;i<methodParams.size();i++){
                objs[i]=methodParams.get(i);
            }
        }

        //拿到spring的上下文
        ApplicationContext context= Service.getApplicationContext();
        //拿到服务的实例
        Object serviceBean=context.getBean(serviceId);

        Method method= ClassUtils.getMethod(serviceBean,methodName,paramTypes);
        if(method!=null){
            return (String) method.invoke(serviceBean,objs);
        }else{
            return "-----------------No Such Method----------------";
        }
    }
}
