package com.ylb.work.common.constants;

/**
 *  redis 常量类
 */
public class RedisKey {
    // 投资排行榜
    public static final String KEY_INVSET_RANK = "INVSET:RANK";
    // 注册用户
    public static final String KEY_PHONE_EXISTS = "PHONE:EXISTS";
    // 注册验证码
    public static final String KEY_SMS_CODE_REG = "SMS:REG:";
    // 登录验证码
    public static final String KEY_LOGIN_CODE_REG = "LOG:SMS:REG:";
    // 实名验证码
    public static final String KEY_REAL_NAME_CODE_REG = "REAL:SMS:REG:";
    // redis 自增
    public static final String KEY_RECHARGE_ORDER = "RECHARGE:ORDER:SEQ:";
    // orderId
    public static final String KEY_RECHARGE_ORDER_ZSET = "RECHARGE:ORDER:ZSET:";
}
