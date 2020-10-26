package com.imooc.controller.center;

import com.imooc.pojo.Users;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName CenterController
 * @Descrintion
 * @Author bd
 * @Date 2020/6/7 10:39
 * @Version 1.0
 **/
@RestController
@RequestMapping("center")
@Api(value = "用户中心",tags = {"用户中心展示的相关接口"})
public class CenterController {
    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息",notes="获取用户信息",httpMethod = "GET")
    @GetMapping("userInfo")
    public IMOOCJSONResult userInfo(
            @ApiParam(name = "userId",value = "用户Id",required = true)
            @RequestParam String userId) {
        Users users = centerUserService.queryUserInfo(userId);
        return IMOOCJSONResult.ok(users);
    }

    @Autowired
    RedisOperator redisOperator;
    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest httpRequest) {

        redisOperator.set("111","111111111");
        HttpSession session = httpRequest.getSession();
        session.setAttribute("userInfo", "new user");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
        return "ok";
    }

}
