package com.qin.parse;

import com.qin.configBean.Protocol;
import com.qin.configBean.Reference;
import com.qin.configBean.Registry;
import com.qin.configBean.Service;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by DELL on 2017/11/13.
 */
public class SOANamespaceHandler extends NamespaceHandlerSupport {


    public void init() {
        this.registerBeanDefinitionParser("registry",new RegistryBeanDefinition(Registry.class));
        this.registerBeanDefinitionParser("protocol",new ProtocolBeanDefinition(Protocol.class));
        this.registerBeanDefinitionParser("service",new ServiceBeanDefinition(Service.class));
        this.registerBeanDefinitionParser("reference",new ReferenceBeanDefinition(Reference.class));
    }
}
