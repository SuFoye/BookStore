package com.miaomiao.dao;

import com.miaomiao.pojo.Order;

import java.util.List;

public interface OrderDao {
    public int saveOrder(Order order);
    public List<Order> queryOrders();
}
