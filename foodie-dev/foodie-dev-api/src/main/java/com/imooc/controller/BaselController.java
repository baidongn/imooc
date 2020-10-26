package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.utils.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;


/**
 * @ClassName HellController
 * @Descrintion
 * @Author bd
 * @Date 2020/5/16 23:06
 * @Version 1.0
 **/
@Controller
public class BaselController {

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 10;

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final String PAY_RESULTURL = " ";   //通知地址
    public static final String PAY_MENT_URL = " ";   //支付中心调用地址
    public static final String REDIS_USER_TOKEN = "redis_user_token";//用户token

    @Autowired
    RedisOperator redisOperator;


    public static final String IMAGE_USER_FACE_LOCATION = "C:\\Users\\Administrator.USER-20190313BK\\Desktop\\02 资料\\Architect-Stage-1-Monolith-master【daobanke.com】\\architect-stage-1-monolith\\第1阶段单体架构\\imooc 静态资源\\img";

    public UsersVO convenUserVo(Users user) {
        //实现用户的redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user,usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        redisOperator.set(BaselController.REDIS_USER_TOKEN+":"+user.getId(),uniqueToken);
        return usersVO;
    }
}
