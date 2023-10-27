package com.miaomiao.dao.impl;

import com.miaomiao.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BasicDao {

    //使用DbUtils操作数据库
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * update()方法用来执行：Insert\Update\Delete语句
     * @return 如果返回-1表示执行失败，否则表示影响的行数
     */
    public int update(String sql, Object...parameters){

        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.update(connection, sql, parameters);
        } catch (SQLException e) {
            e.printStackTrace();
            //抛出异常才可被JdbcUtils捕获然后回滚事务
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回单个javabean的sql语句
     * @param sql 执行的sql语句
     * @param type 返回的对象类型
     * @param parameters sql对应的参数值
     * @param <T> 返回类型的泛型
     * @return
     */
    public <T> T querySingle(String sql, Class<T> type, Object...parameters){

        Connection connection = JdbcUtils.getConnection();

        try {
            return queryRunner.query(connection, sql, new BeanHandler<T>(type), parameters);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回多个javabean的sql语句
     * @param sql 执行的sql语句
     * @param type 返回的对象类型
     * @param parameters sql对应的参数值
     * @param <T> 返回类型的泛型
     * @return
     */
    public <T> List<T> queryMulti(String sql, Class<T> type, Object...parameters){

        Connection connection = JdbcUtils.getConnection();

        try {
            return queryRunner.query(connection, sql, new BeanListHandler<T>(type), parameters);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回一行一列的sql语句
     * @param sql 执行的sql语句
     * @param parameters sql对应的参数值
     * @return
     */
    public Object queryScalar(String sql, Object...parameters){

        Connection connection = JdbcUtils.getConnection();

        try {
            return queryRunner.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
