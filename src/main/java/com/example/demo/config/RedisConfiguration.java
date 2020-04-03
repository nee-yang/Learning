package com.example.demo.config;

import com.example.demo.component.RedisAllReceiver;
import com.example.demo.component.RedisUser2Receiver;
import com.example.demo.component.RedisUserReceiver;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfiguration  {
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(BasicPolymorphicTypeValidator.builder().build(),ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     * @param connectionFactory
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            RedisAllReceiver redisAllReceiver,
                                            RedisUser2Receiver redisUser2Receiver,
                                            RedisUserReceiver redisUserReceiver

    ) {

        //MessageListener 是我们的监听器,可定义多个: RedisUserReceiver redisUserReceiver, RedisUser2Receiver redisUser2Receiver,
        // RedisAllReceiver allReceiver: 这里我定义了三个
        //RedisMessageListenerContainer 存放我们所有的 监听器 MessageListener 我定义个三个 MessageListener
        // 前两个分别指向固定的 channel : topic_user 和 topic_user2 , 而最后一个采用了 匹配符 *, 表示可匹配 topic_ 开头的所有通道
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        //  订阅了一个通道
        container.addMessageListener(redisUserReceiver, new PatternTopic(RedisChannel.USER_CHANNEL));
        container.addMessageListener(redisUser2Receiver, new PatternTopic(RedisChannel.USER2_CHANNEL));

        // 匹配多个  channel
        container.addMessageListener(redisAllReceiver, new PatternTopic("topic_*"));
        return container;
    }

//    /**
//     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
//     * @param redisAllReceiver
//     * @return
//     */
//    @Bean
//    MessageListenerAdapter listenerAdapter(RedisAllReceiver redisAllReceiver) {
//        System.out.println("消息适配器进来了");
//        return new MessageListenerAdapter(redisAllReceiver, "receiveMessage");
//    }

    public static class RedisChannel {
        public static final String USER_CHANNEL = "topic_user";
        public static final String USER2_CHANNEL = "topic_user2";
    }


}
