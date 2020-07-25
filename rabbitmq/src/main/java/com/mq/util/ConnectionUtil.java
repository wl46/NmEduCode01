package com.mq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @ClassName: ConnectionUtil
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
public class ConnectionUtil {
    public static Connection getConnection(){
        //连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //连接5672端口  注意15672为工具界面端口  25672为集群端口
        factory.setPort(15672);
        factory.setVirtualHost("/jxd");
        factory.setUsername("guest");
        factory.setPassword("guest");
        //获取连接
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;

    }

}
