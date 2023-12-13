package com.node.api.service;

import com.node.api.model.BidInvestInfo;
import com.node.api.pojo.BidInfo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InvestService {

    // 根据用户 id  分页查询投资
    List<BidInfo> queryBidInvertInfoByUid(Integer uid, Integer pageNo, Integer pageSize);

    // 根据产品 id 分页查询投资详细信息
    List<BidInvestInfo> queryBidInvestInfoById(Integer productId, Integer pageNo, Integer pageSize);

    // 投资理财产品
    int investProduct(Integer uid, Integer productId, BigDecimal money);
}
