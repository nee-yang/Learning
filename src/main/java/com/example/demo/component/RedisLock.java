package com.example.demo.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class RedisLock {
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


    /**
     * 加锁
     * SETNX
     * 将key设置值为value，如果key不存在，这种情况下等同SET命令。 当key存在时，什么也不做。SETNX是”SET if Not eXists”的简写。
     * 返回值
     * Integer reply, 特定值:
     * 1 如果key被设置了
     * 0 如果key没有被设置
     *
     * GETSET
     * 自动将key对应到value并且返回原来key对应的value。如果key存在但是对应的value不是字符串，就返回错误。
     * 返回值
     * bulk-string-reply: 返回之前的旧值，如果之前Key不存在将返回nil。
     *
     *
     * @param key 唯一标志
     * @param value 当前时间 + 超时时间 也就是时间戳
     * @return
     */
    public boolean lock(String key,String value) {
        if(this.stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
            //可以成功设置,也就是key不存在
            return true;
        }

        //解决死锁，且多个线程同时来时，只会让一个线程拿到锁

        //判断锁超时 - 防止原来的操作异常，没有运行解锁操作  防止死锁
        String currentValue=this.stringRedisTemplate.opsForValue().get(key);
        //如果过期
        //boolean isEmpty(Object str):字符串是否为空或者空字符串：""
        //currentValue不为空且小于当前时间
        if(!StringUtils.isEmpty(currentValue)&&Long.parseLong(currentValue)<System.currentTimeMillis()) {
            //获取上一个锁的时间value，
            String oldValue=this.stringRedisTemplate.opsForValue().getAndSet(key, value);

            //假设两个线程同时进来这里，因为key被占用了，而且锁过期了。获取的值currentValue=A(get取的旧的值肯定是一样的),
            // 两个线程的value都是B,key都是K.锁时间已经过期了。

            //而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的value已经变成了B。
            // 只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
            if (!StringUtils.isEmpty(oldValue)&&oldValue.equals(currentValue)) {
                //oldValue不为空且oldValue等于currentValue，也就是校验是不是上个对应的商品时间戳，也是防止并发
                return true;
            }
        }
        return false;
    }


    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key,String value) {
        try{
            String currentValue=this.stringRedisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(currentValue)&&currentValue.equals(value)){
                //删除Key
                this.stringRedisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            System.out.println("redis解锁失败"+e);
        }
    }
}
