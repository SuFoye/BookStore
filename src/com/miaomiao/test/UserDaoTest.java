package com.miaomiao.test;

import com.miaomiao.dao.UserDao;
import com.miaomiao.dao.impl.UserDaoImpl;
import org.junit.Test;

public class UserDaoTest {

    UserDao userDao = new UserDaoImpl();

    @Test
    public void queryUserByUsername() {
        if (userDao.queryUserByUsername("admin") == null) {
            System.out.println("用户名可用！");
        } else {
            System.out.println("用户名已存在！");
        }
    }

    @Test
    public void saveUser() {

    }

    @Test
    public void queryuserByUsernameAndPwd() {
        if (userDao.queryuserByUsernameAndPwd("admin", "admin") == null) {
            System.out.println("用户名或密码错误，登录失败！");
        } else {
            System.out.println("登录成功！");
        }
    }
}