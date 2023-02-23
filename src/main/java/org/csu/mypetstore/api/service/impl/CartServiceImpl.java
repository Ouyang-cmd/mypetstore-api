package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Param;
import org.csu.mypetstore.api.Common.CommonResponse;
import org.csu.mypetstore.api.entity.CartItem;
import org.csu.mypetstore.api.entity.Item;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.persistence.CartItemMapper;
import org.csu.mypetstore.api.persistence.ItemInventoryMapper;
import org.csu.mypetstore.api.persistence.ItemMapper;
import org.csu.mypetstore.api.persistence.ProductMapper;
import org.csu.mypetstore.api.service.CartService;
import org.csu.mypetstore.api.vo.CartItemVO;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("cartService")
public class CartServiceImpl implements CartService
{

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public CommonResponse<List<CartItemVO>> getCartItemList(String username)
    {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<CartItem> cartItemList = cartItemMapper.selectList(queryWrapper);
        if(cartItemList.isEmpty())
        {
            return CommonResponse.creatForSuccessMessage("购物车为空");
        }

        List<CartItemVO> cartItemVOList = new ArrayList<>();
        for(CartItem cartItem : cartItemList)
        {
            Item item = itemMapper.selectById(cartItem.getItemId());
            Product product = productMapper.selectById(item.getProductId());
            CartItemVO cartItemVO = cartItemTOCartItemVO(cartItem, item, product);
            cartItemVOList.add(cartItemVO);
        }
        return CommonResponse.creatForSuccess(cartItemVOList);
    }

    @Override
    public CommonResponse<CartItemVO> getCartItem(String username, String itemId)
    {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("itemid", itemId);
        CartItem cartItem = cartItemMapper.selectOne(queryWrapper);
        if(cartItem == null)
        {
            return CommonResponse.creatForSuccessMessage("购物车中无此商品");
        }
        Item item = itemMapper.selectById(cartItem.getItemId());
        Product product = productMapper.selectById(item.getProductId());
        CartItemVO cartItemVO = cartItemTOCartItemVO(cartItem, item, product);
        return CommonResponse.creatForSuccess(cartItemVO);
    }

    @Override
    public CommonResponse<List<CartItemVO>> updateCartItem(String username, String itemId, int quantity)
    {
        CartItem cartItem = new CartItem();
        cartItem.setUsername(username);
        cartItem.setItemId(itemId);
        cartItem.setInStock(true);
        cartItem.setQuantity(quantity);
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("itemid", itemId);
        cartItemMapper.update(cartItem, queryWrapper);
        //cartItemMapper.updateById(cartItem);
        return getCartItemList(username);
    }

    @Override
    public CommonResponse<List<CartItemVO>> addCartItem(String username, String itemId)
    {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("itemid", itemId);
        CartItem cartItem = cartItemMapper.selectOne(queryWrapper);
        if(cartItem == null)
        {
            cartItem.setUsername(username);
            cartItem.setItemId(itemId);
            cartItem.setInStock(true);
            cartItem.setQuantity(1);
            cartItemMapper.insert(cartItem);
        }
        else
        {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemMapper.update(cartItem, queryWrapper);
            //cartItemMapper.updateById(cartItem);
        }
        return getCartItemList(username);
    }


    @Override
    public CommonResponse<List<CartItemVO>> removeCartItem(String username, String itemId)
    {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("itemid", itemId);
        cartItemMapper.delete(queryWrapper);
        return getCartItemList(username);
    }

    @Override
    public CommonResponse<List<CartItemVO>> clear(String username)
    {
        QueryWrapper<CartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        cartItemMapper.delete(queryWrapper);
        return getCartItemList(username);
    }

    private CartItemVO cartItemTOCartItemVO(CartItem cartItem, Item item, Product product)
    {
        CartItemVO cartItemVO = new CartItemVO();
        cartItemVO.setItem(item);
        cartItemVO.setProduct(product);
        cartItemVO.setUsername(cartItem.getUsername());
        cartItemVO.setItemId(cartItem.getItemId());
        cartItemVO.setQuantity(cartItem.getQuantity());
        cartItemVO.setInStock(cartItem.isInStock());
        return cartItemVO;
    }
}
