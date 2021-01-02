package com.baizhi;

import com.baizhi.util.RebbitMQUtils;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author:xiaotao
 * @time 2021/1/2-21:20
 */
public class Rebbimaplication {
    //第一种直连式
    //生产者向对列发送消息
    @Test
    void test1() throws IOException, TimeoutException {
        Connection connection = RebbitMQUtils.getConnection();
        //创建连接通道
        Channel channel = connection.createChannel();
        //设置连接参数   参数：对列名称（不存在自动创建）、是否持久化、是否独占队列、是否自动删除、额外参数
        /**设置队列参数
         * @param queue 队列名称  如果这个队列不存在，将会被创建
         * @param durable 持久性：用来定义队列是否要持久化  true:持久化  false:不持久化
         * @param exclusive 是否只能由创建者使用，其他连接不能使用。 true:独占队列  false:不独占队列
         * @param autoDelete 是否自动删除（没有连接自动删除） true:自动删除   false:不自动删除
         * @param arguments 队列的其他属性(构造参数)
         */
        channel.queueDeclare("yingxuetest1", false, false, false, null);

        String message = "hello word";
        /**消费者消费消息
         * @param queue 队列名称
         * @param autoAck 是否自动应答。false表示consumer在成功消费过后必须要手动回复一下服务器，如果不回复，服务器就将认为此条消息消费失败，继续分发给其他consumer。
         * @param callback 回调方法类，一般为自己的Consumer类
         */
        //设置发送消息参数    参数：交换机名称、队列名称、队列是否持久化、发送的内容、
        channel.basicPublish("", "yingxuetest1", null, message.getBytes());

        //关闭连接

        RebbitMQUtils.closeConnetion(channel, connection);
    }

    //创建一个消费者消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RebbitMQUtils.getConnection();
        //创建连接通道
        Channel channel = connection.createChannel();
        //设置连接参数   参数：对列名称（不存在自动创建）、是否持久化、是否独占队列、是否自动删除、额外参数
        /**设置队列参数
         * @param queue 队列名称  如果这个队列不存在，将会被创建
         * @param durable 持久性：用来定义队列是否要持久化  true:持久化  false:不持久化
         * @param exclusive 是否只能由创建者使用，其他连接不能使用。 true:独占队列  false:不独占队列
         * @param autoDelete 是否自动删除（没有连接自动删除） true:自动删除   false:不自动删除
         * @param arguments 队列的其他属性(构造参数)
         */
        channel.queueDeclare("yingxuetest1", false, false, false, null);

        //获取消息   参数：队列名称,是否自动发送回执，创建消费者获取消息
        channel.basicConsume("yingxuetest1", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("消费者获取消息： " + message);
            }
        });
        RebbitMQUtils.closeConnetion(channel, connection);
    }


}
