package com.imooc.service.center;

import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUsersBO;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.utils.PagedGridResult;

/**
 * @ClassName UserService
 * @Descrintion
 * @Author bd
 * @Date 2020/5/18 21:55
 * @Version 1.0
 **/
public interface MyOrdersService {
    /*
     * 查询我的订单列表
     **/
    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

    /*
     * 查询我的订单列表
     **/
    Orders queryMyOrder(String userId, String orderId);

    /*
     * 更新订单状态确认收货
     **/
    boolean updateReceiveOrderStatus(String orderId);


    /*
     * 删除订单状态（逻辑删除）
     **/
    boolean deleteOrder(String userId, String orderId);

    /*
     * 查询用户订单数
     **/
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /*
     * 获得分页的订单动向
     **/
    PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize);

}
