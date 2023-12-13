package com.node.api.model;


import java.io.Serializable;
import java.math.BigDecimal;

public class BidInvestInfo implements Serializable {
    private static final long serialVersionUID = 7323033955872361763L;

    private Integer id;
    // 投资人
    private String phone;
    // 投资时间
    private String bidTime;
    // 投资金额
    private BigDecimal bidMoney;

    public BidInvestInfo(Integer id, String phone, String bidTime, BigDecimal bidMoney) {
        this.id = id;
        this.phone = phone;
        this.bidTime = bidTime;
        this.bidMoney = bidMoney;
    }

    public BidInvestInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBidTime() {
        return bidTime;
    }

    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }

    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }
}
