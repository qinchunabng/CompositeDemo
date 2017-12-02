package com.qin.jni;

/**
 * Created by DELL on 2017/12/02.
 */
public class JniTest {

    /**
     * 从C获取字符串
     * @return
     */
    public native static String getText();

    static {
        //加载动态库
////        System.loadLibrary("jason");
//        String path = JniTest.class.getClassLoader().getResource("").getPath();
//        System.out.println(path);
        //linux上执行
        System.load("/root/jni/jason.so");
    }
}


