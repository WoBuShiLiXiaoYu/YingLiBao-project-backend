package com.ylb.work.dataservice.mapper;

import com.node.api.model.UserAccountInfo;
import com.node.api.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    UserAccountInfo selectUserAccountInfoByUid(@Param("uid") Integer uid);

    int updateRealName(@Param("phone") String phone, @Param("name") String name, @Param("idCard") String idCard);

    // 更新最后登录时间
    int updateByLastLoginTimeUser(@Param("user") User user);

    // 查询登录用户
    User selectUserLogin(@Param("phone") String phone, @Param("loginPassword") String newPassword);

    // 注册用户并返回主键值
    int insertUserReturnPrimaryKey(@Param("user") User user);

    // 根据手机号查询信息
    User selectUserByPhone(@Param("phone") String phone);

    // 统计注册人数
    int selectCountUsers();







    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_user
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_user
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_user
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_user
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_user
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table u_user
     *
     * @mbg.generated Wed Jul 05 06:42:36 CST 2023
     */
    int updateByPrimaryKey(User record);



}