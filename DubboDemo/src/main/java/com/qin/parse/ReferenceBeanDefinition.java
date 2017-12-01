package com.qin.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * reference 标签的解析类
 * Created by DELL on 2017/11/13.
 */
public class ReferenceBeanDefinition implements BeanDefinitionParser {

    private Class<?> beanClass;

    public ReferenceBeanDefinition(Class<?> beanClass){
        this.beanClass=beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition=new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String id=element.getAttribute("id");
        String intf=element.getAttribute("interface");
        String protocol=element.getAttribute("protocol");
        String loadbalance=element.getAttribute("loadbalance");
        String retries = element.getAttribute("retries");
        String cluster=element.getAttribute("cluster");

        if(StringUtils.isEmpty(id)){
            throw new RuntimeException("reference id 不能为空！");
        }
        if(StringUtils.isEmpty(intf)){
            throw new RuntimeException("reference interface 不能为空！");
        }
//        if(StringUtils.isEmpty(protocol)){
//            throw new RuntimeException("reference port 不能为空！");
//        }

        beanDefinition.getPropertyValues().addPropertyValue("intf",intf);
        beanDefinition.getPropertyValues().addPropertyValue("id",id);
        beanDefinition.getPropertyValues().addPropertyValue("protocol",protocol);
        beanDefinition.getPropertyValues().addPropertyValue("loadbalance",loadbalance);
        beanDefinition.getPropertyValues().addPropertyValue("retries",retries);
        beanDefinition.getPropertyValues().addPropertyValue("cluster",cluster);

        parserContext.getRegistry().registerBeanDefinition("Reference_"+id,beanDefinition);
        return beanDefinition;
    }
}
