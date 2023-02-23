package org.csu.mypetstore.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("profile")
public class AccountProfile
{
    @TableId(value = "userid", type = IdType.INPUT)
    private String username;
    @TableField(value = "favcategory")
    private String favouriteCategoryId;
    @TableField(value = "langpref")
    private String languagePreference;
    @TableField(value = "mylistopt")
    private boolean listOption;
    @TableField(value = "banneropt")
    private boolean bannerOption;
}
