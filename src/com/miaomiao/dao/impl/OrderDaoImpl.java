package com.miaomiao.dao.impl;

import com.miaomiao.dao.OrderDao;
import com.miaomiao.pojo.Order;

import java.util.List;

public class OrderDaoImpl extends BasicDao implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql = "insert into t_order(`order_id`, `create_time`, `price`, `status`, `user_id`) " +
                "values(?, ?, ?, ?, ?)";
        return update(sql, order.getOrderId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getUserId());
    }

    @Override
    public List<Order> queryOrders() {
        String sql = "select `order_id` orderId, `create_time` createTime, `price`, `status`, `user_id` userId" +
                "from t_order";
        return queryMulti(sql, Order.class);
    }
}
