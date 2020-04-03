package com.example.demo.controller;

import com.example.demo.config.RedisConfiguration;
import com.example.demo.service.redisService;
import com.example.demo.service.serviceImpl.MessagePub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/redis")
public class RedisTestController {

    private redisService redisService;
    private RedisTemplate redisTemplate;
    private MessagePub messagePub;
        


    @Autowired
    public void setRedisService(redisService redisService) {
        this.redisService=redisService;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate){this.redisTemplate=redisTemplate;}

    @Autowired
    public void setMessagePub(MessagePub messagePub){this.messagePub=messagePub;}




    @RequestMapping("/multiset")
    public void multiset() {
        Map<String,String> map=new HashMap<String, String>();
        map.put("1","1a");
        map.put("2","2b");
        map.put("3","3c");
        this.redisService.multiput(map);
    }

    @GetMapping("/multiget")
    public List<String> multiget() {
        List<String> keys=new ArrayList<String>();
        keys.add("1");
        keys.add("2");
        keys.add("3");
        return this.redisService.multiget(keys);
    }

    @GetMapping("/rangelists/{key}/{start}/{end}")
    public String rangeLists(@PathVariable("key") String key,@PathVariable("start") long start,@PathVariable("end") long end) {
        return this.redisService.rangeLists(key,start,end).toString();
    }

    @GetMapping("/rangelist/{key}/{start}/{end}")
    public List<Object> rangeList(@PathVariable("key") String key, @PathVariable("start") long start, @PathVariable("end") long end) {
        return this.redisService.rangeList(key, start, end);
    }

    @GetMapping("/rangeoflist/{key}/{start}/{end}")
    public List<Object> rangeofList(@PathVariable("key") String key, @PathVariable("start") long start, @PathVariable("end") long end) {
        return this.redisTemplate.opsForList().range(key,start,end);
    }

    @GetMapping("/listPush/{key}/{value}")
    public Long listPush(@PathVariable("key") Object key,@PathVariable("value") Object value) {
        return this.redisTemplate.opsForList().rightPush(key,value);
    }

    @RequestMapping("/hashPut")
    public void hashPut() {
        this.redisTemplate.opsForHash().put("hash",0,"zero");
    }

    @RequestMapping("/hashMultiPut")
    public void hashMultiPut() {
        Map<Object,Object> map=new HashMap<>();
        map.put(1,"one");
        map.put(2,"two");
        map.put(3,"three");
        this.redisTemplate.opsForHash().putAll("hash",map);
    }

    @RequestMapping("/hashDelete")
    public Long hashDelete() {
        return  this.redisTemplate.opsForHash().delete("hash",2);
    }

    @RequestMapping("/isHashExists")
    public Boolean isHashExists() {
        return this.redisTemplate.opsForHash().hasKey("hash",2);
    }

    @RequestMapping("/getHashValueByHashKey")
    public Object getHashValueByHashKey() {
        return this.redisTemplate.opsForHash().get("hash",1);
    }

    @RequestMapping("/getMultiHashValue")
    public List<Object> getMultiHashValue() {
        List<Object> objectList=new ArrayList<Object>();
        objectList.add(1);
        objectList.add(3);
        return this.redisTemplate.opsForHash().multiGet("hash",objectList);
    }

    //获取key所对应的散列表的key（即各项的key）
    @RequestMapping("/getKeysOfHash")
    public Set<Object> getKeysOfHash() {
        return this.redisTemplate.opsForHash().keys("hash");
    }

        @RequestMapping("/getValuesOfHash")
    public List<Object> getValuesOfHash() {
        return this.redisTemplate.opsForHash().values("hash");
    }

