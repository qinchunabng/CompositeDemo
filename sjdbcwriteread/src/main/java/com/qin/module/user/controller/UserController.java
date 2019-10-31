package com.qin.module.user.controller;


import com.qin.module.common.ResponseData;
import com.qin.module.common.ResponseUtil;
import com.qin.module.user.converter.UserConverter;
import com.qin.module.user.dto.UserDto;
import com.qin.module.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qcb
 * @since 2019-10-31
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private IUserService userService;

    @Autowired
    private UserConverter userConverter;

    @GetMapping
    public ResponseData<List<UserDto>> userList(){
        return new ResponseUtil<List<UserDto>>().setData(userConverter.list2List(userService.list()));
    }
}
