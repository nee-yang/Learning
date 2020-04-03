package com.example.demo.repository;


import com.example.demo.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("orderMapper")
public interface OrderMapper  {

    List<Order> queryOrderUserResultMap();
}
