package com.zxj.dao;

import com.zxj.pojo.Order;
import com.zxj.pojo.User;

import java.util.List;

public interface OrderDao {
    /**
     * 查找全部订单
     * @return
     */
    List<Order> getOrderList();
}
