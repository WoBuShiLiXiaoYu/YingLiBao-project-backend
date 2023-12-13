package com.ylb.work.front;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.ylb.work.common.utils.JWTUtil;
import com.ylb.work.front.config.ALiYunSmsConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 使配置类生效
//@EnableConfigurationProperties(ALiYunSmsConfig.class)
// 启动 swagger
@EnableSwaggerBootstrapUI
@EnableSwagger2
// 启动 dubbo
@EnableDubbo
/*@MapperScan(basePackages = "com.ylb.work.front")*/
@SpringBootApplication
public class MicrWebApplication {

    @Value("${jwt.secret}")
    private String secretKye;

    // 创建 jwtUtil
    @Bean
    public JWTUtil jwtUtil() {
        JWTUtil jwtUtil = new JWTUtil(secretKye);
        return jwtUtil;
    }

    public static void main(String[] args) {
        SpringApplication.run(MicrWebApplication.class, args);
    }

}
