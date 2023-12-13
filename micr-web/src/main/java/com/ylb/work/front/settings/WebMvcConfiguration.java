package com.ylb.work.front.settings;

import com.ylb.work.front.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域设置
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TokenInterceptor tokenInterceptor = new TokenInterceptor(jwtSecret);
        String[] addPath = {"/v1/user/realname"};
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns(addPath);
    }

    // 处理跨域请求
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("==========addCorsMappings==========");

                // addMapping：处理请求地址，拦截这些地址，使用跨域处理
        registry.addMapping("/**")
                // allowedOriginPatterns：可跨域的域名
                .allowedOriginPatterns("http://localhost:8080")
                //allowedMethods：请求方式
                .allowedMethods("POST", "GET", "HEAD", "PUT", "DELETE", "OPTIONS")
                // allowCredentials：是否发送 cookie
                .allowCredentials(true)
                // maxAge：最大时间
                .maxAge(3600)
                // allowedHeaders：支持跨域的请求头
                .allowedHeaders("*");
    }
}
