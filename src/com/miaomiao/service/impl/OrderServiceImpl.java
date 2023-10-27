package com.miaomiao.service.impl;

import com.miaomiao.dao.BookDao;
import com.miaomiao.dao.OrderDao;
import com.miaomiao.dao.OrderItemDao;
import com.miaomiao.dao.impl.BasicDao;
import com.miaomiao.dao.impl.BookDaoImpl;
import com.miaomiao.dao.impl.OrderDaoImpl;
import com.miaomiao.dao.impl.OrderItemDaoImpl;
import com.miaomiao.pojo.*;
import com.miaomiao.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl extends BasicDao implements OrderService {

    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        //订单号，唯一性
        String orderId = System.currentTimeMillis() + "" + userId;
        Order order = new Order(orderId, new Date(), cart.getTotalPrice(), 0, userId);
        //保存订单
        orderDao.saveOrder(order);

        //遍历购物车中每个商品项转换成为订单中项保存到数据库
        for (Map.Entry<Integer, CartItem>entry : cart.getItems().entrySet()) {
            //获取购物车中的每个商品项
            CartItem cartItem = entry.getValue();
            //转换为每个订单项
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            //保存订单项到数据库
            orderItemDao.saveOrderItem(orderItem);

            //更新库存和销量
            Book book = bookDao.queryBookById(cartItem.getId());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookDao.updateBook(book);
        }
        //清空购物车
        cart.clear();

        return orderId;
    }

    @Override
    public List<Order> showOrders() {
        return orderDao.queryOrders();
    }


}
