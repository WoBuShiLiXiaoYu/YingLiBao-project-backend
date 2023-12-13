package com.ylb.work.front.controller;

import com.node.api.model.BaseInfo;
import com.ylb.work.front.vo.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 跨域
/*@CrossOrigin*/
@Api(tags = "平台信息标记")
@RequestMapping("/v1")
@RestController
public class PlatInfoController extends BaseController{

    // 查询平台基本信息
    @ApiOperation(value = "平台三项基本信息", notes = "注册人数，平均利率，累计成交金额")
    @GetMapping("/plat/info")
    public RespResult queryPlatBaseInfo() {
        // 远程调用
        BaseInfo baseInfo = platBaseInfoService.queryPlatBaseInfo();

        /*RespResult respResult = new RespResult();
        respResult.setCode(1000);
        respResult.setMsg("查询平台信息成功");*/
        RespResult respResult = RespResult.ok();
        respResult.setData(baseInfo);

        return respResult;
    }
}
