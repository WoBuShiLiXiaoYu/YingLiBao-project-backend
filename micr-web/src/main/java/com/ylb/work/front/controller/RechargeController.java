package com.ylb.work.front.controller;

import com.node.api.pojo.RechargeRecord;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.front.vo.RespResult;
import com.ylb.work.front.vo.recharge.ResultView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "充值业务")
@RequestMapping("/v1")
@RestController
public class RechargeController extends BaseController{

    @ApiOperation(value = "查询充值流水记录")
    @GetMapping("/recharge/records")
    public RespResult queryRechargePage(@RequestHeader("uid") Integer uid,
                                        @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(required = false, defaultValue = "6") Integer pageSize) {
        // 创建响应对象
        RespResult result = RespResult.fail();
        // 验证参数
        if (uid != null && uid > 0) {
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);

            List<RechargeRecord> records = rechargeService.queryRechargeRecordByUid(uid, pageNo, pageSize);
            result = RespResult.ok();
            // 处理响应前端的数据
            result.setList(toView(records));
        }
        return result;
    }


    private List<ResultView> toView(List<RechargeRecord> records) {
        List<ResultView> target = new ArrayList<>();
        records.forEach(record -> {
            target.add(new ResultView(record));
        });
        return target;
    }
}
