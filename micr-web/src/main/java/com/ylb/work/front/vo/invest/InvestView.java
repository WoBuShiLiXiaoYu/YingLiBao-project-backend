package com.ylb.work.front.vo.invest;

import com.node.api.model.BidInvestInfo;
import com.node.api.pojo.BidInfo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;

public class InvestView {
    private Integer id;
    private String result = "未知";
    private String investDate = "-";
    private BigDecimal investMoney;

    public InvestView(BidInfo investInfo) {
        this.id = investInfo.getId();
        this.investMoney = investInfo.getBidMoney();
        if (investInfo.getBidTime() != null) {
            this.investDate = DateFormatUtils.format(investInfo.getBidTime(), "yyyy-MM-dd");
        }
        switch (investInfo.getBidStatus()) {
            case 0:
                result = "投资中";
                break;
            case 1:
                result = "投资成功";
                break;
            case 2:
                result = "投资失败";
                break;
        }

    }

    public Integer getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public String getInvestDate() {
        return investDate;
    }

    public BigDecimal getInvestMoney() {
        return investMoney;
    }
}
