package com.ylb.work.dataservice.service.impl;

import com.node.api.model.UserAccountInfo;
import com.node.api.pojo.FinanceAccount;
import com.node.api.pojo.User;
import com.node.api.service.UserService;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.dataservice.mapper.FinanceAccountMapper;
import com.ylb.work.dataservice.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@DubboService(interfaceClass = UserService.class, version = "1.0")
public class UserServiceImpl implements UserService {

    // 定义数据层对象
    @Resource
    private UserMapper userMapper;
    @Resource
    private FinanceAccountMapper financeAccountMapper;
    // 配置对象
    @Value("${ylb.config.password-salt}")
    private String passwordSalt;

    // 查询用户信息和资金
    @Override
    public UserAccountInfo queryUserAccountInfoByUid(Integer uid) {
        UserAccountInfo userAccountInfo = null;
        // 验证参数
        if (uid != null && uid > 0) {
            userAccountInfo = userMapper.selectUserAccountInfoByUid(uid);
        }
        return userAccountInfo;
    }

    // 更新用户
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean modifyRealName(String phone, String name, String idCard) {
        int rows = 0;
        // 验证参数
        if (!StringUtils.isAllBlank(phone, name, idCard)) {
            rows = userMapper.updateRealName(phone, name, idCard);
        }
        return rows > 0;
    }

    // 用户登录
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public User queryUserLogin(String phone, String password) {
        User user = null;
        // 验证参数
        if (CommonUtil.checkPhone(phone) && (password != null && password.length() == 32)) {
            // 密码加盐
            String newPassword = DigestUtils.md5Hex(password + passwordSalt);
            user = userMapper.selectUserLogin(phone, newPassword);

            if (user != null) {
                // 更新最后登录时间
                user.setLastLoginTime(new Date());
                userMapper.updateByLastLoginTimeUser(user);
            }
        }
        return user;
    }

    // 用户注册
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    // 防止多个请求同时进入对同一个参数进行修改
    public synchronized int userRegister(String phone, String password) {
        int result = 0;
        // 验证手机号、密码
        if (CommonUtil.checkPhone(phone) && password != null && password.length() == 32) {
            // 查询手机号是否存在
            if (userMapper.selectUserByPhone(phone) == null) {// 为空可以注册
                // 注册密码的 MD5 加密，原始密码加盐
                String newPassword = DigestUtils.md5Hex(password + passwordSalt);
                // 注册用户
                User user = new User();
                user.setPhone(phone);
                user.setLoginPassword(newPassword);
                user.setAddTime(new Date());
                userMapper.insertUserReturnPrimaryKey(user);

                FinanceAccount account = new FinanceAccount();
                account.setUid(user.getId());
                account.setAvailableMoney(new BigDecimal("0"));
                financeAccountMapper.insertAccount(account);

                // 注册成功
                result = 1;
            } else {
                // 手机号存在
                result = 2;
            }
        }
        return result;
    }

    // 查询手机号
    @Override
    public User queryPhoneExists(String phone) {
        User user = null;
        // 验证手机号是否合法
        if (CommonUtil.checkPhone(phone)) {
            user = userMapper.selectUserByPhone(phone);
        }
        return user;
    }

    @Override
    public User queryUserById(Integer uid) {
        User user = null;
        if (uid != null && uid > 0) {
            user = userMapper.selectByPrimaryKey(uid);
        }
        return user;
    }
}
