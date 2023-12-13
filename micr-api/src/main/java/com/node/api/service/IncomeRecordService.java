package com.node.api.service;

import com.node.api.pojo.IncomeRecord;

import java.util.List;

public interface IncomeRecordService {

    // 收益返回
    void generateIncomeBack();

    // 收益计划
    void generateIncomePlan();

    // 根据用户 id 分页查询收益
    List<IncomeRecord> queryIncomeRecordByUid(Integer uid, Integer pageNo, Integer pageSize);
}
