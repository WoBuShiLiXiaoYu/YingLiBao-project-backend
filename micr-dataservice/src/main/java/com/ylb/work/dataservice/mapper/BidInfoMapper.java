package com.ylb.work.dataservice.mapper;

import com.node.api.model.BidInvestInfo;
import com.node.api.pojo.BidInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BidInfoMapper {

    List<BidInfo> selectBidInfoByProductId(@Param("productId") Integer productId);

    List<BidInfo> selectBidInvestInfoByUid(@Param("uid") Integer uid,
                                                 @Param("offset") int offset,
                                                 @Param("rows") Integer pageSize);

    // 根据产品 id 查询投资详细信息
    List<BidInvestInfo> selectBidInvestInfoById(@Param("productId") Integer productId,
                                                @Param("offset") Integer offset,
                                                @Param("rows") Integer pageSize);

    // 累计成交金额
    BigDecimal selectSumBidMoney();






    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int insert(BidInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int insertSelective(BidInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    BidInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int updateByPrimaryKeySelective(BidInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int updateByPrimaryKey(BidInfo record);



}