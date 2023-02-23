package org.csu.mypetstore.api.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.csu.mypetstore.api.Common.CommonResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        Map<String, Object> map = new HashMap<>();
        String token = "";
        //1
        try
        {
            token = request.getHeader("Authorization").substring(7);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //map.put("msg", "请先登录");
        }
        //2
        try
        {
            JWTUtil.vertify(token);
            return true;
        }
        //2.1
        catch (SignatureVerificationException e)
        {
            e.printStackTrace();
            map.put("msg", "无效签名");
        }
        //2.2
        catch (TokenExpiredException e)
        {
            e.printStackTrace();
            map.put("msg", "token过期");
        }
        //2.3
        catch (AlgorithmMismatchException e)
        {
            e.printStackTrace();
            map.put("msg", "token算法不一致");
        }
        //2.4
        catch (Exception e)
        {
            e.printStackTrace();
            map.put("msg", "token无效");
        }
        map.put("status", 10);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        //response.getWriter().println(CommonResponse.creatForError("token错误"));
        return false;
    }
}
