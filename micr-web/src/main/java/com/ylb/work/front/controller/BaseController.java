package com.ylb.work.front.controller;

import com.node.api.pojo.User;
import com.node.api.service.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * 声明公共的方法、属性等
 */
public class BaseController {

    // redis 对象
    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    // service 对象
    // 平台信息服务
    @DubboReference(interfaceClass = PlatBaseInfoService.class, version = "1.0")
    protected PlatBaseInfoService platBaseInfoService;

    // 产品服务
    @DubboReference(interfaceClass = ProductService.class, version = "1.0")
    protected ProductService productService;

    // 投资服务
    @DubboReference(interfaceClass = InvestService.class, version = "1.0")
    protected InvestService investService;

    // 用户服务
    @DubboReference(interfaceClass = UserService.class, version = "1.0")
    protected UserService userService;

    // 充值服务
    @DubboReference(interfaceClass = RechargeService.class, version = "1.0")
    protected RechargeService rechargeService;

    // 收益服务
    @DubboReference(interfaceClass = IncomeRecordService.class, version = "1.0")
    protected IncomeRecordService incomeRecordService;

}
