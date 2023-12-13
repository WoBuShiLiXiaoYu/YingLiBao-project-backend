package com.ylb.work.common.constants;

/**
 *  常量类
 */
public class YLBConstant {
    // 产品类型
    // 新手宝
    public static final int PRODUCT_TYPE_XINSHOUBAO = 0;
    // 优选
    public static final int PRODUCT_TYPE_YOUXUAN = 1;
    // 散标
    public static final int PRODUCT_TYPE_SNABIAO = 2;

    // 产品状态
    // 为满标
    public static final int PRODUCT_STATUS_SELLING = 0;
    // 已满标
    public static final int PRODUCT_STATUS_SELLED = 1;
    // 投资计划
    public static final int PRODUCT_STATUS_PLAN = 2;

    // 产品投资状态
    // 投资成功
    public static final int INVEST_STATUS_SUCC = 1;
    // 投资失败
    public static final int INVEST_STATUS_FAIL = 2;

    // 收益状态
    // 生成收益计划
    public static final int INCOME_STATUS_PLAN = 0;
    // 收益返回
    public static final int INCOME_STATUS_BACK = 1;

    // 充值状态
    // 充值中
    public static final int RECHARGE_STATUS_ING = 0;
    // 充值成功
    public static final int RECHARGE_STATUS_SUCCESS = 1;
    // 充值失败
    public static final int RECHARGE_STATUS_FAIL = 2;


    // 应答码
    // 成功
    public static final int RESPONSE_CODE_OK = 1000;
    // 请求参数错误
    public static final int REQUEST_PARAM_ERR = 1001;
    // 产品信息有误
    public static final int PRODUCT_INFO_ERR = 1002;
    // 手机号格式有误
    public static final int PHONE_FORMAT_ERR = 1003;
    // 手机号已注册
    public static final int PHONE_EXISTS_ERR = 1003;
    // 验证码可以继续使用
    public static final int SMS_CODE_CAN_USE = 1004;
    // 验证码无法继续使用
    public static final int SMS_CODE_CANNOT_USE = 1005;
    // 用户不存在
    public static final int USER_NO_EXISTS = 1006;
    // 实名认证失败
    public static final int USER_REAL_NAME_ERR = 1100;
    // 已经实名验证过
    public static final int USER_REAL_NAME_RETRY = 1200;
    // token 验证失败
    public static final int TOKEN_INVALID = 3000;
    // 失败
    public static final int RESPONSE_CODE_FAIL = 2000;
    // 默认
    public static final int RESPONSE_CODE_WARN = 0;

    // 应答信息
    public static final String RESPONSE_MSG_OK = "请求成功";
    public static final String RESPONSE_MSG_FAIL = "请求失败";
    public static final String RESPONSE_MSG_UNKOWN = "稍后重试";
    public static final String RESPONSE_MSG_PARAM = "参数有误";
    public static final String PRODUCT_MSG_DOWN_ERR = "产品下线";
    public static final String PHONE_MSG_FORMAT_ERR = "手机号格式有误";
    public static final String PHONE_MSG_EXISTS_ERR = "手机号已注册";
    public static final String SMS_MSG_CODE_CAN_USE = "验证码可继续使用";
    public static final String SMS_MSG_CODE_CANNOT_USE = "验证码无法使用";
    public static final String USER_NO_EXISTS_MSG = "登录用户不存在";
    public static final String USER_REAL_NAME_ERR_MSG = "实名认证失败";
    public static final String USER_REAL_NAME_RETRY_MSG = "用户已经实名认证过";
    public static final String TOKEN_INVALID_MSG = "token 验证失败";

}
