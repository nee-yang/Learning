package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.config.RedisConfig;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.Students;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/test")
public class SpringBootTest {


    private com.example.demo.service.studentsService studentsService;
    private static final Logger loggers = org.slf4j.LoggerFactory.getLogger(SpringBootTest.class);

    @Autowired
    public void setStudentsService(com.example.demo.service.studentsService studentsService) {
        this.studentsService = studentsService;
    }

    @RequestMapping("/test")
    public JSONObject test() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "success");
        //id 没有实际价值 只是为了更好确认
//        RedisConfig redisConfig = new RedisConfig("100");
//        redisConfig.putObject("1000","hello i m frank!");
        return jsonObject;
    }

    /**
     * 根据id获取对象
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public JSONObject get(@PathVariable("id") int id) {
        Students student = studentsService.get(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", student.getId());
        jsonObject.put("age", student.getAge());
        jsonObject.put("name", student.getName());
//        RedisConfig redisConfig = new RedisConfig("100");
//        redisConfig.putObject("frank",jsonObject.toString());
        return jsonObject;
    }

    /**
     * 更新对象
     *
     * @return
     */
    @RequestMapping("/update")
    public String put() {

        Students student = new Students();
        student.setId(1);
        student.setAge(22);
        student.setName("new frank");
        studentsService.update(student);
        return "success";

    }

    @RequestMapping("/getRedis")
    public JSONObject get() {
        RedisConfig redisConfig = new RedisConfig("100");
        return JSONObject.parseObject(redisConfig.getObject("frank").toString());
    }
}
