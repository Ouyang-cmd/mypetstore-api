package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.Common.CommonResponse;
import org.csu.mypetstore.api.entity.CartItem;
import org.csu.mypetstore.api.vo.CartItemVO;

import java.util.List;

public interface CartService
{
    CommonResponse<List<CartItemVO>> getCartItemList(String username);

    CommonResponse<CartItemVO> getCartItem(String username, String itemId);

    CommonResponse<List<CartItemVO>> updateCartItem(String username, String itemId, int quantity);

    CommonResponse<List<CartItemVO>> addCartItem(String username, String itemId);

    CommonResponse<List<CartItemVO>> removeCartItem(String username, String itemId);

    CommonResponse<List<CartItemVO>> clear(String username);
}
