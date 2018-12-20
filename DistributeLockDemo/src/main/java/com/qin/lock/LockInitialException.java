package com.qin.lock;

/**
 * 锁初始化异常
 * Created by DELL on 2018/12/20.
 */
public class LockInitialException extends IllegalStateException {

    public LockInitialException(String s) {
        super(s);
    }
}
