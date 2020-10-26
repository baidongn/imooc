package com.imooc.controller;

import com.imooc.pojo.bo.UserBO;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @ClassName PassportController
 * @Descrintion
 * @Author bd
 * @Date 2020/5/18 22:19
 * @Version 1.0
 **/
@RestController
@RequestMapping("passport")
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
public class PassportController extends BaselController {
    @Autowired
    UserService userService;
    @Autowired
    RedisOperator redisOperator;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    //@RequestParam代表是一种请求参数而不是一种路径参数
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {
        /*
         * 1.判断用户名不能为空
         * 2.查找用户名是否存在
         **/
//        isBlank()判断是不是空值和空字符串   isEmpty()判断是不是空值
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名以存在");
        }
        return IMOOCJSONResult.ok();

    }

    @ApiOperation(value = "用户注册", notes = "用户名注册", httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO, HttpServletRequest request,
                                  HttpServletResponse response) {
        System.out.println(userBO);
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        //0.判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }
        //1.查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        //2.密码长度不能少于6位
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能小于6");
        }
        //3.判断两侧密码是否一致
        if (!password.equals(confirmPassword)) {
            return IMOOCJSONResult.errorMsg("两次密码输入不一致");
        }
        //实现注册
        Users user = userService.createUser(userBO);
//        user = setNULLProperty(user);

        //实现用户的redis会话
        UsersVO usersVO = convenUserVo(user);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(user), true);

        // TODO: 2020/5/30 同步购物车数据

        return IMOOCJSONResult.ok(user);
    }



    @ApiOperation(value = "用户登录", notes = "用户名登录", httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        //0.判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }
        //实现登录
        Users users = userService.queryUserForLogin(username,
                MD5Utils.getMD5Str(password));

        if (users == null) {
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }

        users = setNULLProperty(users);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);
        // TODO: 2020/5/30 同步购物车数据

        return IMOOCJSONResult.ok(users);
    }

    private Users setNULLProperty(Users userResult) {
        userResult.setRealname(null);
        userResult.setPassword(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        return userResult;
    }


    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId, HttpServletRequest request,
                                  HttpServletResponse response) {
        System.out.println("user" + userId);

        //清楚用户相关cookie，防止别人拿到
        CookieUtils.deleteCookie(request, response, "user");

        //TODO 用户退出登录需要清空购物车

        //TODO 分布式会话中需要清楚用户数据

        return IMOOCJSONResult.ok();
    }
}
