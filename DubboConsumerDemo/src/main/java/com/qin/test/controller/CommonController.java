package com.qin.test.controller;

import com.qin.test.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by DELL on 2017/11/15.
 */
@Controller
public class CommonController {

    @Resource
    private UserService userService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return userService.eat("xxxx");
    }
}
