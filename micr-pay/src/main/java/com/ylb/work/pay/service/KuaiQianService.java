package com.ylb.work.pay.service;

import com.node.api.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

public interface KuaiQianService {
    User queryUser(Integer uid);

    Map<String, String> generateForDate(Integer uid, BigDecimal rechargeMoney, String phone);

    boolean addRecharge(Integer uid, BigDecimal rechargeMoney, String orderId);

    void addOrderIdToRedis(String orderId);

    void kqNotify(HttpServletRequest request);

    void handleQueryOrder();
}
