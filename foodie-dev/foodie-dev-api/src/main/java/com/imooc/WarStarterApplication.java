package com.imooc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ClassName WarStarterApplication
 * @Descrintion  打包war, 增加war包的启动方法
 * @Author bd
 * @Date 2020/6/11 22:44
 * @Version 1.0
 **/
public class WarStarterApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
            //指向Application这个springboot启动类
        return builder.sources(Application.class);
    }
}
