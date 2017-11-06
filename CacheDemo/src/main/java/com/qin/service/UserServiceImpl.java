package com.qin.service;

import com.qin.dao.UserDao;
import com.qin.entity.TUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by DELL on 2017/10/29.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;

    public TUser addUser(TUser user) {
        userDao.addUser(user);
        logger.info("================add a user to db =================:"+user.toString());
        return user;
    }

    public void deleteUser(String ID) {
        userDao.deleteUser(ID);
        logger.info("===============delete a user from db===============:"+ID);
    }

    public void updateUser(TUser user) {
        userDao.updateUser(user);
        logger.info("===============update a user from db===============:"+user.toString());
    }

    public TUser getUser(String ID) {
        logger.info("==============get a user from db===============:"+ID);
        return userDao.getUser(ID);
    }
}
