package com.imooc.service.impl;

import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.bo.UserBO;
import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @ClassName UserServiceImpl
 * @Descrintion
 * @Author bd
 * @Date 2020/5/18 22:02
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    public UsersMapper usersMapper;
    @Autowired
    private Sid sid;

    public static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        //创建ecample
        Example userExample = new Example(Users.class);
        //创建 条件
        Example.Criteria userCriteria = userExample.createCriteria();
        //判断是否相等 数据库中的字段   传入的数据
        userCriteria.andEqualTo("username", username);
        //查找是否有一条记录
        Users result = usersMapper.selectOneByExample(userExample);

        //true代表有，false代表没有
        return result == null ? false : true;
    }

    //因为是创建，所以必须有一个事务，出错了可以去回滚
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users createUser(UserBO users) {
        String userId = sid.nextShort();
        Users user = new Users();
        user.setId(userId);
        user.setUsername(users.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(users.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //默认用户昵称同用户名一样
        user.setNickname(users.getUsername());
        //默认头像
        user.setFace(USER_FACE);
        //默认生日
        user.setBirthday(DateUtil.stringToDate("1990-01-01"));
        //默认性别  不推荐直接写死数据  用枚举
        user.setSex(Sex.secret.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        usersMapper.insert(user);

        return user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        //创建ecample
        Example userExample = new Example(Users.class);
        //创建 条件
        Example.Criteria userCriteria = userExample.createCriteria();
        //判断是否相等 数据库中的字段   传入的数据
        userCriteria.andEqualTo("username", username);
        userCriteria.andEqualTo("password", password);

        //查找是否有一条记录
        Users result = usersMapper.selectOneByExample(userExample);

        //true代表有，false代表没有
        return result ;
    }
}
