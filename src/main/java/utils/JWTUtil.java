package utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtil
{

    private static final String SIGN = "key";

    public static String getToken(Map<String, String> map)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);

        //创建JWT builder
        JWTCreator.Builder builder = JWT.create();

        //payload
        map.forEach((k, v)->{
            builder.withClaim(k, v);
        });

        String token = builder.withExpiresAt(calendar.getTime())//过期时间
                .sign(Algorithm.HMAC256(SIGN));//sign

        return token;
    }

    //token合法性，合法即从token中取出信息返回，不合法抛出异常
    public static DecodedJWT vertify(String token)
    {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        //DecodedJWT vertify = jwtVerifier.verify(token);
    }

}
