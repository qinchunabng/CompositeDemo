package com.qin.registry;

import com.qin.configBean.Registry;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Set;

/**
 * Created by DELL on 2017/11/18.
 */
public class BaseRegistryDelegate {

    public static void registry(String ref, ApplicationContext context){
        Registry registry=context.getBean(Registry.class);
        String protocol=registry.getProtocol();
        BaseRegistry registryBean=registry.getRegistryMap().get(protocol);
        registryBean.registry(ref,context);
    }

    public static List<String> getRegistryInfo(String id, ApplicationContext context){
        Registry registry=context.getBean(Registry.class);
        String protocol=registry.getProtocol();
        BaseRegistry registryBean=registry.getRegistryMap().get(protocol);
        return registryBean.getRegistry(id,context);
    }
}
