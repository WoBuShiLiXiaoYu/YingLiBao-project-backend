package com.ylb.work.front.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.node.api.service.UserService;
import com.ylb.work.common.constants.RedisKey;
import com.ylb.work.front.config.RealNameConfig;
import com.ylb.work.front.service.RealNameService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service(value = "realNameImpl")
public class RealNameImpl implements RealNameService {

    // 定义短信配置类对象
    @Resource
    private RealNameConfig realNameConfig;

    @DubboReference(interfaceClass = UserService.class, version = "1.0")
    private UserService userService;

    // redis 模板对象
    //@Resource
    //private StringRedisTemplate stringRedisTemplate;

    //@Override
    /*public boolean checkSmsCode(String phone, String code) {
        String key = RedisKey.KEY_REAL_NAME_CODE_REG + phone;
        if (stringRedisTemplate.hasKey(key)) {
            // 取出验证码
            String queryCode = stringRedisTemplate.boundValueOps(key).get();
            if (queryCode.equals(code)) {
                return true;
            }
        }
        return false;
    }*/

    @Override
    public boolean handleRealName(String phone, String name, String idCard) {

        boolean send = false;

        // 获取配置类信息
        String url = realNameConfig.getUrl();
        String appCode = realNameConfig.getAppCode();

        Map<String, Object> paris = new HashMap<>();
        paris.put("idcard", idCard);
        paris.put("name", name);


        try {
            String result = this.doPost(url, paris, appCode);
            result = "{ \n" +
                    "  \"code\": 200, \n" + // 接口返回码【注意：不等于HTTP响应状态码】
                    "  \"msg\": \"成功\",\n" +   // 接口返回码对应的描述信息
                    "  \"taskNo\":  \"89889414666618686491\",\n" +  // 任务订单号【可反馈服务商复核对应订单】
                    "  \"data\": {\n" +
                    "    \"result\": 0,\n" +  // 0 一致，1 不一致，2 无记录
                    "    \"desc\": \"一致\",\n" +   // 验证结果描述信息
                    "    \"sex\": \"男\",\n" +  // 性别
                    "    \"birthday\": \"19940320\",\n" +  // 生日
                    "    \"address\": \"浙江省杭州市西湖区\"\n" +  // 地址
                    "  }\n" +
                    "}";
            if (StringUtils.isNotBlank(result)) {
                JSONObject respObject = JSONObject.parseObject(result);
                if (200 == respObject.getInteger("code") && "成功".equals(respObject.getString("msg"))) {// 第三方接口调用成功
                    if (0 == respObject.getJSONObject("data").getInteger("result")) {// 验证成功
                        // 更新数据
                        boolean modifyResult = userService.modifyRealName(phone, name, idCard);
                        send = modifyResult;

                        // 将验证码存入 redis
                        /*String key = RedisKey.KEY_REAL_NAME_CODE_REG + phone;
                        stringRedisTemplate.boundValueOps(key).set(random, 5, TimeUnit.MINUTES);*/
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return send;
    }

    // post 请求
    public String doPost(String url, Map<String, Object> params, String appCode) throws Exception {
        // 定义 httpclient 对象
        CloseableHttpClient httpClient = null;
        // 定义 post 对象
        CloseableHttpResponse response = null;
        String result = "";

        try {
            // 创建对象
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Access-Control-Expose-Headers","Authorization");
            httpPost.setHeader("Authorization","APPCODE " + appCode);
            //httpPost.setHeader("Authorization", "APPCODE " + appCode);

            // 配置请求参数实例
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(6000)    // 设置连接主机服务超时时间
                    .setConnectionRequestTimeout(6000)  // 设置连接请求超时时间
                    .setSocketTimeout(6000)     // 设置读取数据连接超时时间
                    .build();
            // 为 post 请求设置配置
            httpPost.setConfig(requestConfig);
            // 封装参数
            if (null != params && params.size() > 0) {
                List<NameValuePair> pairs = new ArrayList<>();
                // 通过 map 集合取值
                Set<Map.Entry<String, Object>> entrySet = params.entrySet();
                // 迭代
                for (Map.Entry<String, Object> entry : entrySet) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    pairs.add(new BasicNameValuePair(key, value.toString()));
                }
                // 为 post 设置封装号的参数
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
            }
            // 执行
            response = httpClient.execute(httpPost);
            // 获取 json 对象数据
            HttpEntity entity = response.getEntity();
            // 将 json 对象转换为字符串
            result = EntityUtils.toString(entity);

        }  finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
