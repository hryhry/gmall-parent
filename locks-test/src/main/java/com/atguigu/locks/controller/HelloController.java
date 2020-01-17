package com.atguigu.locks.controller;


import com.atguigu.locks.service.RedisIncrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    RedisIncrService redisIncrService;

    @GetMapping("/incr")
    public String incr() {

        redisIncrService.incr();
        return "ok";
    }

    @GetMapping("/incrDintribute")
    public String incrDintribute() {

        redisIncrService.incrDistribute02();
        return "ok";
    }


}
