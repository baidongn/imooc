package com.imooc.pojo.vo;

/**
 * @ClassName OrderVO
 * @Descrintion
 * @Author bd
 * @Date 2020/6/6 12:48
 * @Version 1.0
 **/
public class OrderVO {
    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }
}
