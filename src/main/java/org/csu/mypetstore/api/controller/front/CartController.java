package org.csu.mypetstore.api.controller.front;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.csu.mypetstore.api.Common.CommonResponse;
import org.csu.mypetstore.api.service.CartService;
import org.csu.mypetstore.api.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart/")
public class CartController
{
    @Autowired
    private CartService cartService;

    @PostMapping("cartitems/{id}")
    @ResponseBody
    public CommonResponse<List<CartItemVO>> addCartItem(@PathVariable("id") String itemId, HttpServletRequest request)
    {
        String token = request.getHeader("Authorization").substring(7);
        return cartService.addCartItem(getUsername(token), itemId);
    }

    @PutMapping("cartitems/{id}")
    @ResponseBody
    public CommonResponse<List<CartItemVO>> updateCartItem(@PathVariable("id") String itemId, @RequestParam("quantity") int quantity, HttpServletRequest request)
    {
        String token = request.getHeader("Authorization").substring(7);
        return cartService.updateCartItem(getUsername(token), itemId, quantity);
    }

    @DeleteMapping("cartitems/{id}")
    @ResponseBody
    public CommonResponse<List<CartItemVO>> removeCartItem(@PathVariable("id") String itemId, HttpServletRequest request)
    {
        String token = request.getHeader("Authorization").substring(7);
        return cartService.removeCartItem(getUsername(token), itemId);
    }

    @GetMapping("cartitems")
    @ResponseBody
    public CommonResponse<List<CartItemVO>> getCartItemList(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization").substring(7);
        return cartService.getCartItemList(getUsername(token));
    }

    @DeleteMapping("cartitems")
    @ResponseBody
    public CommonResponse<List<CartItemVO>> clear(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization").substring(7);
        return cartService.clear(getUsername(token));
    }

    private String getUsername(String token)
    {
        DecodedJWT decodedJWT = JWTUtil.vertify(token);
        String username = decodedJWT.getClaim("username").asString();
        return username;
    }
}
