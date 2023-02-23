package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.Common.CommonResponse;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CatalogService
{

    CommonResponse<List<Category>> getCategoryList();

    CommonResponse<Category> getCategory(String categoryId);

    CommonResponse<List<Product>> getProductListByCategoryId(String categoryId);

    CommonResponse<Product> getProductById(String productId);

    CommonResponse<List<ItemVO>> getItemsByProductId(String productId);

    CommonResponse<ItemVO> getItem(String itemId);

}
