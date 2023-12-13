package com.ylb.work.front.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 *  Swagger 配置类
 */
@Configuration
public class SwaggerConfigurationSetting {
    // 创建 Docket 对象
    @Bean
    public Docket docket() {
        // 1.创建 Docket 对象
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        // 2.创建 api 信息，接口文档具体描述
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("金融项目")
                .version("1.0")
                .description("前后端分离项目，前端 Vue，后端 SpringBoot + Dubbo")
                // 文档发布者，发布者网址，发布者邮箱
                .contact(new Contact("胡国海", "http://www.HGHJingRongApi.com", "719468256@qq.com"))
                .license("Apache 2.0")
                .build();
        // 3.设置 ApiInfo
        docket = docket.apiInfo(apiInfo);

        //4.设置文档生成的包 指定注解
        docket.select().apis(RequestHandlerSelectors.basePackage("com.ylb.work.front.controller")).build();

        return docket;
    }
}
