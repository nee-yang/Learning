package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){this.userService=userService;}

    @GetMapping("/queryUserWithOrder")
    public JSONObject getMaxAndMinScores() {
        List<User> userList = userService.queryUserWithOrder();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", userList);
        int i = 10;
        return jsonObject;
    }
}
