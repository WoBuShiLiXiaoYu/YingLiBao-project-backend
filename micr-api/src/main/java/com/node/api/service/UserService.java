package com.node.api.service;

import com.node.api.model.UserAccountInfo;
import com.node.api.pojo.User;

public interface UserService {

    //  查询投资记录


    // 获取用户信息和资金
    UserAccountInfo queryUserAccountInfoByUid(Integer uid);

    // 更新用户
    boolean modifyRealName(String phone, String name, String idCard);

    // 查询登录用户
    User queryUserLogin(String phone, String password);

    // 注册用户
    int userRegister (String phone, String password);

    // 查询手机号是否存在
    User queryPhoneExists(String phone);

    User queryUserById(Integer uid);

}
