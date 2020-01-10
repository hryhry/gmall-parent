package com.atguigu.gmall.admin.aop;


import com.atguigu.gmall.to.CommonResult;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
* 统一处理所有异常，给前端返回500的json
*
* 当我们编写环绕通知的时候，目标方法出现的异常一定要再次跑出去
* */
@Log4j2
@RestControllerAdvice   //表明这是一个异常处理类，有异常了就会到这个类进行异常处理
//进行全局异常感知，如果代码出现异常并进行了异常抛出，就会执行该类中对应的方法
public class GlobeExceptionHandler {
    //注解，能处理哪些异常
    @ExceptionHandler(value = {ArithmeticException.class})
    public Object handlerException(Exception ex) {

        log.error("系统全局异常感知，信息：{}",ex.getStackTrace());
        return new CommonResult().validateFailed("数学运算错误");
    }


    @ExceptionHandler(value = {NullPointerException.class})
    public Object handlerException02(Exception ex) {

        log.error("系统全局异常感知，信息：{}",ex.getStackTrace());
        return new CommonResult().validateFailed("空指针异常");
    }


}
