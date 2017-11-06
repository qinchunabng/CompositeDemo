package com.qin.seckill;

import com.qin.entity.TUser;
import com.qin.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by DELL on 2017/10/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class CacheTest {

    private String ID="1006";

    @Resource
    private UserService userService;


    @Test
    public void cache(){
        TUser user=new TUser();
        user.setID(ID);
        user.setName("tomcat");
        user.setSex("男");
        user.setSubject("缓存");
        user.setDepartment("技术部");
        userService.addUser(user);
        TUser user1=userService.getUser(ID);
        Assert.assertEquals(ID,user1.getID());
    }
}