    @RequestMapping("/getSizeOfHash")
    public Long getSizeOfHash() {
        return this.redisTemplate.opsForHash().size("hash");
    }

//    Cursor<Map.Entry<HK, HV>> scan(H key, ScanOptions options);
//    使用Cursor在key的hash中迭代，相当于迭代器。
    @RequestMapping("/cursorTest")
    public void cursorTest() {
        ScanOptions scanOptions=ScanOptions.NONE;
//        ScanOptions scanOptions=ScanOptions.scanOptions().count(1).match("*").build();
        Cursor<Map.Entry<Object,Object>> cursor=this.redisTemplate.opsForHash().scan("hash",scanOptions);
        while (cursor.hasNext()) {
            Map.Entry<Object,Object> entry=cursor.next();
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }

    @RequestMapping("/addSet")
    public void addSet() {
        this.redisTemplate.opsForSet().add("sets","th");
        this.redisTemplate.opsForSet().add("setss","th");
        this.redisTemplate.opsForSet().add("set","th");
    }

    @RequestMapping("/removeSet")
    public Long removeSet() {
        String[] strings=new String[]{"two","1"};
        return this.redisTemplate.opsForSet().remove("set",strings);
    }

    //移除并返回集合中的一个随机元素
    @RequestMapping("/popSet")
    public Object popSet() {
        return this.redisTemplate.opsForSet().pop("set");
    }

//    将 member 元素从 source 集合移动到 destination 集合
    @RequestMapping("/moveSetBetweenTwoSet")
    public Boolean moveSetBetweenTwoSets() {
        return this.redisTemplate.opsForSet().move("set","four","sets");
    }

//    key对应的无序集合与otherKey对应的无序集合求交集
    @RequestMapping("/intersectSet")
    public Set<Object> intersectSet() {
        return this.redisTemplate.opsForSet().intersect("set","sets");
    }

    //key对应的无序集合与多个otherKey对应的无序集合求交集
    @RequestMapping("intersectMultiSet")
    public Set<Object> intersectMultiSet() {
        List<String> otherKeys=new ArrayList<String>();
        otherKeys.add("sets");
        otherKeys.add("setss");
        return this.redisTemplate.opsForSet().intersect("set",otherKeys);
    }

//    key无序集合与otherkey无序集合的交集存储到destKey无序集合中
    @RequestMapping("intersectSetAndStore")
    public Long intersectSetAndStore() {
        return this.redisTemplate.opsForSet().intersectAndStore("set","sets","intersectDestSet");
    }

    //key无序集合与otherKey无序集合的并集【其他同交集】
    @RequestMapping("/unuinSet")
    public Set<Object> unuinSet() {
        return this.redisTemplate.opsForSet().union("set","sets");
    }

    //key无序集合与otherKey无序集合的差集
    @RequestMapping("/differenceSet")
    public Set<Object> differencrSet() {
        return this.redisTemplate.opsForSet().difference("set","sets");
    }

    @RequestMapping("membersSet")
    public Set<Object> memberSet() {
        return this.redisTemplate.opsForSet().members("set");
    }

    @RequestMapping("/RandomMenberSet")
    public Object randomSet() {
        return this.redisTemplate.opsForSet().randomMembers("set",2);
    }

    //cursor遍历set
    @RequestMapping("cursorSet")
    public void cursorSet() {
        ScanOptions scanOptions=ScanOptions.NONE;
        Cursor<Object> cursor=this.redisTemplate.opsForSet().scan("set",scanOptions);
        while (cursor.hasNext()){
            System.out.println(cursor.next());
        }
    }



    //zset
    @RequestMapping("/addZset")
    public void addZset() {
        ZSetOperations.TypedTuple<Object> objectTypedTuple0 = new DefaultTypedTuple<Object>(0,6.3);
        ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>(1,4.2);
        ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<Object>(2,5.4);
        ZSetOperations.TypedTuple<Object> objectTypedTuple3 = new DefaultTypedTuple<Object>(3,1.3);
        ZSetOperations.TypedTuple<Object> objectTypedTuple4 = new DefaultTypedTuple<Object>(4,9.9);
        ZSetOperations.TypedTuple<Object> objectTypedTuple5 = new DefaultTypedTuple<Object>(5,7.4);
        ZSetOperations.TypedTuple<Object> objectTypedTuple6 = new DefaultTypedTuple<Object>(6,2.5);
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<ZSetOperations.TypedTuple<Object>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        tuples.add(objectTypedTuple3);
        tuples.add(objectTypedTuple4);
        tuples.add(objectTypedTuple5);
        tuples.add(objectTypedTuple6);
        tuples.add(objectTypedTuple0);
        System.out.println(this.redisTemplate.opsForZSet().add("zset",tuples));
        System.out.println(this.redisTemplate.opsForZSet().range("zset",0,-1));
    }


    @RequestMapping("/removeZset")
    public Long removeZset() {
        return this.redisTemplate.opsForZSet().remove("zset",1,5);
    }


    //返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
    @RequestMapping("/rankZset")
    public Long rankZset() {
        return this.redisTemplate.opsForZSet().rank("zset",2);
    }

    //返回有序集中指定成员的排名，其中有序集成员按分数值递减(从大到小)顺序排列
    @RequestMapping("/reverseZset")
    public Long reverseZset() {
        return this.redisTemplate.opsForZSet().reverseRank("zset",2);
    }

    //通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
    @RequestMapping("/rangeZset")
    public Set<Object> rangeZset() {
        return this.redisTemplate.opsForZSet().range("zset",0,2);
    }

    //通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
    @RequestMapping("/rangeWithScoresOfZset")
    public void rangeWithScoresOfZset() {
        Set<ZSetOperations.TypedTuple<Object>> tuples=this.redisTemplate.opsForZSet().rangeWithScores("zset",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator=tuples.iterator();
        while (iterator.hasNext()) {
            ZSetOperations.TypedTuple<Object> typedTuple=iterator.next();
            System.out.println("value: "+typedTuple.getValue()+" score: "+typedTuple.getScore());
        }
    }

    //通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
    //Set<V> rangeByScore(K key, double min, double max);
    @RequestMapping("/rangeByScoresOfZset")
    public Set<Object> rangeByScoresOfZset() {
        return this.redisTemplate.opsForZSet().rangeByScore("zset",2,5);
    }

    //通过分数返回有序集合指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
    //Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max);
    @RequestMapping("/rangeByScoreWithScores")
    public void rangeByScoreWithScores() {
        Set<ZSetOperations.TypedTuple<Object>> tuples = this.redisTemplate.opsForZSet().rangeByScoreWithScores("zset",0,5);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + " score:" + typedTuple.getScore());
        }
    }

