package com.miaomiao.test;

import com.miaomiao.pojo.User;
import com.miaomiao.service.UserService;
import com.miaomiao.service.impl.UserServiceImpl;
import org.junit.Test;

public class UserServiceTest {

    UserService userService = new UserServiceImpl();

    @Test
    public void registUser() {
        userService.registUser(new User(null, "bbj168", "6666666", "bbj168@qq.com"));
    }

    @Test
    public void login() {
        userService.login(new User(null, "admin", "admin", null));
    }

    @Test
    public void existsUsrname() {

        if (userService.existsUsername("admin")) {
            System.out.println("用户名已存在！");
        } else {
            System.out.println("用户名可用！");
        }
    }
}