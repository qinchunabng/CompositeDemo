package com.qin.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * service 标签的解析类
 * Created by DELL on 2017/11/13.
 */
public class ServiceBeanDefinition implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ServiceBeanDefinition(Class<?> beanClass){
        this.beanClass=beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition=new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String intf=element.getAttribute("interface");
        String ref=element.getAttribute("ref");
        String protocol=element.getAttribute("protocol");
        if(StringUtils.isEmpty(intf)){
            throw new RuntimeException("service interface 不能为空！");
        }
        if(StringUtils.isEmpty(ref)){
            throw new RuntimeException("service ref 不能为空！");
        }
//        if(StringUtils.isEmpty(protocol)){
//            throw new RuntimeException("service protocol 不能为空！");
//        }
        beanDefinition.getPropertyValues().addPropertyValue("intf",intf);
        beanDefinition.getPropertyValues().addPropertyValue("ref",ref);
        beanDefinition.getPropertyValues().addPropertyValue("protocol",protocol);

        parserContext.getRegistry().registerBeanDefinition("Service_"+ref,beanDefinition);
        return beanDefinition;
    }
}
