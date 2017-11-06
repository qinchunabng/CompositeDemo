package com.qin.dao;

import com.qin.entity.TUser;
import org.mybatis.spring.annotation.MapperScan;

/**
 * Created by DELL on 2017/10/29.
 */
@MapperScan
public interface UserDao {

    public int addUser(TUser user);

    public int deleteUser(String ID);

    public int updateUser(TUser user);

    public TUser getUser(String ID);
}
