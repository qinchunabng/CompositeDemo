package com.qin.test;

/**
 * Created by DELL on 2017/11/19.
 */
public class TestServiceImpl implements TestService {
    @Override
    public String test(String param) {
        return "测试："+param;
    }
}
