package com.qin.remote.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qin.configBean.Service;
import com.qin.util.ClassUtils;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by DELL on 2017/11/19.
 */
public class DispatcherServlet extends HttpServlet {


    private static final long serialVersionUID = -668932363613383681L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);

        JSONObject requestParam=httpProcess(req,resp);
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

        //获取要调用的方法
        Method method= ClassUtils.getMethod(serviceBean,methodName,paramTypes);

        PrintWriter printWriter=resp.getWriter();
        if(method!=null){
            try {
                String result = (String) method.invoke(serviceBean,objs);
                System.out.println("服务端收到请求，结果："+result);
                printWriter.print(result);
            } catch (IllegalAccessException|InvocationTargetException e) {
                e.printStackTrace();
            }
        }else{
            printWriter.print("---------------No Such Method----------------");
        }
//        printWriter.close();
    }



    private JSONObject httpProcess(HttpServletRequest request,HttpServletResponse response) throws IOException {
        StringBuilder sb=new StringBuilder();
        InputStream is=request.getInputStream();

        BufferedReader reader=new BufferedReader(new InputStreamReader(is));
        String line=null;
        while ((line=reader.readLine())!=null){
            sb.append(line);
        }
        if(sb.length()>0){
            return JSON.parseObject(sb.toString());
        }else{
            return null;
        }
    }
}
