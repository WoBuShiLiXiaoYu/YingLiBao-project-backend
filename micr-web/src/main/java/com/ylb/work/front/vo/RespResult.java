package com.ylb.work.front.vo;

import com.ylb.work.common.enums.ResCodeMsg;

import java.util.List;

/**
 *  统一应答结果
 */
public class RespResult {
    //应答码
    private int code;

    // 应答信息
    private String msg;

    // 单个数据
    private Object data;

    // 集合数据
    private List list;

    // 分页数据
    private PageInfo page;

    // 访问 Token
    private String accessToken;



    // 使用枚举设置值
    public void setResCodeMsg(ResCodeMsg resCodeMsg) {
        this.code = resCodeMsg.getCode();
        this.msg = resCodeMsg.getText();
    }

    // 表示请求成功
    public static RespResult ok() {
        RespResult respResult = new RespResult();

        respResult.setResCodeMsg(ResCodeMsg.SUCC);

        return respResult;
    }

    // 表示请求失败
    public static RespResult fail() {
        RespResult respResult = new RespResult();

        respResult.setResCodeMsg(ResCodeMsg.FAIL);

        return respResult;
    }

    public RespResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RespResult() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
