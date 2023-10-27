package com.miaomiao.service.impl;

import com.miaomiao.dao.UserDao;
import com.miaomiao.dao.impl.UserDaoImpl;
import com.miaomiao.pojo.User;
import com.miaomiao.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registUser(User user) {
         userDao.saveUser(user);
    }

    /**
     * 登录
     * @param user
     * @return 如果返回null,
     */
    @Override
    public User login(User user) {
        return userDao.queryuserByUsernameAndPwd(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {

        if (userDao.queryUserByUsername(username) == null) {
            //等于null说明没查到，表示可用
            return false;
        } else {
            return true;
        }
    }
}
