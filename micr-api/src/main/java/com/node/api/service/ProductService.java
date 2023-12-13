package com.node.api.service;

import com.node.api.model.MultiProduct;
import com.node.api.pojo.ProductInfo;

import java.util.List;

/**
 * 产品接口
 */
public interface ProductService {

    // 查询产品详细信息
    ProductInfo queryProductInfoById(Integer productId);

    // 查询多种产品信息
    MultiProduct queryIndexPageProducts();

    // 根据产品类型查询产品，支持分页
    List<ProductInfo> queryByTypeLimit(Integer productType, Integer pageNo, Integer pageSize);

    Integer queryRecordNumsByType(Integer productType);
}
