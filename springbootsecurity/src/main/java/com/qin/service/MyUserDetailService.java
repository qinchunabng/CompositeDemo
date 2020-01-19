package com.qin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qin.entity.Role;
import com.qin.entity.User;
import com.qin.mapper.RoleMapper;
import com.qin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User userQuery = new User();
        userQuery.setUsername(s);
        User user = userMapper.select(userQuery);
        if(null != user){
            List<Role> roleList = roleMapper.getRolesByUserId(user.getId());
            user.setAuthorities(roleList);
        }
        return user;
    }
}
