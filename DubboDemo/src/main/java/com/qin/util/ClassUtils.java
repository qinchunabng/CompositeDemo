package com.qin.util;

import com.alibaba.fastjson.JSONArray;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/11/20.
 */
public class ClassUtils {

    /**
     * 根据方法名和方法参数类型获取方法的实例
     * @param bean
     * @param methodName
     * @param paramTypes
     * @return
     */
    public static Method getMethod(Object bean, String methodName, JSONArray paramTypes){
        Method[] methods=bean.getClass().getDeclaredMethods();
        List<Method> retMethod=new ArrayList<Method>(methods.length);
        for(int i=0;i<methods.length;i++){
            Method method=methods[i];
            if(method.getName().equals(methodName)){
                retMethod.add(method);
            }
        }
        //
        if(retMethod.size()==1){
            return  retMethod.get(0);
        }
        boolean isSameSize=false;
        boolean isSameType=false;
        //判断重载
        for(Method method:retMethod){
            Class<?>[] types=method.getParameterTypes();

            if(types.length==paramTypes.size()){
                isSameSize=true;
            }

            if(!isSameSize){
                continue;
            }

            for(int i=0;i<types.length;i++){
                if(types[i].equals(paramTypes.get(i))){
                    isSameType=true;
                }else{
                    isSameType=false;
                    break;
                }
            }

            if(isSameType){
                return method;
            }
        }
        return null;
    }
}
