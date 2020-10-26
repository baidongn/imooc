package com.imooc.config;

import com.imooc.controller.interceptor.UserTokeInterception;
import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebMvcConfig
 * @Descrintion
 * @Author bd
 * @Date 2020/6/6 13:03
 * @Version 1.0
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //实现静态资源的映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                //映射swagger2
                .addResourceLocations("classpath:\\META-INF\\resources\\")
                //映射本地静态资源文件
                .addResourceLocations("file:\\C:\\Users\\Administrator.USER-20190313BK\\Desktop\\02 资料\\Architect-Stage-1-Monolith-master【daobanke.com】\\architect-stage-1-monolith\\第1阶段单体架构\\imooc 静态资源\\img\\");

    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public UserTokeInterception userTokeInterception() {
        return new UserTokeInterception();
    }


    /*
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userTokeInterception())
                .addPathPatterns("/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
