package com.example.demo.controller;


import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.OrderService;
import com.example.demo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;
    private RedisTemplate redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setOrderService(OrderService orderService) { this.orderService = orderService; }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate){this.redisTemplate=redisTemplate;}

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate){this.stringRedisTemplate=stringRedisTemplate;}



    @GetMapping("/queryOrderUserResultMap")
    public Long queryOrderUserResultMap() {
        List<Order> orderList = orderService.queryOrderUserResultMap();
        List<String> orderJsons=new ArrayList<String>();
        for(int i=0;i<orderList.size();i++) {
            orderJsons.add(JsonUtils.objectToJson(orderList.get(i)));
        }
        int i=1;
        return this.redisTemplate.opsForList().leftPushAll("OrderUserResult", orderJsons);
    }

    @GetMapping("/queryOrderUserResultMaps")
    public Long queryOrderUserResultMaps() {
        List<Order> orderList = orderService.queryOrderUserResultMap();
        List<String> orderJsons=new ArrayList<String>();
        for(Iterator it=orderList.iterator();it.hasNext();) {
            Order order=(Order) it.next();
            orderJsons.add(JsonUtils.objectToJson(order));
        }
        int i=1;
        return this.redisTemplate.opsForList().leftPushAll("OrderUserResult", orderJsons);
    }

    @GetMapping("/redisOrderUserResultMap")
    public List<Order> getRedisOrderUserResult() {
        List<Order> orderList=new ArrayList<Order>();
        List<Object> objectList=this.redisTemplate.opsForList().range("OrderUserResult",0,-1);
        for(int i=0;i<objectList.size();i++) {
            orderList.add(JsonUtils.jsonToPojo(objectList.get(i).toString(),Order.class));
        }
        return orderList;
    }


    @GetMapping("/createOrder")
    public void createOrder() {
        List<Order> orderList = orderService.queryOrderUserResultMap();
        Order order=new Order();
        order.setOid(1);
        order.setUid(1);
        order.setNote("none");
        User user=new User();
        user.setUid(2);
        user.setAddress("beiji");
        order.setUser(user);
        this.redisTemplate.opsForValue().set("order", JsonUtils.objectToJson(order));
    }

    @GetMapping("/getOrder")
    public Order getOrder() {
        return (Order) JsonUtils.jsonToPojo(this.redisTemplate.opsForValue().get("order").toString(),Order.class);
    }
}