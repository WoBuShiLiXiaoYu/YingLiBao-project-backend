package com.ylb.work.front.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public interface SmsService {



    /** 发送短信
     * @param phone 手机号
     * @return true:发送成功    false:发送失败
     */
    boolean sendSms(String phone);

    boolean checkSmsCode(String phone, String code);
}
