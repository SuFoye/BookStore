package com.miaomiao.filter;

import com.miaomiao.utils.JdbcUtils;

import javax.servlet.*;

public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)  {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            JdbcUtils.commitAndClose(); //提交事务
        } catch (Exception e) {
            JdbcUtils.rollbackAndClose(); //回滚事务
            e.printStackTrace();
            throw new RuntimeException(); //把异常抛给tomcat管理展示友好的错误页面
        }
    }

    @Override
    public void destroy() {

    }
}
