package com.ylb.work.front.controller;

import com.node.api.pojo.IncomeRecord;
import com.node.api.service.IncomeRecordService;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.front.vo.RespResult;
import com.ylb.work.front.vo.income.IncomeView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "用户收益")
@RequestMapping("/v1")
@RestController
public class IncomeRecordController extends BaseController{

    @ApiOperation(value = "查询收益记录")
    @GetMapping("/income/info")
    public RespResult queryIncomeRecord(@RequestHeader("uid") Integer uid,
                                        @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                        @RequestParam(required = false, defaultValue = "6") Integer pageSize) {
        RespResult result = RespResult.fail();
        if (uid != null && uid > 0) {
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            List<IncomeRecord> incomeRecords = incomeRecordService.queryIncomeRecordByUid(uid, pageNo, pageSize);
            result = RespResult.ok();
            result.setList(toView(incomeRecords));

        }


        return result;
    }



    private List<IncomeView> toView(List<IncomeRecord> incomeRecords) {
        List<IncomeView> target = new ArrayList<>();
        incomeRecords.forEach(incomeRecord -> {
            target.add(new IncomeView(incomeRecord));
        });
        return target;
    }
}
