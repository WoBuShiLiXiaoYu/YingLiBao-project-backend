package com.ylb.work.dataservice.service.impl;

import com.node.api.pojo.BidInfo;
import com.node.api.pojo.FinanceAccount;
import com.node.api.pojo.IncomeRecord;
import com.node.api.pojo.ProductInfo;
import com.node.api.service.IncomeRecordService;
import com.ylb.work.common.constants.YLBConstant;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.dataservice.mapper.BidInfoMapper;
import com.ylb.work.dataservice.mapper.FinanceAccountMapper;
import com.ylb.work.dataservice.mapper.IncomeRecordMapper;
import com.ylb.work.dataservice.mapper.ProductInfoMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

@DubboService(interfaceClass = IncomeRecordService.class, version = "1.0")
public class IncomeRecordServiceImpl implements IncomeRecordService {

    @Resource
    private IncomeRecordMapper incomeRecordMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private BidInfoMapper bidInfoMapper;
    @Resource
    private FinanceAccountMapper accountMapper;

    // 收益返回
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public void generateIncomeBack() {
        // 获取要处理的到期收益记录
        Date date = new Date();
        Date expired = DateUtils.truncate(DateUtils.addDays(date, -1), Calendar.DATE);
        List<IncomeRecord> incomeRecords = incomeRecordMapper.selectExpiredIncome(expired);
        int rows = 0;
        for (IncomeRecord incomeRecord : incomeRecords) {
            // 计算每笔交易的收益：本金 + 利息
            rows = accountMapper.updateAvailableMoneyByIncomeBack(incomeRecord.getBidMoney(),
                    incomeRecord.getIncomeMoney(), incomeRecord.getUid());
            if (rows < 1) {
                throw new RuntimeException("收益返回，更新账户金额失败");
            }
            // 更新收益记录状态
            incomeRecord.setIncomeStatus(YLBConstant.INCOME_STATUS_BACK);
            rows = incomeRecordMapper.updateByPrimaryKey(incomeRecord);
            if (rows < 1) {
                throw new RuntimeException("收益返回，更新收益记录状态失败");
            }
        }

    }

    // 收益计划
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public synchronized void generateIncomePlan() {
        // 获取要处理的理财产品记录
        Date date = new Date();
        Date beginTime = DateUtils.truncate(DateUtils.addDays(date, -1), Calendar.DATE);
        Date endTime = DateUtils.truncate(date, Calendar.DATE);
        List<ProductInfo> productInfos = productInfoMapper.selectFullTimeProducts(beginTime, endTime);

        int rows = 0;
        BigDecimal income = null;   // 收益
        BigDecimal dayRate = null;  // 日利率
        BigDecimal cycle = null;    // 周期
        Date incomeDate = null;     // 到期时间
        // 查询每个理财产品的多条投资记录
        for (ProductInfo productInfo : productInfos) {
            // 计算日利率
            dayRate = productInfo.getRate().divide(new BigDecimal("360"), 10, RoundingMode.HALF_UP)
                    .divide(new BigDecimal("30"), 10, RoundingMode.HALF_UP);
            // 计算周期
            if (productInfo.getProductType() == YLBConstant.PRODUCT_TYPE_XINSHOUBAO) {// 天为单位
                cycle = new BigDecimal(productInfo.getCycle());
                incomeDate = DateUtils.addDays(productInfo.getProductFullTime(), (1 + productInfo.getCycle()));
            } else {// 月为单位
                cycle = new BigDecimal(productInfo.getCycle() * 30);
                incomeDate = DateUtils.addDays(productInfo.getProductFullTime(), (1 + productInfo.getCycle() * 30));
            }
            List<BidInfo> bidInfos = bidInfoMapper.selectBidInfoByProductId(productInfo.getId());
            // 计算每笔投资的利息和到期时间
            for (BidInfo bidInfo : bidInfos) {
                // 利息
                income = bidInfo.getBidMoney().multiply(cycle).multiply(dayRate);

                // 创建收益记录
                IncomeRecord incomeRecord = new IncomeRecord();
                incomeRecord.setUid(bidInfo.getUid());
                incomeRecord.setBidId(bidInfo.getId());
                incomeRecord.setLoanId(productInfo.getId());
                incomeRecord.setBidMoney(bidInfo.getBidMoney());
                incomeRecord.setIncomeMoney(income);
                incomeRecord.setIncomeDate(incomeDate);
                incomeRecord.setIncomeStatus(YLBConstant.INCOME_STATUS_PLAN);
                incomeRecordMapper.insertSelective(incomeRecord);

            }

            // 更新产品状态
            rows = productInfoMapper.updateProductStatus(productInfo.getId(), YLBConstant.PRODUCT_STATUS_PLAN);
            if (rows < 1) {
                throw new RuntimeException("生成收益计划，更新产品状态为 2 失败");
            }
        }






    }

    @Override
    public List<IncomeRecord> queryIncomeRecordByUid(Integer uid, Integer pageNo, Integer pageSize) {
        List<IncomeRecord> list = new ArrayList<>();
        if (uid != null && uid > 0) {
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1) * pageSize;
            list = incomeRecordMapper.selectIncomeRecordByUid(uid, offset, pageSize);
        }
        return list;
    }
}
