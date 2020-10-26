package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName Swagger2
 * @Descrintion
 * @Author bd
 * @Date 2020/5/23 10:24
 * @Version 1.0
 **/
//这个注解一般用来做配置类,一般和bean连用  configuration 是做类似于spring xml 工作的注解 ，component是spring扫描组件时的标识。
@Configuration
@EnableSwagger2
public class Swagger2 {

    //http：//localhost:8088/swagger-uri.html    原路径
    //http：//localhost:8088/doc.html    原路径


    //配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)//指定api类型为 swagger2
                .apiInfo(apiInfo())                        //用于定义api文档汇总信息
                .select().apis(RequestHandlerSelectors.basePackage("com.imooc.controller"))  //指定controller接口的包
                .paths(PathSelectors.any())                //扫描这个包下的所有controller
                .build();
    }


    //只在当前类用 所以用private
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("天天吃货 电商平台接口api")//文档页标题
                .contact(new Contact("imooc", "https://www.imooc.com", "163@imooc.com"))//开发人员联系信息
                .description("专为天天吃货提供的api文档")                //详细的描述
                .version("1.0.1")//文档版本号
                .termsOfServiceUrl("https://www.imooc.com") //网站地址
                .build();


    }

}
