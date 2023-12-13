package com.node.api.service;

import com.node.api.pojo.RechargeRecord;

import java.util.List;

public interface RechargeService {
    // 根据用户 id 查询充值记录，分页
    List<RechargeRecord> queryRechargeRecordByUid(Integer uid, Integer pageNo, Integer pageSize);

    int addRechargeRecord(RechargeRecord record);

    int handleKQNotify(String orderId, String payAmount, String payResult);
}
