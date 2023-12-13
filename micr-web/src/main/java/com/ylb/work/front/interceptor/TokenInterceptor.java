package com.ylb.work.front.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ylb.work.common.enums.ResCodeMsg;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.common.utils.JWTUtil;
import com.ylb.work.front.vo.RespResult;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *  令牌拦截器
 */
public class TokenInterceptor implements HandlerInterceptor {
    private String secret = "";

    public TokenInterceptor(String secret) {
        this.secret = secret;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果是 OPTIONS 请求放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        boolean requestSend = false;

        try {

            // 获取 token，验证
            String headUid = request.getHeader("uid");
            String headToken = request.getHeader("Authorization");
            if (StringUtils.isNotBlank(headToken)) {
                String jwt = headToken.substring(7);
                // 读取 jwt
                JWTUtil jwtUtil = new JWTUtil(secret);
                Claims claims = jwtUtil.readJWT(jwt);

                // 读取数据
                Integer JwtUid = claims.get("uid", Integer.class);
                if (headUid.equals(String.valueOf(JwtUid))) {
                    // 验证成功
                    requestSend = true;
                }
            }

        } catch (Exception e) {
            requestSend = false;
            e.printStackTrace();
        }
        // 验证没有通过
        if (requestSend == false) {
            // 返回错误 json 给前端
            RespResult result = RespResult.fail();
            result.setResCodeMsg(ResCodeMsg.TOKEN_INVALID);
            // response 输出
            String respJson = JSONObject.toJSONString(result);
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print(respJson);
            out.flush();
            out.close();

        }

        return requestSend;
    }
}
