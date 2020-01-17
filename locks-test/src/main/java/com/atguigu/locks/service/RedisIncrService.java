package com.atguigu.locks.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;


import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RedisIncrService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    JedisPool jedisPool;

    @Autowired
    RedissonClient redissonClient;
    AtomicInteger integer = new AtomicInteger(1);
    int i = 1;

    public void useRedissonForLock() {

        // 利用CAS算法可以结算。 ABA
        integer.incrementAndGet();

        // 1.获取一把锁。只要各个代码，用的锁名一样即可
        RLock lock = redissonClient.getLock("lock");

        try {

            //lock.lock();//一直等待。阻塞住
            //感知别人删锁。发布订阅模式（实时感知）。   lock监听redis，redis一旦删锁。赶紧尝试去加锁。

            lock.lock(3, TimeUnit.SECONDS); //加锁带自动解锁
            Jedis jedis = jedisPool.getResource();
            String num = jedis.get("num");
            Integer i = Integer.parseInt(num);
            i = i+1;
            jedis.set("num", i.toString());
            jedis.close();

        } finally {
            lock.unlock();  //解锁
        }



    }



    public void incrDistribute() {

        //Object hello = map.get("hello");

        //1、占坑。（原子性）
        //1）、先判断没有，2）、再给里面放值



        ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();

        String num = stringStringValueOperations.get("num");
        if (num != null) {
            Integer i = Integer.parseInt(num);
            i += 1;
            stringStringValueOperations.set("num", i.toString());
        }

    }





    public synchronized void incr() {

        redisTemplate.opsForValue().increment("num");
    }


//RedisTemplate和Jedis客户端2选一

    //使用 redisTemplate
    public void incrDistribute02() {

        //加锁
        String token = UUID.randomUUID().toString();
        //设置的数据在三秒后就失效，如果在3秒内执行完成就直接进行删除操作
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", token, 3, TimeUnit.SECONDS);
        if (lock) {
            ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
            String num = stringStringValueOperations.get("num");
            if (null != num) {

                Integer i = Integer.parseInt(num);
                i += 1;
                stringStringValueOperations.setIfAbsent("num", i.toString());
            }

            //删除锁
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            DefaultRedisScript<String> script1 = new DefaultRedisScript<>(script);
            redisTemplate.execute(script1, Arrays.asList("lock"), token);
            System.out.println("删除锁完成...");

        } else {
            incrDistribute02();
        }


    }

    //使用 jedis
    public void incrDistribute03() {
        //从redis客户端连接池中获取一个客户端
        Jedis jedis = jedisPool.getResource();

        try {
            //创建唯一标识符
            String token = UUID.randomUUID().toString();
            //将锁放入redis中
            String lock = jedis.set("lock", token, SetParams.setParams().ex(3).nx());
            //
            if (lock != null && lock.equalsIgnoreCase("OK")) {
                // ok
                String num = jedis.get("num");
                Integer i = Integer.parseInt(num);
                i = i + 1;
                jedis.set("num", i.toString());

                //删除锁
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                jedis.eval(script, Collections.singletonList("lock"), Collections.singletonList(token));
                System.out.println("删除锁ok....");

            } else {
                try {
                    Thread.sleep(1000);
                    incrDistribute();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            jedis.close();
        }


    }



}
