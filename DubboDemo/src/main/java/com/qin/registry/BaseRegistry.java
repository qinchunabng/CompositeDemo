package com.qin.registry;

import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by DELL on 2017/11/18.
 */
public interface BaseRegistry {

    boolean registry(String param, ApplicationContext applicationContext);

    List<String> getRegistry(String id, ApplicationContext applicationContext);
}
