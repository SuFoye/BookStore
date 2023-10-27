package com.miaomiao.web;

import com.miaomiao.pojo.Book;
import com.miaomiao.pojo.Cart;
import com.miaomiao.pojo.CartItem;
import com.miaomiao.service.BookService;
import com.miaomiao.service.impl.BookServiceImpl;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BasicServlet{

    private BookService bookService = new BookServiceImpl();

    /**
     * 加入购物车
     * @param req
     * @param resp
     */
    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1.获取请求的参数 商品编号
        int id = req.getParameter("id") == null ? 0 : Integer.parseInt(req.getParameter("id"));
        //2.调用bookService.queryBookById(id)：Book得到图书信息
        Book book = bookService.queryBookById(id);
        //3.把图书信息，转换成为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //4.调用Cart.addItem(CartItem)；添加商品项
        //购物车保存在session域中，一个用户一个session且唯一个购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);

        /*
        //最后一个添加商品的名称
        req.getSession().setAttribute("lastName", cartItem.getName());
        //5.重定向回原来商品所在的地址列表页面
        resp.sendRedirect(req.getHeader("Referer"));*/

        //返回购物车总的商品数量和最后一个添加商品的名称
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("totalCount", cart.getTotalCount());
        resultMap.put("lastName", cartItem.getName());

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        resp.getWriter().write(json);

    }

    /**
     * 删除商品项目
     * @param req
     * @param resp
     */
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取商品编号
        int id = req.getParameter("id") == null ? 0 : Integer.parseInt(req.getParameter("id"));
        //获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            //删除购物车商品项
            cart.deleteItem(id);
            //重定向回原来购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 清空购物车
     * @param req
     * @param resp
     */
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       //1.获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            //清空购物车
            cart.clear();
            //重定向回原来购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    /**
     * 修改商品数量
     * @param req
     * @param resp
     */
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取请求的参数 商品编号和商品数量
        int id = req.getParameter("bookId") == null ? 0 : Integer.parseInt(req.getParameter("bookId"));
        int count = req.getParameter("count") == null ? 1 : Integer.parseInt(req.getParameter("count"));
        //获取cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            //修改商品数量
            cart.updateCount(id, count);
            //重定向回原来购物车页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }
}
