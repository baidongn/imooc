package com.imooc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @ClassName HellController
 * @Descrintion
 * @Author bd
 * @Date 2020/5/16 23:06
 * @Version 1.0
 **/

//restController默认返回的都是json格式数据
@RestController
@ApiIgnore//在文档中不会显示，忽略
public class HellController {

    final static Logger logger = LoggerFactory.getLogger(HellController.class);

    @GetMapping("/hello")
    public  Object hello() {
        logger.debug("debug hell");
        logger.info("info hell");
        logger.warn("warn hell");
        logger.error("error hell");
        return "HELLO WORD";

    }

    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request) {
        //虽然在后端进行了设置，但是前段获取不了，只能获取到sessionId  java默认进行存储色存储session到缓存
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", "new user");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
//        session.removeAttribute("userInfo");
        return "ok";
    }
}
