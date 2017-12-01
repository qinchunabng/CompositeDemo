package com.qin.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/11/19.
 */
public class HttpRequest {

    private static final Logger logger= LoggerFactory.getLogger(HttpRequest.class.getName());

    /**
     * 发送get请求
     * @param url
     * @param param 请求参数，参数格式：name1=value1&name2=value2&name3=value3
     * @return
     */
    public static String sendGet(String url,String param) {
        StringBuilder sb=new StringBuilder();
        BufferedReader reader=null;
        String urlWithParam=url+"?"+param;
        try {
            URL realUrl = new URL(urlWithParam);

            URLConnection connection = realUrl.openConnection();

            connection.setRequestProperty("accept", "*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();

            Map<String, List<String>> map = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "--->" + entry.getValue());
            }
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }catch (IOException e){
            logger.error(e.getMessage(),e);
            return null;
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }
    }

    /**
     * 发送post请求
     * @param url 请求地址
     * @param param 请求参数，参数格式：name1=value1&name2=value2&name3=value3
     * @return
     */
    public static String sendPost(String url,String param){
        PrintWriter out=null;
        BufferedReader reader=null;
        StringBuilder result=new StringBuilder();
        try {
            URL realUrl=new URL(url);
            URLConnection connection=realUrl.openConnection();

            connection.setRequestProperty("accept", "*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            out=new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.flush();

            reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line=null;
            while((line=reader.readLine())!=null){
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            return null;
        }finally {
            if(out!=null){
                out.close();
            }
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }
    }
}
