package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName Application
 * @Descrintion
 * @Author bd
 * @Date 2020/5/16 23:03
 * @Version 1.0
 **/
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//springboot会默认扫描com.imooc下面的包。但是mapper通用包也需要扫描，只不过他是要让mybatis进行扫描
@MapperScan(basePackages = "com.imooc.mapper")
//扫描所有包，以及相应组件包  自动扫描包也应该加进来
@ComponentScan(basePackages = {"com.imooc","org.n3r.idworker"})
@EnableScheduling  //开启定时任务
@EnableRedisHttpSession //开启使用redis作为spring session
public class Application {
    public static void main(String[] args) {

        //springboot 启动这个类，写class
        //运行加载完之后，将spring的applicationContext（上下文）返回
        SpringApplication.run(Application.class, args);
    }
}
