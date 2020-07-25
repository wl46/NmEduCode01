package com.ningmeng;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TestRadis
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRadis {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void  testRadis(){
        //定义key
        String key="user_token:9734b68f‐cf5e‐456f‐9bd6‐df578c711390";
        //定义map
        Map<String, String> mapValue = new HashMap<>();
        mapValue.put("id","101");
        mapValue.put("username","ningmeng");
        String jsonString = JSON.toJSONString(mapValue);
        //向radis中存储字符串
        stringRedisTemplate.boundValueOps(key).set(jsonString,60, TimeUnit.SECONDS);
        //读取过期时间，已过期返回-2
        Long expire = stringRedisTemplate.getExpire(key);
        //根据key获取value
        String s = stringRedisTemplate.opsForValue().get(key);
        System.out.println(s);


    }
}
