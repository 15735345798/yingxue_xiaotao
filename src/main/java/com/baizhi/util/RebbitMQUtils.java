package com.baizhi.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author:xiaotao
 * @time 2021/1/2-22:35
 */
public class RebbitMQUtils {
    private static ConnectionFactory factory;

    static {
        factory = new ConnectionFactory();
        factory.setHost("192.168.109.130");//IP地址
        factory.setPort(5672);//端口
        factory.setVirtualHost("yingx");//虚拟主机
        factory.setUsername("xiaotao");//用户名
        factory.setPassword("123456");//密码
    }

    public static Connection getConnection() throws IOException, TimeoutException {
        //根据链接工厂
        Connection connection = factory.newConnection();
        return connection;
    }

    public static void closeConnetion(Channel channel, Connection connection) throws IOException, TimeoutException {
        if (channel != null) {
            channel.close();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
