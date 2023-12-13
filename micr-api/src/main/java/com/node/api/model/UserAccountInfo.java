package com.node.api.model;

import com.node.api.pojo.User;

import java.math.BigDecimal;

public class UserAccountInfo extends User {
    private BigDecimal availableMoney;

    public UserAccountInfo() {
    }

    public UserAccountInfo(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }

    public BigDecimal getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }
}
