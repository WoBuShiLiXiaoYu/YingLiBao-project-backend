package com.ylb.work.front.controller;

import com.ylb.work.common.constants.RedisKey;
import com.ylb.work.common.enums.ResCodeMsg;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.front.service.RealNameService;
import com.ylb.work.front.service.SmsService;
import com.ylb.work.front.vo.RespResult;
import com.ylb.work.front.vo.view.RealNameView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@Api(tags = "短信验证功能")
@RequestMapping("/v1/sms")
@RestController
public class SmsController extends BaseController{

    @Resource(name = "smsCodeRealNameImpl")
    private SmsService realNameService;

    @Resource(name = "smsCodeRegisterImpl")
    private SmsService smsService;

    @Resource(name = "smsCodeLoginImpl")
    private SmsService loginService;


    @ApiOperation(value = "发送实名验证码短信", notes = "发送实名验证码短信，并缓存至 redis 设置有效期限")
    @PostMapping("/code/realname")
    public RespResult sendCodeRealName(@RequestParam String phone) {


        RespResult result = RespResult.fail();
        // 验证手机号
        if (CommonUtil.checkPhone(phone)) {
            // 判断 redis 是否缓存验证码
            String key = RedisKey.KEY_REAL_NAME_CODE_REG + phone;
            if (stringRedisTemplate.hasKey(key)) {
                result = RespResult.ok();
                result.setResCodeMsg(ResCodeMsg.SMS_CODE_CAN_USE);
            } else {
                boolean isSuccess = realNameService.sendSms(phone);
                if (isSuccess) {
                    result = RespResult.ok();
                }
            }

        } else {
            result.setResCodeMsg(ResCodeMsg.PHONE_FORMAT_ERR);
        }

        return result;
    }


    @ApiOperation(value = "发送登录验证码短信", notes = "发送登录验证码短信，并缓存至 redis 设置有效期限")
    @PostMapping("/code/login")
    public RespResult sendCodeLogin(@RequestParam String phone) {

        RespResult result = RespResult.fail();
        // 验证手机号
        if (CommonUtil.checkPhone(phone)) {
            // 判断 redis 是否缓存验证码
            String key = RedisKey.KEY_LOGIN_CODE_REG + phone;
            if (stringRedisTemplate.hasKey(key)) {
                result = RespResult.ok();
                result.setResCodeMsg(ResCodeMsg.SMS_CODE_CAN_USE);
            } else {
                boolean isSuccess = loginService.sendSms(phone);
                if (isSuccess) {
                    result = RespResult.ok();
                }
            }

        } else {
            result.setResCodeMsg(ResCodeMsg.PHONE_FORMAT_ERR);
        }

        return result;
    }

    @ApiOperation(value = "发送注册验证码短信", notes = "发送注册验证码短信，并缓存至 redis 设置有效期限")
    @PostMapping("/code/register")
    public RespResult sendCodeRegister(@RequestBody HashMap phone) {
        System.out.println(phone);
        String newPhone = (String) phone.get("phone");
        System.out.println(newPhone);
        RespResult result = RespResult.fail();
        // 验证手机号
        if (CommonUtil.checkPhone(newPhone)) {
            // 判断 redis 是否缓存验证码
            String key = RedisKey.KEY_SMS_CODE_REG + phone;
            if (stringRedisTemplate.hasKey(key)) {
                result = RespResult.ok();
                result.setResCodeMsg(ResCodeMsg.SMS_CODE_CAN_USE);
            } else {
                boolean isSuccess = smsService.sendSms(newPhone);
                if (isSuccess) {
                    result = RespResult.ok();
                }
            }

        } else {
            result.setResCodeMsg(ResCodeMsg.PHONE_FORMAT_ERR);
        }

        return result;
    }
}
