package com.qin.security;

import com.qin.entity.RolePermission;
import com.qin.mapper.PermissionMapper;
import com.qin.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Component
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    /**
     * 每一个资源所需要的角色 Collection<ConfigAttribute>决策器会用到
     */
    private static HashMap<String, Collection<ConfigAttribute>> map =null;

    //当接收到一个http请求时, filterSecurityInterceptor会调用的方法. 参数object是一个包含url信息的HttpServletRequest实例. 这个方法要返回请求该url所需要的所有权限集合。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if(null == map){
            loadResourceDefine();
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation)o).getHttpRequest();
        for(String url : map.keySet()){
            if(new AntPathRequestMatcher(url).matches(request)){
                return map.get(url);
            }
        }
        return null;
    }

    //Spring容器启动时自动调用, 一般把所有请求与权限的对应关系也要在这个方法里初始化, 保存在一个属性变量里。
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    //指示该类是否能够为指定的方法调用或Web请求提供ConfigAttributes。
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    //初始化 所有资源 对应的角色
    private void loadResourceDefine(){
        map = new HashMap<>(16);
        //权限资源 和 角色对应的表  也就是 角色权限 中间表
        List<RolePermission> rolePermissions = rolePermissionMapper.getRolePermissions();
        //某个资源可以被哪些角色访问
        for(RolePermission rolePermission : rolePermissions){
            String url = rolePermission.getUrl();
            String roleName = rolePermission.getRoleName();
            ConfigAttribute role = new SecurityConfig(roleName);
            if(map.containsKey(url)){
                map.get(url).add(role);
            }else{
                List<ConfigAttribute> list = new ArrayList<>();
                list.add(role);
                map.put(url, list);
            }
        }
    }
}