    //通过分数返回有序集合指定区间内的成员，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
    //Set<V> rangeByScore(K key, double min, double max, long offset, long count);
    @RequestMapping("rangeByScore")
    public Set<Object> rangeByScore() {
        return this.redisTemplate.opsForZSet().rangeByScore("zset",1,6,1,2);
    }

    //通过分数返回有序集合指定区间内的成员个数
    //Long count(K key, double min, double max);
    @RequestMapping("/countBetweenOfZset")
    public Long countBetweenOfZset() {
        return  this.redisTemplate.opsForZSet().count("zset",1,5);
    }

    //获取有序集合的成员数，内部调用的就是zCard方法
    @RequestMapping("/sizeOfZset")
    public Long sizeOfZset() {
        return this.redisTemplate.opsForZSet().size("zset");
    }

    //获取有序集合的成员数
    @RequestMapping("/zCardZset")
    public Long zCardZset() {
        return this.redisTemplate.opsForZSet().zCard("zset");
    }

    //获取指定成员的score值
    @RequestMapping("/getScore")
    public Double scoreZset() {
        return this.redisTemplate.opsForZSet().score("zset",3);
    }


    //curosr遍历
    @RequestMapping("/cursorZset")
    public void cursorZset() {
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = this.redisTemplate.opsForZSet().scan("zset", ScanOptions.NONE);
        while (cursor.hasNext()){
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            System.out.println(item.getValue() + " : " + item.getScore());
        }
    }




    //测试订阅模式
    @RequestMapping("/message")
    public void pubMessage() {
//        User user = new User();
//        user.setUid(100);
//        user.setBirthday("nen");
//        user.setAddress("hdf");
//        user.setUname("one hundred");
        this.messagePub.convertAndSend(RedisConfiguration.RedisChannel.USER_CHANNEL, 2);
    }

    @RequestMapping("/messages")
    public void pubMessage1() {
//        User user = new User();
//        user.setUid(1000);
//        user.setBirthday("nens");
//        user.setAddress("hdfs");
//        user.setUname("one hundreds");
        this.messagePub.convertAndSend(RedisConfiguration.RedisChannel.USER2_CHANNEL, 3);
    }



    //分布式锁


}
