package org.csu.mypetstore.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.persistence.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

//@SpringBootTest
class MypetstoreApiApplicationTests
{

    @Test
    void contextLoads()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 60);

        String token = JWT.create()//header省略即为默认
                .withClaim("userId", 21)//payload
                .withClaim("username", "wick")
                .withExpiresAt(calendar.getTime())//令牌过期时间
                .sign(Algorithm.HMAC256("key"));//签名

        System.out.println(token);
    }

    @Test
    public void test()
    {
        //创建验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("key")).build();
        DecodedJWT vertify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MjExMzYzNjEsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoid2ljayJ9.LyWIh9cot79m-7fJvvj5BhVjEbK9B8L9Xpc-K-cUkik");
        System.out.println(vertify.getClaim("username").asString());
        System.out.println(vertify.getClaim("userId").asInt());
    }

}
