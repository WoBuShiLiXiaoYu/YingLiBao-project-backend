package com.ylb.work.front.service;

public interface RealNameService {
    boolean handleRealName(String phone, String name, String idCard);

    //boolean checkSmsCode(String phone, String code);
}
