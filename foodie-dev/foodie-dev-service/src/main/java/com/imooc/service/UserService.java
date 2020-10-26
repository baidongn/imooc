package com.imooc.service;

import com.imooc.pojo.bo.UserBO;
import com.imooc.pojo.Users;

/**
 * @ClassName UserService
 * @Descrintion
 * @Author bd
 * @Date 2020/5/18 21:55
 * @Version 1.0
 **/
public interface UserService {

    /*
     * 判断用户名是不是存在
     **/
    boolean queryUsernameIsExist(String username);

    /*
     *创建用户
     **/
    Users createUser(UserBO users);

    /*
     * 检索用户名和密码是否匹配，用于登录
     **/
    Users queryUserForLogin(String username,String password);

}
