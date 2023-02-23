package org.csu.mypetstore.api.vo;

import lombok.Data;
import org.csu.mypetstore.api.entity.Item;
import org.csu.mypetstore.api.entity.Product;

@Data
public class CartItemVO
{
    private Item item;

    private Product product;

    private int quantity;
    private boolean inStock;
    private String itemId;
    private String username;
}
