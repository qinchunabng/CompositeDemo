package com.qin.mapper;

import com.qin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qcb
 * @since 2020-01-19
 */
public interface UserMapper extends BaseMapper<User> {

    User select(User user);
}
