package com.imooc.controller;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.PayMethod;
import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.MerchantOrdersVO;
import com.imooc.pojo.vo.OrderVO;
import com.imooc.service.OrderService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @ClassName HellController
 * @Descrintion
 * @Author bd
 * @Date 2020/5/16 23:06
 * @Version 1.0
 **/

@Api(value = "订单相关", tags = {"地址相关的api接口"})
@RestController
@RequestMapping("orders")
public class OrdersController extends BaselController {

    @Autowired
    private OrderService orderService;


    //需要引进配置config
    @Autowired
    private RestTemplate restTemplate;

    final static Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO,
                                  HttpServletRequest request, HttpServletResponse response) {

        if (submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type
                && submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type) {
            return IMOOCJSONResult.errorMsg("支付方式不支持");
        }


        System.out.println(submitOrderBO.toString());
        //1.创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();

        //2.移除订单以后，移除购物车中已结算（已提交的订单）
        // TODO: 2020/6/4 整合redis之后，完善购物车中已结算商品清除，并且同步到前端cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        //3.相向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(PAY_RESULTURL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("imoocUserId", "imooc");
        httpHeaders.add("password", "imooc");
        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, httpHeaders);

        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(PAY_MENT_URL, entity, IMOOCJSONResult.class);
        IMOOCJSONResult imoocjsonResult = responseEntity.getBody();
        if (imoocjsonResult.getStatus() != 200) {
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败，请联系管理员");
        }
        return IMOOCJSONResult.ok(orderId);
    }

    @ApiOperation(value = "回调通知", notes = "回调通知", httpMethod = "POST")
    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {

        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return HttpStatus.OK.value();
    }

    @ApiOperation(value = "支付轮询结果", notes = "支付轮询结果", httpMethod = "POST")
    @PostMapping("getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(String orderId) {

        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return IMOOCJSONResult.ok(orderStatus);
    }
}
