package com.qin.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * protocol 标签的解析类
 * Created by DELL on 2017/11/13.
 */
public class ProtocolBeanDefinition implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ProtocolBeanDefinition(Class<?> beanClass){
        this.beanClass=beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition=new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String name=element.getAttribute("name");
        String host=element.getAttribute("host");
        String port=element.getAttribute("port");
        String contextpath=element.getAttribute("contextpath");
        if(StringUtils.isEmpty(name)){
            throw new RuntimeException("protocol protocol 不能为空！");
        }
        if(StringUtils.isEmpty(host)){
            throw new RuntimeException("protocol host 不能为空！");
        }
        if(StringUtils.isEmpty(port)){
            throw new RuntimeException("protocol port 不能为空！");
        }
        beanDefinition.getPropertyValues().addPropertyValue("name",name);
        beanDefinition.getPropertyValues().addPropertyValue("host",host);
        beanDefinition.getPropertyValues().addPropertyValue("port",port);
        beanDefinition.getPropertyValues().addPropertyValue("contextpath",contextpath);

        parserContext.getRegistry().registerBeanDefinition("Protocol_"+host+port,beanDefinition);
        return beanDefinition;
    }
}
