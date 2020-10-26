package com.imooc.service.center;

import com.imooc.pojo.OrderItems;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUsersBO;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * @ClassName UserService
 * @Descrintion
 * @Author bd
 * @Date 2020/5/18 21:55
 * @Version 1.0
 **/
public interface MyCommentsService {
    /*
     * 根据订单id查询相关联的商品
     **/
    List<OrderItems> queryPendingComment(String orderId);

    /*
     * 保存用户的评论
     **/
    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /*
     * 我的评价查询
     **/
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);


}
