package com.baizhi.controller;


import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;

/**
 * @author:xiaotao
 * @time 2021/1/3-18:53
 */
@Controller
@RabbitListener(queuesToDeclare = @Queue("springBootone"))
public class SpringRebbit2 {
    @RabbitHandler
    public void getMaeeage(String message) {
        System.out.println(message);
    }
}
