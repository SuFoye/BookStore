package com.miaomiao.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {

    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    static {

        Properties properties = new Properties();

        try {
            //读取jdbc.properties属性配置文件，从流中加载数据
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(inputStream);
            //创建数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接池中的连接
     * @return 如果返回null，说明获取连接失败
     */
    public static Connection getConnection(){

        Connection connection = connectionThreadLocal.get();

        if (connection == null) {
            try {
                //从数据库连接池中获取连接
                connection = dataSource.getConnection();
                //保存到ThreadLocal对象中，供后面的jdbc操作中使用
                connectionThreadLocal.set(connection);
                //设置手动管理事务
                connection.setAutoCommit(false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    /**
     * 提交事务并关闭释放连接
     */
    public static void commitAndClose(){
        Connection connection = connectionThreadLocal.get();
        if (connection != null) {
            //提交事务
            try {
                connection.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //关闭连接，释放资源----resultSet和statement在dbutils工具内部会使用完毕自动关
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则出错，因为tomcat底层使用了线程池技术
        connectionThreadLocal.remove();
    }

    /**
     * 回滚事务并关闭释放连接
     */
    public static void rollbackAndClose(){
        Connection connection = connectionThreadLocal.get();
        if (connection != null) {
            //回滚事务
            try {
                connection.rollback();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //关闭连接，释放资源----resultSet和statement在dbutils工具内部会使用完毕自动关
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则出错，因为tomcat底层使用了线程池技术
        connectionThreadLocal.remove();
    }
}
