package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("cartitem")
public class CartItem
{
    @TableId(type = IdType.INPUT)
    private String username;
//    @TableId(value = "itemid", type = IdType.INPUT)
//    private String itemId;
    @TableField(value = "itemid")
    private String itemId;
    private int quantity;
    @TableField(value = "instock")
    private boolean inStock;
//    private BigDecimal total;
//    private Item item;
}
