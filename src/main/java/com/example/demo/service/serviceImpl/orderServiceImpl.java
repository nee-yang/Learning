package com.example.demo.service.serviceImpl;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderMapper;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("OrderService")
public class orderServiceImpl implements OrderService {


    private OrderMapper orderMapper;

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {this.orderMapper=orderMapper;}

    @Override
    public List<Order> queryOrderUserResultMap() {
        return orderMapper.queryOrderUserResultMap();
    }
}
