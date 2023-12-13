package com.ylb.work.front.vo.recharge;

import com.node.api.pojo.RechargeRecord;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;

public class ResultView {
    private Integer id;
    private String result = "未知";
    private String rechargeDate = "-";
    private BigDecimal rechargeMoney;

    // 使用构造方法传入对象进行赋值
    public ResultView(RechargeRecord record) {
        this.id = record.getId();
        this.rechargeMoney = record.getRechargeMoney();
        // 处理充值日期
        if (record.getRechargeTime() != null) {
            this.rechargeDate = DateFormatUtils.format(record.getRechargeTime(), "yyyy-MM-dd");
        }
        // 处理充值结果
        switch (record.getRechargeStatus()) {
            case 0:
                this.result = "充值中";
                break;
            case 1:
                this.result = "充值成功";
                break;
            case 2:
                this.result = "充值失败";
                break;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public String getRechargeDate() {
        return rechargeDate;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }
}
