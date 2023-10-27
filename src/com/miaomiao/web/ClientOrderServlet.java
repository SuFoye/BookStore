package com.miaomiao.web;

import com.miaomiao.pojo.Cart;
import com.miaomiao.pojo.User;
import com.miaomiao.service.OrderService;
import com.miaomiao.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientOrderServlet extends BasicServlet{

    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     * @param req
     * @param resp
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        //获取userId
        User loginUser = (User) req.getSession().getAttribute("user");
        if(loginUser == null) {
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
            //一般转发和重定向后不需要执行别的代码
            return;
        }
        Integer userId = loginUser.getId();

        //调用orderService.createOrder(cart, userID) 生成订单
        String orderId = orderService.createOrder(cart, userId);
        req.getSession().setAttribute("orderId", orderId);
        //请求重定向（防止刷新重复提交订单结账），到/page/cart/checkout.jsp
        resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
    }
}
