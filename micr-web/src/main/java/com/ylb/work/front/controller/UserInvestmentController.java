package com.ylb.work.front.controller;

import com.node.api.model.BidInvestInfo;
import com.node.api.pojo.BidInfo;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.front.vo.RespResult;
import com.ylb.work.front.vo.invest.InvestView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "用户投资")
@RequestMapping("/v1")
@RestController
public class UserInvestmentController extends BaseController{

    @ApiOperation(value = "查询投资记录")
    @GetMapping("/invest/info")
    public RespResult queryInvestInfo (@RequestHeader("uid") Integer uid,
                                       @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                       @RequestParam(required = false, defaultValue = "6") Integer pageSize) {
        // 创建响应对象
        RespResult result = RespResult.fail();
        // 验证参数
        if (uid != null && uid > 0) {
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            List<BidInfo> list = investService.queryBidInvertInfoByUid(uid, pageNo, pageSize);
            result = RespResult.ok();
            // 处理数据
            result.setList(toView(list));
        }

        return result;
    }


    private List<InvestView> toView(List<BidInfo> bidInfoList) {
        List<InvestView> target = new ArrayList<>();
        bidInfoList.forEach(bidInfo -> {
            target.add(new InvestView(bidInfo));
        });
        return target;
    }
}
