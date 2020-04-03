package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.RedisConfig;
import com.example.demo.config.RedisConfiguration;
import com.example.demo.model.Students;
import com.example.demo.service.redisService;
import com.example.demo.service.scoreService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.scoreService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/score")
public class ScoreController {


    private scoreService scoreService;
    private redisService redisService;



    @Autowired
    public void setScoreService(scoreService scoreService) {
        this.scoreService=scoreService;
    }

    @Autowired
    public void setRedisService(redisService redisService) {
        this.redisService=redisService;
    }

    /**
     * 根据id获取对象
     *
     * @param cid
     * @return
     */
    @GetMapping("/sumScores/{cid}")
    public double getSumScores(@PathVariable("cid") int cid) {
        double sumScores=scoreService.getSumScores(cid);
//        RedisConfig redisConfig = new RedisConfig("100");
        BigDecimal bigDecimal=new BigDecimal(sumScores);
        String str=String.valueOf(bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
//        redisConfig.putObject(cid+"sumScores", str);
        long EXPIRE_TIME=30;
        TimeUnit timeUnit=TimeUnit.MINUTES;
        this.redisService.setString("sumscore of "+cid,str,EXPIRE_TIME,timeUnit);
        return sumScores;
    }


    @GetMapping("/getMaxAndMinScores")
    public JSONObject getMaxAndMinScores() {
        List<Map<Object, Object>> maps=scoreService.getMaxAndMinScores();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("list",maps);
        int i=10;
        return jsonObject;
    }




    @RequestMapping("/getRedis/{cid}")
    public double get(@PathVariable("cid") int cid) {
//        RedisConfig redisConfig = new RedisConfig("100");
        return Double.valueOf(this.redisService.getString("sumscore of "+cid).toString());
    }
}
