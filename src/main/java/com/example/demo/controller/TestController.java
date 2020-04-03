package com.example.demo.controller;

import com.example.demo.config.RedisConfig;
import com.example.demo.model.Students;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/put")
    public String put() {
//        RedisConfig redisConfig=new RedisConfig("99");
        List<String> stringList=new ArrayList<String>();
        stringList.add("sd");
        stringList.add("ed");
//        redisConfig.putObject("list",stringList);
        return "success";
    }
}
