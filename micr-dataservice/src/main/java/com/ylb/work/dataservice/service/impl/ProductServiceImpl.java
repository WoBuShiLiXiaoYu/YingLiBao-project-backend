package com.ylb.work.dataservice.service.impl;

import com.node.api.model.MultiProduct;
import com.node.api.pojo.ProductInfo;
import com.node.api.service.ProductService;
import com.ylb.work.common.constants.YLBConstant;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.dataservice.mapper.ProductInfoMapper;
import io.swagger.models.auth.In;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@DubboService(interfaceClass = ProductService.class, version = "1.0")
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductInfoMapper productInfoMapper;

    // 根据产品 id 查询产品详细信息
    public ProductInfo queryProductInfoById(Integer productId) {
        ProductInfo productInfo = null;
        // 判断 id 是否合法
        if (productId != null && productId > 0) {
            productInfo = productInfoMapper.selectProductInfoById(productId);
        }
        return productInfo;
    }

    // 根据产品类型查询产品数量
    public Integer queryRecordNumsByType(Integer productType) {
        Integer nums = 0;
        // 判断产品类型
        if (productType == YLBConstant.PRODUCT_TYPE_XINSHOUBAO
                || productType == YLBConstant.PRODUCT_TYPE_YOUXUAN
                || productType == YLBConstant.PRODUCT_TYPE_SNABIAO) {
            // 查询产品记录条数
            nums = productInfoMapper.selectCountByType(productType);
        }
        return nums;
    }

    // 查询多种产品信息
    @Override
    public MultiProduct queryIndexPageProducts() {
        MultiProduct result = new MultiProduct();

        // 调用产品分页查询方法
        // 查询新手宝信息
        List<ProductInfo> xinShouList = productInfoMapper.selectByTypeLimit(YLBConstant.PRODUCT_TYPE_XINSHOUBAO, 0, 1);
        // 查询优选信息
        List<ProductInfo> youXuanList = productInfoMapper.selectByTypeLimit(YLBConstant.PRODUCT_TYPE_YOUXUAN, 0, 3);
        // 查询散标
        List<ProductInfo> sanBiaoList = productInfoMapper.selectByTypeLimit(YLBConstant.PRODUCT_TYPE_SNABIAO, 0, 3);

        result.setXinShouBao(xinShouList);
        result.setYouXuan(youXuanList);
        result.setSanBiao(sanBiaoList);

        return result;
    }

    // 根据产品类型分页查询产品
    @Override
    public List<ProductInfo> queryByTypeLimit(Integer productType, Integer pageNo, Integer pageSize) {
        List<ProductInfo> productInfos = new ArrayList<>();
        // 验证产品类型
        if (productType == YLBConstant.PRODUCT_TYPE_YOUXUAN ||
                productType == YLBConstant.PRODUCT_TYPE_SNABIAO ||
                productType == YLBConstant.PRODUCT_TYPE_XINSHOUBAO) {
            // 验证分页参数
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1) * pageSize;
            productInfos = productInfoMapper.selectByTypeLimit(productType, offset, pageSize);
        }
        return productInfos;
    }
}
