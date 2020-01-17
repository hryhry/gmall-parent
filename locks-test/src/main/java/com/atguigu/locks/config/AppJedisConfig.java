package com.atguigu.locks.config;


import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class AppJedisConfig {

    @Bean
    public JedisPool jedisPoolConfig(RedisProperties properties) {

        // 1.连接工厂中所有信息都有
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        RedisProperties.Pool pool = properties.getJedis().getPool();

        //这些配置
        jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
        jedisPoolConfig.setMaxTotal(pool.getMaxActive());


        JedisPool jedisPool = new JedisPool(jedisPoolConfig, properties.getHost(), properties.getPort());

        return jedisPool;


    }



}
