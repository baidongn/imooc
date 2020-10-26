package com.imooc.controller.center;

import com.imooc.controller.BaselController;
import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUsersBO;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.resource.FileUpload;
import com.imooc.service.center.CenterUserService;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CenterController
 * @Descrintion
 * @Author bd
 * @Date 2020/6/7 10:39
 * @Version 1.0
 **/
@RestController
@RequestMapping("myorders")
@Api(value = "用户中心我的订单", tags = {"用户中心我的订单的相关接口"})
public class MyOrdersController extends BaselController {
    @Autowired
    private MyOrdersService myOrdersService;


    @ApiOperation(value = "获取订单状态概述", notes = "获取订单状态概述", httpMethod = "POST")
    @PostMapping("statusCounts")
    public IMOOCJSONResult statusCounts(@ApiParam(name = "userId", value = "用户Id", required = true)
                                        @RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        OrderStatusCountsVO orderStatusCounts = myOrdersService.getOrderStatusCounts(userId);
        return IMOOCJSONResult.ok(orderStatusCounts);

    }


    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("query")
    public IMOOCJSONResult query(@ApiParam(name = "userId", value = "用户Id", required = true)
                                 @RequestParam String userId,
                                 @ApiParam(name = "orderStatus", value = "订单状态", required = false)
                                 @RequestParam Integer orderStatus,
                                 @ApiParam(name = "page", value = "页码", required = false)
                                 @RequestParam Integer page,
                                 @ApiParam(name = "pageSize", value = "每页数量", required = false)
                                 @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult gridResult = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);
        return IMOOCJSONResult.ok(gridResult);

    }


    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @GetMapping("confirmReceive")
    public IMOOCJSONResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单Id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户Id", required = true)
            @RequestParam String userId) {

        IMOOCJSONResult imoocjsonResult = checkUserOrder(userId, orderId);
        if (imoocjsonResult.getStatus() != HttpStatus.OK.value()) {
            return imoocjsonResult;
        }
        boolean result = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!result) {

            return IMOOCJSONResult.errorMsg("订单确认收货失败");
        }
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @GetMapping("delete")
    public IMOOCJSONResult delete(
            @ApiParam(name = "orderId", value = "订单Id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户Id", required = true)
            @RequestParam String userId) {

        IMOOCJSONResult imoocjsonResult = checkUserOrder(userId, orderId);
        if (imoocjsonResult.getStatus() != HttpStatus.OK.value()) {
            return imoocjsonResult;
        }
        boolean result = myOrdersService.deleteOrder(userId, orderId);
        if (!result) {

            return IMOOCJSONResult.errorMsg("订单删除失败");
        }
        return IMOOCJSONResult.ok();
    }

    /*
     *用于验证订单的准确性，避免非法用户调用
     */
    private IMOOCJSONResult checkUserOrder(String orderId, String userId) {

        Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if (orders == null) {
            return IMOOCJSONResult.errorMsg("订单不存在");
        }
        return IMOOCJSONResult.ok(orders);

    }


    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("trend")
    public IMOOCJSONResult query(@ApiParam(name = "userId", value = "用户Id", required = true)
                                 @RequestParam String userId,
                                 @ApiParam(name = "page", value = "页码", required = false)
                                 @RequestParam Integer page,
                                 @ApiParam(name = "pageSize", value = "每页数量", required = false)
                                 @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult gridResult = myOrdersService.getOrdersTrend(userId, page, pageSize);
        return IMOOCJSONResult.ok(gridResult);

    }
}
