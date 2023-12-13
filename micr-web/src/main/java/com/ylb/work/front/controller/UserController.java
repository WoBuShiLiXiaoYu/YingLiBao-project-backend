package com.ylb.work.front.controller;

import com.node.api.model.UserAccountInfo;
import com.node.api.pojo.User;
import com.ylb.work.common.constants.RedisKey;
import com.ylb.work.common.enums.ResCodeMsg;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.common.utils.JWTUtil;
import com.ylb.work.front.service.SmsService;
import com.ylb.work.front.service.RealNameService;
import com.ylb.work.front.service.impl.SmsCodeRealNameImpl;
import com.ylb.work.front.vo.RespResult;
import com.ylb.work.front.vo.view.RealNameView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户功能")
@RequestMapping("/v1/user")
@RestController
public class UserController extends BaseController{

    @Resource(name = "smsCodeRegisterImpl")
    private SmsService smsService;
    @Resource(name = "smsCodeLoginImpl")
    private SmsService loginService;
    @Resource(name = "realNameImpl")
    private RealNameService nameService;
    @Resource(name = "")
    private SmsCodeRealNameImpl codeRealNameService;
    @Resource
    private JWTUtil jwtUtil;


    @ApiOperation(value = "用户中心", notes = "用户基本信息和资金")
    @GetMapping("/usercenter")
    public RespResult userCenter(@RequestHeader("uid") Integer uid) {
        RespResult result = RespResult.fail();
        // 验证参数
        if (uid != null && uid > 0) {
            UserAccountInfo userAccountInfo = userService.queryUserAccountInfoByUid(uid);
            if (userAccountInfo != null) {
                result = RespResult.ok();
                // 封装信息
                Map<String, Object> data = new HashMap<>();
                data.put("name", userAccountInfo.getName());
                data.put("phone", userAccountInfo.getPhone());
                data.put("headerUrl", userAccountInfo.getHeaderImage());
                data.put("money", userAccountInfo.getAvailableMoney());
                if (userAccountInfo.getLastLoginTime() != null) {
                    data.put("loginTime", DateFormatUtils.format(
                            userAccountInfo.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss"));
                } else {
                    data.put("loginTime", "-");
                }
                result.setData(data);
            }
        }

        return result;
    }

    @ApiOperation(value = "实名验证", notes = "提供手机号、姓名、身份证号、验证码进行实名验证")
    @PostMapping("/realname")
    public RespResult userRealName(@RequestBody RealNameView realName) {
        RespResult result = RespResult.fail();

        // 验证参数
        if (CommonUtil.checkPhone(realName.getPhone())
                && (StringUtils.isNoneBlank(realName.getName())
                && StringUtils.isNoneBlank(realName.getIdCard()))) {

            // 查询用户是否实名验证过
            User user = userService.queryPhoneExists(realName.getPhone());
            if (user != null) {
                if (!StringUtils.isNoneBlank(user.getName())) {
                    if (codeRealNameService.checkSmsCode(realName.getPhone(), realName.getCode())) {
                        // 第三方接口验证成功并且更新了数据
                        boolean realNameResult = nameService.handleRealName(realName.getPhone(), realName.getName(), realName.getIdCard());
                        if (realNameResult) {
                            result = RespResult.ok();
                        } else {
                            result.setResCodeMsg(ResCodeMsg.USER_REAL_NAME_ERR);
                        }
                    } else {
                        result.setResCodeMsg(ResCodeMsg.SMS_CODE_CANNOT_USE);
                    }
                } else {
                    result.setResCodeMsg(ResCodeMsg.USER_REAL_NAME_RETRY);
                }
            }  else {
                result.setResCodeMsg(ResCodeMsg.USER_NO_EXISTS);
            }
        } else {
            result.setResCodeMsg(ResCodeMsg.REQUEST_PARAM_ERR);
        }

        return result;

    }

    @ApiOperation(value = "手机号登录-获取 Token")
    @PostMapping("/login")
    public RespResult userLogin(@RequestParam String phone,
                                @RequestParam String pword,
                                @RequestParam String scode) throws Exception {
        // 创建响应对象
        RespResult result = RespResult.fail();
        // 验证参数
        if (CommonUtil.checkPhone(phone) && (pword != null && pword.length() == 32)) {
            if (loginService.checkSmsCode(phone, scode)) {
                //查询用户是否存在
                User user = userService.queryUserLogin(phone, pword);
                if (user != null) {
                    // 生成 Token
                    Map<String, Object> data = new HashMap<>();
                    data.put("uid", user.getId());
                    String jwtToken = jwtUtil.createJWT(data, 120);
                    result = RespResult.ok();
                    result.setAccessToken(jwtToken);
                    // 需要响应的数据
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("uid", user.getId());
                    userInfo.put("phone", user.getPhone());
                    userInfo.put("name", user.getName());
                    result.setData(userInfo);
                } else {
                    result.setResCodeMsg(ResCodeMsg.USER_NO_EXISTS);
                }
            } else {
                result.setResCodeMsg(ResCodeMsg.SMS_CODE_CANNOT_USE);
            }

        } else {
            result.setResCodeMsg(ResCodeMsg.REQUEST_PARAM_ERR);
        }

        return result;
    }

    @ApiOperation(value = "手机号注册用户")
    @PostMapping("/register")
    public RespResult userRegister(@RequestParam String phone,
                                   @RequestParam String pword,
                                   @RequestParam String scode) {

        RespResult result = RespResult.fail();
        // 验证手机号
        if (CommonUtil.checkPhone(phone)) {
            // 验证密码
            if (pword != null && pword.length() == 32) {
                // 检查验证码
                if (smsService.checkSmsCode(phone,scode)) {
                    // 可以注册
                    int registerResult = userService.userRegister(phone, pword);
                    if (registerResult == 1) {
                        result = RespResult.ok();
                    } else if (registerResult == 2) {
                        result.setResCodeMsg(ResCodeMsg.PHONE_EXISTS_ERR);
                    } else {
                        result.setResCodeMsg(ResCodeMsg.REQUEST_PARAM_ERR);
                    }
                } else {
                    // 验证码无效
                    result.setResCodeMsg(ResCodeMsg.SMS_CODE_CANNOT_USE);
                }
            } else {
                result.setResCodeMsg(ResCodeMsg.REQUEST_PARAM_ERR);
            }
        } else {
            result.setResCodeMsg(ResCodeMsg.PHONE_FORMAT_ERR);
        }
        return result;
    }

    @ApiOperation(value = "手机号是否注册", notes = "在注册功能中判断手机号是否已注册")
    @GetMapping("/phone/exists")
    public RespResult phoneExists (@RequestParam("phone") String phone) {
        // 创建响应对象
        RespResult result = new RespResult();
        User user = new User();
        result.setResCodeMsg(ResCodeMsg.PHONE_EXISTS_ERR);
        // 验证手机号是否合法
        if (CommonUtil.checkPhone(phone)) {

            String key = RedisKey.KEY_PHONE_EXISTS + phone;
            // 从 redis 中读取数据，如果存在则提醒用户手机号已注册，不存在查询数据库后缓存到 redis 中
            Boolean hasKey = stringRedisTemplate.hasKey(key);
            // 判断缓存中是否有该手机号
            if (!hasKey) {
                // 查询手机号是否存在
                user = userService.queryPhoneExists(phone);
                if (user == null) {
                    // 用户为空，可以注册
                    result = RespResult.ok();
                } else {
                    // 手机号存在，缓存到 redis 中
                    stringRedisTemplate.opsForValue().set(key, user.getPhone());
                }
            }
        } else {
            result.setResCodeMsg(ResCodeMsg.PHONE_FORMAT_ERR);
        }

        return result;
    }
}
