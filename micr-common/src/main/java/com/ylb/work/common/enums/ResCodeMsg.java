package com.ylb.work.common.enums;

import com.ylb.work.common.constants.YLBConstant;

public enum ResCodeMsg {
    UNKOWN(YLBConstant.RESPONSE_CODE_WARN, YLBConstant.RESPONSE_MSG_UNKOWN),
    SUCC(YLBConstant.RESPONSE_CODE_OK, YLBConstant.RESPONSE_MSG_OK),
    FAIL(YLBConstant.RESPONSE_CODE_FAIL, YLBConstant.RESPONSE_MSG_FAIL),
    REQUEST_PARAM_ERR(YLBConstant.REQUEST_PARAM_ERR, YLBConstant.RESPONSE_MSG_PARAM),
    PRODUCT_DOWN_ERR(YLBConstant.PRODUCT_INFO_ERR, YLBConstant.PRODUCT_MSG_DOWN_ERR),
    PHONE_FORMAT_ERR(YLBConstant.PHONE_FORMAT_ERR, YLBConstant.PHONE_MSG_FORMAT_ERR),
    PHONE_EXISTS_ERR(YLBConstant.PHONE_EXISTS_ERR, YLBConstant.PHONE_MSG_EXISTS_ERR),
    SMS_CODE_CAN_USE(YLBConstant.SMS_CODE_CAN_USE, YLBConstant.SMS_MSG_CODE_CAN_USE),
    SMS_CODE_CANNOT_USE(YLBConstant.SMS_CODE_CANNOT_USE, YLBConstant.SMS_MSG_CODE_CANNOT_USE),
    USER_NO_EXISTS(YLBConstant.USER_NO_EXISTS, YLBConstant.USER_NO_EXISTS_MSG),
    USER_REAL_NAME_ERR(YLBConstant.USER_REAL_NAME_ERR, YLBConstant.USER_REAL_NAME_ERR_MSG),
    USER_REAL_NAME_RETRY(YLBConstant.USER_REAL_NAME_RETRY, YLBConstant.USER_REAL_NAME_RETRY_MSG),
    TOKEN_INVALID(YLBConstant.TOKEN_INVALID, YLBConstant.TOKEN_INVALID_MSG)


    ;
    // 应答码
    private int code;
    // 响应信息
    private String text;

    ResCodeMsg(int c, String t) {
        this.code = c;
        this.text = t;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
