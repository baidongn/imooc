package com.imooc.pojo.vo;

/**
 * @ClassName MerchantOrdersVO
 * @Descrintion
 * @Author bd
 * @Date 2020/6/6 12:41
 * @Version 1.0
 **/
public class MerchantOrdersVO {
    private String merchantOrderId;  //商户订单号
    private String merchantUserId;   //商户方发起的用户 的主键id
    private Integer amount;         //实际支付总金额
    private Integer payMethod;      //支付方式  1.微信   2.支付宝
    private String returnUrl;       //支付成功后的回调地址

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
