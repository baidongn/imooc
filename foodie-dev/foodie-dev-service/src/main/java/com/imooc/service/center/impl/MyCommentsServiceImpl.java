package com.imooc.service.center.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.ItemsCommentsMapperCustom;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.OrderItems;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.Orders;
import com.imooc.pojo.bo.center.OrderItemsCommentBO;
import com.imooc.pojo.vo.MyCommentVO;
import com.imooc.service.center.MyCommentsService;
import com.imooc.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MyCommentsServiceImpl
 * @Descrintion
 * @Author bd
 * @Date 2020/6/9 22:56
 * @Version 1.0
 **/
@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {
    @Autowired(required = false)
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    Sid sid;
    @Autowired(required = false)
    public ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    @Autowired(required = false)
    public OrdersMapper ordersMapper;
    @Autowired(required = false)
    public OrderStatusMapper orderStatusMapper;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {

        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {

        //1.保存评价items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> quetryMap = new HashMap<>();
        quetryMap.put("userId", userId);
        quetryMap.put("commentList", commentList);

        itemsCommentsMapperCustom.saveComments(quetryMap);
        //2.修改订单表为已评价
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsComment(YesOrNo.YES.type);
        //Selective其他属性不用更改
        ordersMapper.updateByPrimaryKeySelective(orders);
        //3.修改订单状态表的留言时间
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> myCommentVOS = itemsCommentsMapperCustom.queryMyComments(map);
        return setterPagedGrid(myCommentVOS,page);
    }


}
