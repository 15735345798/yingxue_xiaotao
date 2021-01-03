package com.baizhi;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author:xiaotao
 * @time 2021/1/3-18:53
 */
@SpringBootTest
public class SpringRebbit {
    @Resource
    RabbitTemplate rabbitTemplate;

    @Test
    void testone() {
        //使用生产者发送消息
        rabbitTemplate.convertAndSend("springBootone", "我是hello的发送方式");
    }
}
