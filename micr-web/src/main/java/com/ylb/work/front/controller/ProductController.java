package com.ylb.work.front.controller;

import com.node.api.model.MultiProduct;
import com.node.api.pojo.ProductInfo;
import com.ylb.work.common.constants.YLBConstant;
import com.ylb.work.common.enums.ResCodeMsg;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.front.vo.PageInfo;
import com.ylb.work.front.vo.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*@CrossOrigin*/
@Api(tags = "理财产品功能")
@RequestMapping("/v1")
@RestController
public class ProductController extends BaseController{

    @ApiOperation(value = "产品更多信息，分页查询" , notes = "优选、散标")
    @GetMapping("/product/list")
    // 产品类型分页查询
    public RespResult queryProductByType(@RequestParam("pType") Integer productType,
                                         @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "9") Integer pageSize) {
        // 获取失败响应对象
        RespResult respResult = RespResult.fail();
        // 验证请求参数
        if (productType != null &&
                (productType == YLBConstant.PRODUCT_TYPE_XINSHOUBAO
                || productType == YLBConstant.PRODUCT_TYPE_YOUXUAN
                || productType == YLBConstant.PRODUCT_TYPE_SNABIAO)) {
             pageNo = CommonUtil.defaultPageNo(pageNo);
             pageSize = CommonUtil.defaultPageSize(pageSize);

             // 查询记录条数
            Integer nums = productService.queryRecordNumsByType(productType);
            if (nums > 0) {
                // 查询产品
                List<ProductInfo> productInfos = productService.queryByTypeLimit(productType, pageNo, pageSize);
                respResult = RespResult.ok();
                respResult.setList(productInfos);
                respResult.setPage(new PageInfo(pageNo, pageSize, nums));
                return respResult;

            }
        } else {
            respResult.setResCodeMsg(ResCodeMsg.REQUEST_PARAM_ERR);
        }
        return respResult;
    }

    @ApiOperation(value = "首页三类产品列表", notes = "一个新手宝，三个优选，三个散标")
    @GetMapping("/product/index")
    // 查询多种产品信息
    public RespResult queryProductIndex() {
        // 创建响应对象
        // RespResult respResult = new RespResult();
        // 远程调用方法
        MultiProduct multiProduct = productService.queryIndexPageProducts();
        // 通过方法获取响应对象
        RespResult respResult = RespResult.ok();
        respResult.setData(multiProduct);

        return respResult;

    }
}
