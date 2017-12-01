package com.qin.parse;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * registry 标签的解析类
 * Created by DELL on 2017/11/13.
 */
public class RegistryBeanDefinition implements BeanDefinitionParser {

    private Class<?> beanClass;

    public RegistryBeanDefinition(Class<?> beanClass){
        this.beanClass=beanClass;
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition=new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String protocol=element.getAttribute("protocol");
        String address=element.getAttribute("address");
        if(StringUtils.isEmpty(protocol)){
            throw new RuntimeException("Registry protocol 不能为空！");
        }
        if(StringUtils.isEmpty(address)){
            throw new RuntimeException("Registry address 不能为空！");
        }
        beanDefinition.getPropertyValues().addPropertyValue("protocol",protocol);
        beanDefinition.getPropertyValues().addPropertyValue("address",address);

        parserContext.getRegistry().registerBeanDefinition("Registry_"+protocol+address,beanDefinition);
        return beanDefinition;
    }
}
