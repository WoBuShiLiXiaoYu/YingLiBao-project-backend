package com.ylb.work.front.vo.view;

import java.io.Serializable;

public class RealNameView implements Serializable {
    private static final long serialVersionUID = 4160343588965046560L;
    // 手机号
    private String phone;
    // 姓名
    private String name;
    // 身份证号
    private String idCard;
    // 验证码
    private String code;

    public RealNameView() {
    }

    public RealNameView(String phone, String name, String idCard, String code) {
        this.phone = phone;
        this.name = name;
        this.idCard = idCard;
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
