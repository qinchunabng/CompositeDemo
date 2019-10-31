package com.qin.module.user.service.impl;

import com.qin.module.user.converter.UserConverter;
import com.qin.module.user.dto.UserDto;
import com.qin.module.user.entity.User;
import com.qin.module.user.mapper.UserMapper;
import com.qin.module.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qcb
 * @since 2019-10-31
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserConverter userConverter;

    @Transactional
    @Override
    public List<UserDto> getList(){
        User user = new User();
        user.setCity("北京");
        user.setName("tomcat");
        this.save(user);
        return userConverter.list2List(list());
    }
}
