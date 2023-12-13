package com.ylb.work.dataservice.service.impl;

import com.node.api.pojo.RechargeRecord;
import com.node.api.service.RechargeService;
import com.ylb.work.common.constants.YLBConstant;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.dataservice.mapper.FinanceAccountMapper;
import com.ylb.work.dataservice.mapper.RechargeRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@DubboService(interfaceClass = RechargeService.class, version = "1.0")
public class RechargeServiceImpl implements RechargeService {

    @Resource
    private RechargeRecordMapper recordMapper;
    @Resource
    private FinanceAccountMapper accountMapper;

    @Override
    public int addRechargeRecord(RechargeRecord record) {

        return recordMapper.insertSelective(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int handleKQNotify(String orderId, String payAmount, String payResult) {
        int result = 0;
        int rows = 0;
        // 查询订单
        RechargeRecord record = recordMapper.selectByRechargeNo(orderId);
        if (record != null) {
            if (record.getRechargeStatus() == YLBConstant.RECHARGE_STATUS_ING) {
                // 判断金额
                String fen = record.getRechargeMoney().multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString();
                if (fen.equals(payAmount)) {
                    // 金额一致
                    if ("10".equals(payResult)) {
                        // 充值成功
                        // 更新账户金额
                        rows = accountMapper.updateAvailableMoneyByRecharge(record.getUid(), record.getRechargeMoney());
                        if (rows < 1) {
                            throw new RuntimeException("充值，更新资金账户失败");
                        }
                        rows = recordMapper.updateStatus(record.getId(), YLBConstant.RECHARGE_STATUS_SUCCESS);
                        if (rows < 1) {
                            throw new RuntimeException("充值，更新充值记录状态失败");
                        }
                        result = 1;
                    } else {
                        rows = recordMapper.updateStatus(record.getId(), YLBConstant.RECHARGE_STATUS_FAIL);
                        if (rows < 1) {
                            throw new RuntimeException("充值，更新充值记录状态失败");
                        }
                        result = 2;// 更新充值记录结果失败
                    }
                } else {
                    result = 4;// 金额不一致
                }
            } else {
                result = 3;// 订单已处理
            }
        }

        return result;
    }

    @Override
    public List<RechargeRecord> queryRechargeRecordByUid(Integer uid, Integer pageNo, Integer pageSize) {
        List<RechargeRecord> records = new ArrayList<>();
        // 验证参数
        if (uid != null && uid > 0) {
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1) * pageSize;
            records = recordMapper.selectRechargeRecordByUid(uid, offset, pageSize);
        }
        return records;
    }
}
