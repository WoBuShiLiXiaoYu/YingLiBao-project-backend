package com.node.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaseInfo implements Serializable {
    private static final long serialVersionUID = -7227967349596603449L;

    // 收益率平均值
    private BigDecimal historyAvgRate;

    // 累计成交金额
    private BigDecimal sumBidMoney;

    // 注册人数
    private Integer registerUser;

    public BaseInfo(BigDecimal historyAvgRate, BigDecimal sumBidMoney, Integer registerUser) {
        this.historyAvgRate = historyAvgRate;
        this.sumBidMoney = sumBidMoney;
        this.registerUser = registerUser;
    }

    public BaseInfo() {
    }

    public BigDecimal getHistoryAvgRate() {
        return historyAvgRate;
    }

    public void setHistoryAvgRate(BigDecimal historyAvgRate) {
        this.historyAvgRate = historyAvgRate;
    }

    public BigDecimal getSumBidMoney() {
        return sumBidMoney;
    }

    public void setSumBidMoney(BigDecimal sumBidMoney) {
        this.sumBidMoney = sumBidMoney;
    }

    public Integer getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(Integer registerUser) {
        this.registerUser = registerUser;
    }
}
