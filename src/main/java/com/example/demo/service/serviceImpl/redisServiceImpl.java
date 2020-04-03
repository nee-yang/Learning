package com.example.demo.service.serviceImpl;


import com.example.demo.service.redisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("redisService")
public class redisServiceImpl implements redisService {

    private RedisTemplate<String, Object> redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate=stringRedisTemplate;
    }


    @Override
    public void setString(String key, String value) {
        this.stringRedisTemplate.opsForValue().set(key,value);
    }

    @Override
    public String getString(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void setString(String key, String value, long expire_time, TimeUnit timeUnit) {
        this.stringRedisTemplate.opsForValue().set(key, value, expire_time, timeUnit);
    }

    @Override
    public void multiput(Map<String, String> map) {
        this.stringRedisTemplate.opsForValue().multiSet(map);
    }

    @Override
    public List<String> multiget(List<String> keys) {
        return this.stringRedisTemplate.opsForValue().multiGet(keys);
    }

    @Override
    public List<Object> rangeList(String key, long start, long end) {
        int i=0;
        List<Object> objectList=this.redisTemplate.opsForList().range(key, start, end);
        return objectList;
    }

    @Override
    public List<String> rangeLists(String key, long start, long end) {
        return this.stringRedisTemplate.opsForList().range(key, start, end);
    }
}
