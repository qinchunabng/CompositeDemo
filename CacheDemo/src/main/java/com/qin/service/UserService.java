package com.qin.service;

import com.qin.entity.TUser;

/**
 * Created by DELL on 2017/10/29.
 */
public interface UserService {

    public TUser addUser(TUser user);

    public void deleteUser(String ID);

    public void updateUser(TUser user);

    public TUser getUser(String ID);
}
