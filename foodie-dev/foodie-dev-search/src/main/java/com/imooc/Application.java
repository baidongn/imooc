package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName Application
 * @Descrintion
 * @Author bd
 * @Date 2020/5/16 23:03
 * @Version 1.0
 **/
@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        //springboot 启动这个类，写class
        //运行加载完之后，将spring的applicationContext（上下文）返回
        SpringApplication.run(Application.class, args);
    }
}
