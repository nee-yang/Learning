package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class indexController {
    @RequestMapping(path={"/index","/"})
    @ResponseBody
    public String index(){
        return "Hello World";
    }
}
