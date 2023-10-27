package com.miaomiao.dao.impl;

import com.miaomiao.dao.OrderItemDao;
import com.miaomiao.pojo.OrderItem;

public class OrderItemDaoImpl extends BasicDao implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item(`name`, `count`, `price`, `total_price`, `order_id`)" +
                "values(?, ?, ?, ?, ?)";
        return update(sql, orderItem.getName(), orderItem.getCount(), orderItem.getPrice(), orderItem.getTotalPrice(), orderItem.getOrderId());
    }
}
