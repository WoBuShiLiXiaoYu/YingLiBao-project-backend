package com.node.api.model;

import com.node.api.pojo.ProductInfo;

import java.io.Serializable;
import java.util.List;

/** 多种产品信息*/
public class MultiProduct implements Serializable {
    private static final long serialVersionUID = 3090755581268101156L;

    // 新手宝产品
    private List<ProductInfo> xinShouBao;
    // 优选产品
    private List<ProductInfo> youXuan;
    // 散标产品
    private List<ProductInfo> sanBiao;

    public List<ProductInfo> getXinShouBao() {
        return xinShouBao;
    }

    public void setXinShouBao(List<ProductInfo> xinShouBao) {
        this.xinShouBao = xinShouBao;
    }

    public List<ProductInfo> getYouXuan() {
        return youXuan;
    }

    public void setYouXuan(List<ProductInfo> youXuan) {
        this.youXuan = youXuan;
    }

    public List<ProductInfo> getSanBiao() {
        return sanBiao;
    }

    public void setSanBiao(List<ProductInfo> sanBiao) {
        this.sanBiao = sanBiao;
    }
}
