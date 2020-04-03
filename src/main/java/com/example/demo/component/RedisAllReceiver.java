package com.example.demo.component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisAllReceiver implements MessageListener {


    //用于接受所有的消息(匹配topic_* 通道的消息),在后面的配置文件可见到


    private final Logger logger = LoggerFactory.getLogger(RedisAllReceiver.class);

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        Object itemValue = redisTemplate.getValueSerializer().deserialize(body);
        String topic = redisTemplate.getStringSerializer().deserialize(channel);
        logger.info("topic_*: {}, value: {}", topic, itemValue);
    }

}
