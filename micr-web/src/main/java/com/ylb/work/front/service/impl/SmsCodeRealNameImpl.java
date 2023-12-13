package com.ylb.work.front.service.impl;

import com.ylb.work.common.constants.RedisKey;
import com.ylb.work.common.utils.CommonUtil;
import com.ylb.work.front.config.ALiYunSmsConfig;
import com.ylb.work.front.service.SmsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service(value = "smsCodeRealNameImpl")
public class SmsCodeRealNameImpl implements SmsService {

    // 定义短信配置类对象
    @Resource
    private ALiYunSmsConfig aLiYunSmsConfig;

    // redis 模板对象
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean checkSmsCode(String phone, String code) {
        String key = RedisKey.KEY_REAL_NAME_CODE_REG + phone;
        if (stringRedisTemplate.hasKey(key)) {
            // 取出验证码
            String queryCode = stringRedisTemplate.boundValueOps(key).get();
            if (queryCode.equals(code)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean sendSms(String phone) {

        boolean send = false;

        // 获取短信配置类信息
        String url = aLiYunSmsConfig.getUrl();
        String appkey = aLiYunSmsConfig.getAppkey();
        String appCode = aLiYunSmsConfig.getAppCode();
        String appSecret = aLiYunSmsConfig.getAppSecret();
        String content = aLiYunSmsConfig.getContent();
        String loginText = aLiYunSmsConfig.getLoginText();


        // 设置短信内容
        // 更新验证码
        String random = RandomStringUtils.randomNumeric(4);
        System.out.println("实名验证码: random = " + random);
        content = String.format(loginText, random);

        // 拼接 url
        // http(s)://yytz.market.alicloudapi.com/chuangxin/yytz?content=(模板)&mobile=(电话)
        // url = url + "?content=" + content + "&mobile=" + phone;

        // 使用 HttpClient 发送请求
        // 创建 HttpClient 对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建 get 对象
        /*HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization", "APPCODE " + appCode);*/

        // 发送 get 请求
        /*try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 得到返回数据: json
                String text = EntityUtils.toString(response.getEntity());
                *//*text = "{\n" +
                        "  \"status\": \"0\",\n" +
                        "  \"balance\": -40,\n" +
                        "  \"list\": [\n" +
                        "    {\n" +
                        "      \"mid\": \"4D3605838272F189\",\n" +
                        "      \"mobile\": \"13800138001\",\n" +
                        "      \"result\": 0\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";*//*
                // 解析 json
                if (StringUtils.isNoneBlank(text)) {// 判断不为空
                    JSONObject jsonObject = JSONObject.parseObject(text);
                    if ("0".equals(jsonObject.getString("status"))) {// 第三方接口调用成功
                        // 读取 list 中 "result" 的值
                        if (jsonObject.getJSONArray("list")
                                .getJSONObject(0).getInteger("result") == 0) {
                            // 短信发送成功
                            send = true;

                            // 将验证码添加到 redis
                            String key = RedisKey.KEY_SMS_CODE_REG + phone;
                            stringRedisTemplate.boundValueOps(key).set(random, 3, TimeUnit.MINUTES);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        CloseableHttpResponse response = null;
        try {
            response = postMethod(httpClient, url, content, phone, appCode);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
                    || response.getStatusLine().getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                // String text = EntityUtils.toString(response.getEntity());
                String text = "{\n" +
                        "  \"ReturnStatus\": \"Success\",\n" +
                        "  \"Message\": \"ok\",\n" +
                        "  \"RemainPoint\": 420842,\n" +
                        "  \"TaskID\": 18424321,\n" +
                        "  \"SuccessCounts\": 1\n" +
                        "}";
                // 解析 json
                send = CommonUtil.analysisJSON(text);

                // 将验证码添加到 redis
                String key = RedisKey.KEY_REAL_NAME_CODE_REG + phone;
                stringRedisTemplate.boundValueOps(key).set(random, 5, TimeUnit.MINUTES);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        /*// 创建 Post 对象
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization", "APPCODE " + appCode);

        // 设置参数
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("content", content));
        pairs.add(new BasicNameValuePair("mobile", phone));

        try {
            HttpEntity entity = new UrlEncodedFormEntity(pairs);
            httpPost.setEntity(entity);

            // 执行
            CloseableHttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 得到返回数据: json
                // String text = EntityUtils.toString(response.getEntity());
                String text = "{\n" +
                        "  \"ReturnStatus\": \"Success\",\n" +
                        "  \"Message\": \"ok\",\n" +
                        "  \"RemainPoint\": 420842,\n" +
                        "  \"TaskID\": 18424321,\n" +
                        "  \"SuccessCounts\": 1\n" +
                        "}";
                // 解析 json
                if (StringUtils.isNotBlank(text)) {// 判断不为空
                    JSONObject jsonObject = JSONObject.parseObject(text);
                    if ("Success".equals(jsonObject.getString("ReturnStatus"))) {// 第三方接口调用成功
                        if (jsonObject.getInteger("SuccessCounts") == 1) {
                            send = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        return send;
    }




    // post 请求
    public CloseableHttpResponse postMethod(CloseableHttpClient httpClient, String uri, String loginText, String phone, String appCode) throws Exception {
        // 创建 HttpClient 对象
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        // 创建 Post 对象
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("Authorization", "APPCODE " + appCode);

        // 设置参数
        List<NameValuePair> pairs = new ArrayList<>();
        pairs.add(new BasicNameValuePair("content", loginText));
        pairs.add(new BasicNameValuePair("mobile", phone));
        HttpEntity entity = new UrlEncodedFormEntity(pairs);
        httpPost.setEntity(entity);
        httpClient.execute(httpPost);
        // 执行
        response = httpClient.execute(httpPost);

        return response;

    }



    // get 请求，返回 response
    public CloseableHttpResponse getMethod(CloseableHttpClient httpClient, String uri, String loginText, String phone, String appCode) throws IOException {
        // 拼接 url
        String url = CommonUtil.pieceUrl(uri, loginText, phone);

        // 创建 HttpClient 对象
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        // 创建 get 对象
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization", "APPCODE " + appCode);
        response = httpClient.execute(httpGet);
        return response;
    }
}
