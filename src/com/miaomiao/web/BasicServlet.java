package com.miaomiao.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

public abstract class BasicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {

        /*
            解决乱码问题
            1.请求阶段编码字符集
            2.响应阶段编码字符集（服务器和客户端均设置）
         */
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        /*
            解决乱码问题
            1.请求阶段编码字符集
            2.响应阶段编码字符集（服务器和客户端均设置）
         */
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String action = req.getParameter("action");
        //通过动态绑定机制+反射机制+action方法名获取 method方法对象（对应的业务方法），调用invoke执行方法
        try {
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(); //把异常抛给Filter过滤器
        }
    }
}
