package com.nmeducode.text.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName: Producer01
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
public class Producer01 {
    //队列
    private static  final  String QUEUE="helloworld";
    public static void main(String[] args) throws Exception {
        //t通过连接工厂创建新的连接和mq建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);//端口
        connectionFactory.setUsername("guest");//账号
        connectionFactory.setPassword("guest");//密码
        connectionFactory.setVirtualHost("/");
        Connection connection=null;
        Channel channel =null;
        try {
            //建立新连接
           connection=connectionFactory.newConnection();
           //创建会话通道生产者和mq服务所有通信都在channel通道中完成
            channel = connection.createChannel();
            /**
             * 声明队列，如果Rabbit中没有此队列将自动创建
             * param1:队列名称
             * param2:是否持久化
             * param3:队列是否独占此连接
             * param4:队列不再使用时是否自动删除此队列
             * param5:队列参数
             */
            channel.queueDeclare(QUEUE,true,false,false,null);
            String message="hello world 我发的法师答复"+System.currentTimeMillis();
            /**
             * 消息发布方法
             * param1：Exchange的名称，如果没有指定，则使用Default Exchange
             * param2:routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
             * param3:消息包含的属性
             * param4：消息体
             */
            /**
             * 这里没有指定交换机，消息将发送给默认交换机，每个队列也会绑定那个默认的交换机，但是不能显
             示绑定或解除绑定
             * 默认的交换机，routingKey等于队列名称
             */

            channel.basicPublish("",QUEUE,null,message.getBytes());
            System.out.println("Send Message is"+message);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
          if(channel != null){
             channel.close();
          }
          if(connection!= null){
              connection.close();
          }
        }
    }
}
