package com.ningmeng.auth.service;

import com.alibaba.fastjson.JSON;
import com.ningmeng.framework.client.NmServiceList;
import com.ningmeng.framework.domain.ucenter.ext.AuthToken;
import com.ningmeng.framework.domain.ucenter.response.AuthCode;
import com.ningmeng.framework.exception.ExceptionCast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: AuthService
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@Service
public class AuthService {
     @Autowired
    LoadBalancerClient loadBalancerClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    //用户认证申请令牌,将令牌存储到redis里
    public AuthToken login(String username, String password, String clientid, String clientSecret) {
      //申请令牌
        AuthToken authToken = applyToken(username, password, clientid, clientSecret);
        if(authToken==null){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR);
        }
        //将token存储到redis
        String access_token = authToken.getAccess_token();
        String content = JSON.toJSONString(authToken);
        boolean saveTokenResult = saveToken(access_token, content, tokenValiditySeconds);
        if(!saveTokenResult){
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR);
        }
        return authToken;
    }
    //存储令牌到redis
    private  boolean saveToken(String access_token,String content,long ttl){
        //令牌名称
        String name="user_token:"+access_token;
        //保存到令牌到redis
        stringRedisTemplate.boundValueOps(name).set(content,ttl, TimeUnit.SECONDS);
        //获取过期时间
        Long expire = stringRedisTemplate.getExpire(name);
        return expire>0;
    }
    //获取httpbasic认证串
    private String httpbasic(String clientId,String clicentSecret){
        //将客户端Id和客户端密码拼接按客户端id：客户端密码
        String string=clientId+":"+clicentSecret;
        //进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic"+new String(encode);

    }
    //申请令牌
    private AuthToken applyToken(String username,String password,String clientId,String clientSecret){
        //选中认证服务的地址
        ServiceInstance choose = loadBalancerClient.choose(NmServiceList.NM_SERVICE_UCENTER_AUTH);
        if(choose==null){
            LOGGER.error("choose an auth instance fail");
            ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
        }
        //获取令牌的url
        String path=choose.getUri().toString()+"/auth/oauth/token";
        //定义body
        LinkedMultiValueMap<String, String> forData = new LinkedMultiValueMap<>();
        //授权方式
        forData.add("grant_type","password");
        //账号
        forData.add("username",username);
        //密码
        forData.add("password",password);
        //定义头
       MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization",httpbasic(clientId,clientSecret));
        //指定restTemplate当遇到400或401响应的时候也不要抛出异常，也要正常返回值
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
                //当响应的值为400或401时候也要正常响应，不要抛出异常
                if(response.getRawStatusCode()!=400&&response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }

        });
        Map map=null;
        try {
            //http请求spring security的申请令牌接口
            ResponseEntity<Map> mapResponseEntity = restTemplate.exchange(path, HttpMethod.POST, new HttpEntity<MultiValueMap<String, String>>(forData, header), Map.class);
            map=mapResponseEntity.getBody();
        }catch (RestClientException e){
            e.printStackTrace();
            LOGGER.error("request oauth_token_password error: {}",e.getMessage());
            e.printStackTrace();
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR);
        }
        if(map == null ||
                map.get("access_token") == null ||
                map.get("refresh_token") == null ||
                map.get("jti") == null){

            //jti是jwt令牌的唯一标识作为用户身份令牌
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_ERROR);
        }
        AuthToken authToken = new AuthToken();
        //访问令牌（jwt）
        String jwt_token = (String) map.get("access_token");
        //刷新令牌（jwt）
        String refresh_token = (String) map.get("refresh_token");
        //jti 作为用户的身份标识
       String access_token= (String) map.get("jti");
       authToken.setJwt_token(jwt_token);
       authToken.setAccess_token(access_token);
       authToken.setRefresh_token(refresh_token);
       return authToken;
    }
    //从redis查询令牌
    public AuthToken getUserToke(String token) {
        String userToken = "user_token:" + token;
        String userTokenString = stringRedisTemplate.opsForValue().get(userToken);
        if (userToken != null) {
            AuthToken authToken = null;
            try {
                authToken = JSON.parseObject(userTokenString, AuthToken.class);
            } catch (Exception e) {
                LOGGER.error("getUserToken from redis and execute JSON.parseObject error\n" +
                        "{}", e.getMessage());
                e.printStackTrace();
            }
            return authToken;
        }
        return null;
    }

    //从redis中删除命牌
    public boolean delToken(String access_token){
        String name="user_token:"+access_token;
        stringRedisTemplate.delete(name);
        return true;
    }

}
