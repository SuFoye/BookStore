package com.miaomiao.service;

import com.miaomiao.pojo.Cart;
import com.miaomiao.pojo.Order;

import java.util.List;

public interface OrderService {
    public String createOrder(Cart cart, Integer userId);
    public List<Order> showOrders();
}
