package com.miaomiao.dao.impl;

import com.miaomiao.dao.UserDao;
import com.miaomiao.pojo.User;

public class UserDaoImpl extends BasicDao implements UserDao {

    @Override
    public User queryUserByUsername(String username) {
        String sql = "select `id`, `username`, `password`, `email` " +
                "from t_user where `username` = ?";
        return querySingle(sql, User.class, username);
    }

    /**
     * 保存用户信息
     * @param user 用户信息
     * @return 返回-1表示操作失败，其它是sql执行影响的行数
     */
    @Override
    public int saveUser(User user) {
        String sql = "insert into t_user(`username`, `password`, `email`)" +
                "values(?, ?, ?)";
        return update(sql, user.getUsername(), user.getPassword(),user.getEmail());
    }

    @Override
    public User queryuserByUsernameAndPwd(String username, String password) {
        String sql = "select `id`, `username`, `password`, `email` " +
                "from t_user where `username` = ? and `password` = ?";
        return querySingle(sql, User.class, username, password);
    }
}
