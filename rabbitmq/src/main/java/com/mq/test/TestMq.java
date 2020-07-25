package com.mq.test;

import com.mq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ClassName: TestMq
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
public class TestMq {

        private final static String QUEUE_NAME = "simple_queue";

        public static void main(String[] args) throws IOException {
            //创建连接
            Connection connection = ConnectionUtil.getConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //声明队列
            /**
             * 队列名
             * 是否持久化
             *  是否排外  即只允许该channel访问该队列   一般等于true的话用于一个队列只能有一个消费者来消费的场景
             *  是否自动删除  消费完删除
             *  其他属性
             *
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            //消息内容
            /**
             * 交换机
             * 队列名
             * 其他属性  路由
             * 消息body
             */
            String message = "错的不是我，是这个世界~";
            channel.basicPublish("", QUEUE_NAME,null,message.getBytes());
            System.out.println("[x]Sent '"+message + "'");

            //最后关闭通关和连接
            channel.close();
            connection.close();


        }
    }

