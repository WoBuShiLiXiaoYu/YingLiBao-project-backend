package com.ylb.work.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ylb.work.common.constants.RedisKey;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 *  公共工具类
 */
public class CommonUtil {
    // 私有化构造方法
    private CommonUtil() {}

    // 判断 BigDecimal 大小
    public static boolean ge(BigDecimal n1, BigDecimal n2) {
        if (n1 == null || n2 == null) {
           throw new RuntimeException("参数 BiDecimal 为 null");
        }
        return n1.compareTo(n2) >= 0;
    }

    // 解析短信验证返回数据
    public static boolean analysisJSON(String text) {
        // 解析 json
        if (StringUtils.isNotBlank(text)) {// 判断不为空
            JSONObject jsonObject = JSONObject.parseObject(text);
            if ("Success".equals(jsonObject.getString("ReturnStatus"))) {// 第三方接口调用成功
                if (jsonObject.getInteger("SuccessCounts") == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean analysisJSONList(String text) {
        // 解析 json
        if (StringUtils.isNoneBlank(text)) {// 判断不为空
            JSONObject jsonObject = JSONObject.parseObject(text);
            if ("0".equals(jsonObject.getString("status"))) {// 第三方接口调用成功
                // 读取 list 中 "result" 的值
                if (jsonObject.getJSONArray("list")
                        .getJSONObject(0).getInteger("result") == 0) {
                    // 短信发送成功
                   return true;
                }
            }
        }
        return false;
    }

    // 拼接短信验证 Get 方式的 url
    public static String pieceUrl(String url, String content, String phone) {
        url = url + "?content=" + content + "&mobile=" + phone;
        return url;
    }

    // 手机号格式
    public static boolean checkPhone(String phone) {
        boolean flag = false;
        // 验证手机号格式
        if (phone != null && phone.length() == 11) {
            // 正则表达式
            flag = Pattern.matches("^1[1-9]\\d{9}$", phone);
        }
        return flag;
    }

    // 手机号脱敏
    public static String tuoMinOfPhone(String phone) {
        String result = "***********";
        // 验证手机号
        if (phone != null && (phone.trim().length() == 11)) {
            // 将手机号 4-9 位替换
            result = phone.substring(0, 3) + "******" + phone.substring(9, 11);
        }
        return result;
    }

    // 验证分页参数
    public static int defaultPageNo(Integer pageNo) {
        int pNo = pageNo;
        if (pageNo == null || pageNo < 1) {
            pNo = 1;
        }
        return pNo;
    }

    public static int defaultPageSize(Integer pageSize) {
        int pSize = pageSize;
        if (pageSize == null || pageSize < 1) {
            pSize = 1;
        }
        return pSize;
    }
}
