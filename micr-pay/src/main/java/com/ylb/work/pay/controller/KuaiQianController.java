package com.ylb.work.pay.controller;

import com.node.api.pojo.User;
import com.ylb.work.pay.service.impl.KuaiQianServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@RequestMapping("/kq")
@Controller
public class KuaiQianController {
    @Resource
    private KuaiQianServiceImpl kuaiQianService;

    @GetMapping("/rece/recharge")
    public String receFrontRechargeKQ(Integer uid, BigDecimal rechargeMoney, Model model) {

        String view = "err";
        if ((uid != null && uid > 0) && (rechargeMoney.doubleValue() > 0 && rechargeMoney != null)) {
            try {
                User user = kuaiQianService.queryUser(uid);
                if (user != null) {

                    // 设置请求参数
                    Map<String, String> data = kuaiQianService.generateForDate(uid, rechargeMoney, user.getPhone());
                    model.addAllAttributes(data);
                    // 提交请求
                    view = "kqFrom";
                    // 创建充值记录
                    kuaiQianService.addRecharge(uid, rechargeMoney, data.get("orderId"));
                    // 订单号存好 redis
                    kuaiQianService.addOrderIdToRedis(data.get("orderId"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return view;

    }

    // 接受快钱给商家的支付结果
    @GetMapping("/rece/notify")
    @ResponseBody
    public String payResultNotify(HttpServletRequest request) {
        kuaiQianService.kqNotify(request);

        return "<result>1</result><redirecturl>http://localhost:8080</redirecturl>";
    }

    @GetMapping("/rece/query")
    @ResponseBody
    public String queryKQOrder() {

        kuaiQianService.handleQueryOrder();
        return "接受查询请求";
    }
}
