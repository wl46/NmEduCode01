package com.nmeducode.text.rebbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: Producer01
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
public class Consumer01 {
    //队列
    private static  final  String QUEUE="helloworld";

    public static void main(String[] args) throws Exception, TimeoutException {
        //设置MabbitMq所在服务的ip和端口发
        //t通过连接工厂创建新的连接和mq建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);//端口
        connectionFactory.setUsername("guest");//账号
        connectionFactory.setPassword("guest");//密码
        connectionFactory.setVirtualHost("/");
        //建立新连接
        Connection connection = connectionFactory.newConnection();

       Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE,true,false,false,null);
        //定义消费方法
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            //当接收消息后此方法将被
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String exchange = envelope.getExchange();
                //消息idMq在channel中用标识消息id可用于确认消息已接收
                long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String message = new String(body, "utf-8");
                System.out.println("receive message:"+message);
            }
        };
        //队列名称

                channel.basicConsume(QUEUE,true,defaultConsumer);
        /**
         * 消费者收消息调用此方法
         * @param consumerTag 消费者的标签 在channel.basicConsume去指定
         * @param envelope 消息包的内容，可从中获取消息id，消息routingkey 交换机消息和重传标志（收到消息失败后市否需要重新发送）
         * @param properties
         * @param body
         * @throws IoException
         */

    }
}
