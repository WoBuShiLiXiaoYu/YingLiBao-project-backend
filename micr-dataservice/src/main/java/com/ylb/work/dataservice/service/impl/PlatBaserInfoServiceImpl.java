package com.ylb.work.dataservice.service.impl;

import com.node.api.model.BaseInfo;
import com.node.api.service.PlatBaseInfoService;
import com.ylb.work.dataservice.mapper.BidInfoMapper;
import com.ylb.work.dataservice.mapper.ProductInfoMapper;
import com.ylb.work.dataservice.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.math.BigDecimal;

@DubboService(interfaceClass = PlatBaseInfoService.class, version = "1.0")
public class PlatBaserInfoServiceImpl implements PlatBaseInfoService {

    @Resource
    private ProductInfoMapper productInfoMapper;

    @Resource
    private BidInfoMapper bidInfoMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    // 查询平台信息
    public BaseInfo queryPlatBaseInfo() {
        // 获取注册人数，累计成交金额，收益率平均值
        int registerUser = userMapper.selectCountUsers();

        BigDecimal sumBidMoney = bidInfoMapper.selectSumBidMoney();

        BigDecimal historyAvgRate = productInfoMapper.selectAvgRate();

        BaseInfo baseInfo = new BaseInfo(historyAvgRate, sumBidMoney, registerUser);

        return baseInfo;
    }
}
