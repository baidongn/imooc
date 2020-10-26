package com.imooc.service.center.impl;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUsersBO;
import com.imooc.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName CenterUserServiceImpl
 * @Descrintion
 * @Author bd
 * @Date 2020/6/7 10:44
 * @Version 1.0
 **/
@Service
public class CenterUserServiceImpl implements CenterUserService {
    @Autowired(required = false)
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Users users = usersMapper.selectByPrimaryKey(userId);
        //密码传到前端为空
        users.setPassword(null);
        return users;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUsersBO centerUsersBO) {
        Users users = new Users();
        BeanUtils.copyProperties(centerUsersBO, users);
        users.setId(userId);
        users.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(users);

        return queryUserInfo(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users users = new Users();
        users.setId(userId);
        users.setFace(faceUrl);
        users.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(users);

        return queryUserInfo(userId);
    }
}

