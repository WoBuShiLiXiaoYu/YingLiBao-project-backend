package com.ylb.work.dataservice.mapper;

import com.node.api.pojo.FinanceAccount;
import com.node.api.pojo.IncomeRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface FinanceAccountMapper {

    int updateAvailableMoneyByRecharge(@Param("uid") Integer uid, @Param("rechargeMoney") BigDecimal rechargeMoney);

    int updateAvailableMoneyByIncomeBack(@Param("bidMoney") BigDecimal bidMoney,
                                        @Param("incomeMoney") BigDecimal incomeMoney,
                                        @Param("uid") Integer uid);

    // 更新投资金额
    int updateAvailableMoneyByInvest(@Param("uid") Integer uid, @Param("money") BigDecimal money);

    // 查询账户金额，加锁
    FinanceAccount selectByUidForUpdate(@Param("uid") Integer uid);

    // 添加金融账号
    int insertAccount (@Param("account") FinanceAccount account);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_finance_account
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_finance_account
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int insert(FinanceAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_finance_account
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int insertSelective(FinanceAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_finance_account
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    FinanceAccount selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_finance_account
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int updateByPrimaryKeySelective(FinanceAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_finance_account
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int updateByPrimaryKey(FinanceAccount record);



}