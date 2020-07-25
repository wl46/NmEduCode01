package com.nmeducode.text.rabbitmq;

import com.nmeducode.text.rebbitmq.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName: Producer05_topics_springboot
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05_topics_springboot {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void testSendByTopices(){
        for (int i=0;i<5;i++){
            String message="sms email inform to user"+i;
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.sms.email",message);
            System.out.println("Send Message is:"+message);
        }
    }
}
