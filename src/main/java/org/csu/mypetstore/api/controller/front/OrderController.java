package org.csu.mypetstore.api.controller.front;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.csu.mypetstore.api.Common.CommonResponse;
import org.csu.mypetstore.api.service.AccountService;
import org.csu.mypetstore.api.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order/")
public class OrderController
{

    @Autowired
    private AccountService accountService;

    @GetMapping("news")
    @ResponseBody
    public CommonResponse<AccountVO> newOrder(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization").substring(7);
        return accountService.getAccount(getUsername(token));
    }

    private String getUsername(String token)
    {
        DecodedJWT decodedJWT = JWTUtil.vertify(token);
        String username = decodedJWT.getClaim("username").asString();
        return username;
    }
}
