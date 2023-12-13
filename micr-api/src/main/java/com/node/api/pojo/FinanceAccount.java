package com.node.api.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class FinanceAccount implements Serializable {
    private static final long serialVersionUID = 4866786237811532332L;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_finance_account.id
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_finance_account.uid
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    private Integer uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column u_finance_account.available_money
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    private BigDecimal availableMoney;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_finance_account.id
     *
     * @return the value of u_finance_account.id
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_finance_account.id
     *
     * @param id the value for u_finance_account.id
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_finance_account.uid
     *
     * @return the value of u_finance_account.uid
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_finance_account.uid
     *
     * @param uid the value for u_finance_account.uid
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column u_finance_account.available_money
     *
     * @return the value of u_finance_account.available_money
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    public BigDecimal getAvailableMoney() {
        return availableMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column u_finance_account.available_money
     *
     * @param availableMoney the value for u_finance_account.available_money
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    public void setAvailableMoney(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }
}