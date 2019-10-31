package com.qin.module.user.service;

import com.qin.module.user.dto.UserDto;
import com.qin.module.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qcb
 * @since 2019-10-31
 */
public interface IUserService extends IService<User> {

    List<UserDto> getList();
}
