package com.ylb.work.front.controller;

import com.node.api.model.BidInvestInfo;
import com.node.api.pojo.BidInfo;
import com.node.api.pojo.ProductInfo;
import com.node.api.pojo.User;
import com.ylb.work.common.constants.RedisKey;
import com.ylb.work.common.enums.ResCodeMsg;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.front.vo.RespResult;
import com.ylb.work.front.vo.invest.RankVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*@CrossOrigin*/
@Api(tags = "投资理财产品")
@RequestMapping("/v1")
@RestController
public class InvestController extends BaseController{


    @ApiOperation(value = "投资理财产品")
    @PostMapping("/invest/product")
    public RespResult investProduct(@RequestHeader("uid") Integer uid,
                                    @RequestParam("productId") Integer productId,
                                    @RequestParam("money") BigDecimal money) {

        // 创建响应对象
        RespResult result = RespResult.fail();

        // 验证参数
        if ((uid != null && uid >= 0)
                && (productId != null && productId >=0)
                && (money != null && money.intValue() % 100 == 0)) {
            int investResult = investService.investProduct(uid, productId, money);
            switch (investResult) {
                case 0:
                    result.setMsg("投资参数不正确");
                    break;
                case 1:
                    result = RespResult.ok();
                    modifyInvestRank(uid, money);
                    break;
                case 2:
                    result.setMsg("账户不存在");
                    break;
                case 3:
                    result.setMsg("资金不足");
                    break;
                case 4:
                    result.setMsg("产品不存在");
                    break;
            }
        }

        return result;
    }


    @ApiOperation(value = "产品详情", notes = "查询某个产品的详细信息和5条投资记录")
    @GetMapping("/product/info")
    public RespResult queryProductDetail(@RequestParam("productId") Integer productId) {

        // 创建响应对象
        RespResult result = RespResult.fail();

        // 查询产品详细信息
        if (productId != null && productId > 0) {
            ProductInfo productInfo = productService.queryProductInfoById(productId);
            if (productInfo != null) {
                // 查询投资信息
                List<BidInvestInfo> list = investService.queryBidInvestInfoById(productId, 1, 5);
                result = RespResult.ok();
                result.setData(productInfo);
                result.setList(list);
            } else {
                result.setResCodeMsg(ResCodeMsg.PRODUCT_DOWN_ERR);
            }
        }
        return result;
    }

    @ApiOperation(value = "投资排行榜", notes = "显示投资最高的三位信息")
    @GetMapping("/invest/rank")
    public RespResult showInvestRank() {
        // 从 redis 查询数据，zset 类型
        Set<ZSetOperations.TypedTuple<String>> sets = stringRedisTemplate
                .boundZSetOps(RedisKey.KEY_INVSET_RANK)
                .reverseRangeWithScores(0, 2);

        // 创建 list 存储遍历结果
        List<RankVO> rankList = new ArrayList();
        // 遍历 set
        sets.forEach(tuple -> {
            // 手机号
            // tuple.getValue();
            // 投资金额
            // tuple.getScore();

            rankList.add(new RankVO(tuple.getScore(), CommonUtil.tuoMinOfPhone(tuple.getValue())));

        });
        // 创建响应对象
        RespResult respResult = RespResult.ok();
        respResult.setList(rankList);
        return respResult;
    }


    // 更新投资排行榜
    private void modifyInvestRank(Integer uid, BigDecimal money) {
        // 查询用户信息
        User user = userService.queryUserById(uid);
        // 更新 redis 投资排行榜信息
        if (user != null) {
            String key = RedisKey.KEY_INVSET_RANK;
            stringRedisTemplate.boundZSetOps(key).incrementScore(user.getPhone(), money.doubleValue());
        }
    }

}
