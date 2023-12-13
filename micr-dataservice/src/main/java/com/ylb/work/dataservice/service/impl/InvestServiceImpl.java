package com.ylb.work.dataservice.service.impl;

import com.node.api.model.BidInvestInfo;
import com.node.api.pojo.BidInfo;
import com.node.api.pojo.FinanceAccount;
import com.node.api.pojo.ProductInfo;
import com.node.api.service.InvestService;
import com.ylb.work.common.constants.YLBConstant;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.dataservice.mapper.BidInfoMapper;
import com.ylb.work.dataservice.mapper.FinanceAccountMapper;
import com.ylb.work.dataservice.mapper.ProductInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 暴露接口
@DubboService(interfaceClass = InvestService.class, version = "1.0")
public class InvestServiceImpl implements InvestService {
    // 定义数据层对象
    @Resource
    private BidInfoMapper bidInfoMapper;
    @Resource
    private FinanceAccountMapper accountMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;


    /**
     * 投资理财产品
     * @param uid
     * @param productId
     * @param money
     * @return 1 投资成功、2 账户不存在、3 /资金不足、4 产品不存在、0 参数不正确
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public int investProduct(Integer uid, Integer productId, BigDecimal money) {
        int result = 0;
        int rows = 0;
        // 验证参数
        if ((uid != null && uid >= 0)
                && (productId != null && productId >= 0)
                && (money != null && money.intValue() % 100 == 0)) {
            // 查询账户金额，检查账户是否符合购买要求
            FinanceAccount account = accountMapper.selectByUidForUpdate(uid);
            if (account != null) {
                if (CommonUtil.ge(account.getAvailableMoney(), money)) {// 账户金额应大于购买金额
                    // 资金满足购买要求
                    // 检查产品是否符合购买要求
                    ProductInfo productInfo = productInfoMapper.selectProductInfoById(productId);
                    if (productInfo != null && productInfo.getProductStatus() == YLBConstant.PRODUCT_STATUS_SELLING) {
                        // 产品剩余购买金额应大于购买金额，产品最小购买金额 <= 购买金额 <= 产品最大购买金额
                        if (CommonUtil.ge(productInfo.getLeftProductMoney(), money)
                                && CommonUtil.ge(money, productInfo.getBidMinLimit())
                                && CommonUtil.ge(productInfo.getBidMaxLimit(), money)) {
                            // 可以购买
                            //  更新账户金额
                            rows = accountMapper.updateAvailableMoneyByInvest(uid, money);
                            if (rows < 1) {
                                throw new RuntimeException("更新账户金额失败");
                            }
                            // 更新产品剩余购买金额
                            rows = productInfoMapper.updateLeftProductMoney(productId, money);
                            if (rows < 1) {
                                throw new RuntimeException("更新产品剩余金额失败");
                            }
                            // 创建产品投资记录
                            BidInfo bidInfo = new BidInfo();
                            bidInfo.setBidMoney(money);
                            bidInfo.setUid(uid);
                            bidInfo.setBidTime(new Date());
                            bidInfo.setLoanId(productId);
                            bidInfo.setBidStatus(YLBConstant.INVEST_STATUS_SUCC);
                            bidInfoMapper.insertSelective(bidInfo);

                            // 判断产品是否卖完，更新产品状态
                            ProductInfo dbProductInfo = productInfoMapper.selectProductInfoById(productId);
                            if (dbProductInfo.getLeftProductMoney().compareTo(new BigDecimal("0")) == 0) {
                                rows = productInfoMapper.updateSelled(productId);
                                if (rows < 1) {
                                    throw new RuntimeException("更新产品满标状态失败");
                                }
                            }
                            // 投资成功
                            result = 1;
                        }
                    } else {
                        // 产品不存在
                        result = 4;
                    }
                } else {
                    // 资金不足
                    result = 3;
                }
            } else {
                // 账户不存在
                result = 2;
            }
        }
        return result;
    }

    @Override
    public List<BidInfo> queryBidInvertInfoByUid(Integer uid, Integer pageNo, Integer pageSize) {
        List<BidInfo> list = new ArrayList<>();
        // 验证参数
        if (uid != null && uid > 0) {
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1) * pageSize;
            list = bidInfoMapper.selectBidInvestInfoByUid(uid, offset, pageSize);
        }
        return list;

    }

    @Override
    public List<BidInvestInfo> queryBidInvestInfoById(Integer productId, Integer pageNo, Integer pageSize) {
        List<BidInvestInfo> list = new ArrayList<>();
        // 验证产品 id 是否合法
        if (productId != null && productId > 0) {
            // 处理页数和每页显示条数
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1) * pageSize;
           list = bidInfoMapper.selectBidInvestInfoById(productId, offset, pageSize);
        }
        return list;
    }

}
