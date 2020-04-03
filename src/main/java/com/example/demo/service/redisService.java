package com.example.demo.service;


import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface redisService {


    void setString(String key,String value);

    String getString(String key);

    void setString(String s, String str, long expire_time, TimeUnit timeUnit);

    void multiput(Map<String, String> map);

    List<String> multiget(List<String> keys);

    List<Object> rangeList(String key,long start,long end);

    List<String> rangeLists(String key, long start, long end);


}
