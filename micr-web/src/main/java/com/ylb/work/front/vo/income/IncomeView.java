package com.ylb.work.front.vo.income;

import com.node.api.pojo.IncomeRecord;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;

public class IncomeView {
    private Integer id;
    private String result = "未知";
    private String rechargeDate = "-";
    private BigDecimal rechargeMoney;

    public IncomeView(IncomeRecord incomeRecord) {
        this.id = incomeRecord.getId();
        this.rechargeMoney = incomeRecord.getIncomeMoney();
        if (incomeRecord.getIncomeDate() != null) {
            this.rechargeDate = DateFormatUtils.format(incomeRecord.getIncomeDate(), "yyyy-MM-dd");
        }
        switch (incomeRecord.getIncomeStatus()) {
            case 0:
                this.result = "投标中";
                break;
            case 1:
                this.result = "投标成功";
                break;
            case 2:
                this.result = "投标失败";
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
