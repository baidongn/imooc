package com.imooc.service.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.pojo.bo.center.CenterUsersBO;

/**
 * @ClassName UserService
 * @Descrintion
 * @Author bd
 * @Date 2020/5/18 21:55
 * @Version 1.0
 **/
public interface CenterUserService {
    /*
     * 根据用户中心查询用户信息
     **/
    Users queryUserInfo(String userId);

    /*
     * 更新用户信息
     **/
    Users updateUserInfo(String userId, CenterUsersBO centerUsersBO);

        /*
     * 更新用户头像
     **/
    Users updateUserFace(String userId, String faceUrl);
}
