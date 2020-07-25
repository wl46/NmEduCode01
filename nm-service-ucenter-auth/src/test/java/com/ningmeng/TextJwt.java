package com.ningmeng;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: auth
 * @Description: TODO
 * @Auther: wangli
 * @Date:
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TextJwt {
    //创建jwt令牌
    @Test
    public void testCreateJwt(){
        //密钥库文件
        String keystrore="nm.keystore";
        //密钥别名
        String alias="alias";
        //密钥库的密码
        String keystore_password="ningmeng";
        //密钥库文件路径
        ClassPathResource classPathResource = new ClassPathResource(keystrore);
        //密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,keystore_password.toCharArray());
        //密钥对（公钥和私钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, keystore_password.toCharArray());
        //获取私钥
        PrivateKey aPrivate = keyPair.getPrivate();
        //jwt令牌的内容
        Map<String, String> body = new HashMap<>();
        body.put("name","itcast");
        String jsonString = JSON.toJSONString(body);

        //生成jwt令牌
        Jwt encode = JwtHelper.encode(jsonString, new RsaSigner((RSAPrivateKey) aPrivate));
        //生成jwt令牌编辑
        String encoded = encode.getEncoded();
        System.out.println(encoded);

    }
    //资源服务使用公钥验证jwt的合法性，并对jwt解码
    @Test
    public void testVerify(){
//jwt令牌
        String token=" eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoibmluZ21lbmcifQ.h_kmM8nkhSQjWDUSVNQuNB7ML j4iUeyw4VjYEkA4UPxeMjqZM2fk9Oorjoy1cUR3SQLyYw2eq0vSCTZDMWq-cbLprE-gCAwsNNKuUBUo3cLHrYLxnJ fKlO9mJ6GzweLog9lnzjkecnzI8DfvYriylWk3sbytNOUk7WidklxpM0QDq7PZCBcFgFOGF3XI7iKOkjrlYJjzwHK 3aUzJQD-7k3M6nX-WGz_EJzLIdKO0INoD_KLKWtEtsyqGHeZd8sg2GIf9ktXSz5BskD8nqIA9KvNk1DrY2Td5jhv46AI0414LfawCIGdcx4cuyCOY_4JlRtlwbgqbJyjygQOuWE7G7A";
//公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0JWLscE2/Xz9OcQ9+H4LuP/ifrTdM7dZoga/t 1xMH37GEdYOmwRLidiUYHkuTRTaWNgaTthtbyKsByVMOwTc+zpRf2nR9YAde8+ZNysk6gHjtfcEJ2qzx+Gr1SZMC2 7uuXKg1SktIzpvI5q+eBE+QUVtHG/nMfqEDPFtoyfasi6eSenWvw/MChc2wPEDTW/oTghzS99Jx5wfhUjf3Zf05Vo tyBjqOgywV6XlOpWjE/P4BV2NKj6TMs5+/gQJnoB9FmGRt7FPr7kBBHRq8YJXaOjOalvGZ9xPaL8F5uKZ571z7fgq CBLhzeHA5B+tYOdedEGx9Y47qYKyW7v+gh/+RQIDAQAB-----END PUBLIC KEY-----";
//校验jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));
//获取jwt原始内容
        String claims = jwt.getClaims();
//jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
