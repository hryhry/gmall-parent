package com.atguigu.locks.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedissonLockService {

    @Autowired
    RedissonClient redissonClient;

    //哪个线程加的锁，就必须由哪个线程进行解锁
    public void lock() {

        // 获取锁
        RLock lock = redissonClient.getLock("lock");
        // 加锁
        //lock.lock();  //默认是阻塞，加锁失败会一直等待加锁
        boolean b = lock.tryLock();  // tryLock()是非阻塞的，会尝试加锁，失败也会直接返回


    }

    public void unlock() {

        // 获取锁
        RLock lock = redissonClient.getLock("lock");
        // 解锁
        lock.unlock();

    }
}
