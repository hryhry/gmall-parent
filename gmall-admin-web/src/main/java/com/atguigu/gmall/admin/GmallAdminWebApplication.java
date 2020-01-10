package com.atguigu.gmall.admin;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


/*
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
 1.不进行数据源的自动配置
 如果导入的依赖，引入了一个自动配置的场景
 1） 这个场景自动配置默认生效，我们就必须配置
 2） 不想配置它，
        1.引入的是后排除这个场景依赖
        2.排斥掉这个场景的自动配置类
*/

@EnableDubbo
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GmallAdminWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallAdminWebApplication.class, args);
    }

}
