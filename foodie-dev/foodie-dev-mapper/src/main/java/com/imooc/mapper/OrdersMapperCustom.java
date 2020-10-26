package com.imooc.mapper;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName OrdersMapperCustom
 * @Descrintion
 * @Author bd
 * @Date 2020/6/7 20:54
 * @Version 1.0
 **/
public interface OrdersMapperCustom {

    List<MyOrdersVO> queryMyOrders(@Param("paramMap") Map<String,Object> map);

    int getMyOrderStatusCounts(@Param("paramMap") Map<String, Object> map);

    List<OrderStatus> getMyOrderTrend(@Param("paramMap") Map<String, Object> map);

}
