package com.atguigu.locks.controller;

import com.atguigu.locks.service.RedissonLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LockTestController {

    @Autowired
    RedissonLockService redissonLockService;


    @GetMapping("/lock")
    public String lock() {

        redissonLockService.lock();
        return "OK";

    }

    @GetMapping("/unlock")
    public String unlock() {

        redissonLockService.unlock();
        return "OK";

    }



}
