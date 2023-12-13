package com.ylb.work.front.vo.invest;

/**
 * 存储投资排行榜数据
 */
public class RankVO {
    // 投资金额
    private Double money;
    // 手机号
    private String phone;

    public RankVO(Double money, String phone) {
        this.money = money;
        this.phone = phone;
    }

    public RankVO() {
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
