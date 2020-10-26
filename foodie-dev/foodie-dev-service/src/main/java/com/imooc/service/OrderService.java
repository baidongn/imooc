package com.imooc.service;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.OrderVO;

import java.util.List;

/**
 * @ClassName CarouselService
 * @Descrintion
 * @Author bd
 * @Date 2020/5/24 0:09
 * @Version 1.0
 **/
public interface OrderService {

    /*
     * 用于创建订单
     **/
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /*
     * 修改订单状态
     **/
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /*
     * 查看订单状态
     **/
    public OrderStatus queryOrderStatusInfo(String orderId);
    /*
     * 关闭超时未支付订单
     **/
    void closeOrder();
}
