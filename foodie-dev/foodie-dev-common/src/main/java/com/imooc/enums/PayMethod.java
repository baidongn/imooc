package com.imooc.enums;
/*
 * @Author bd
 * @Description  支付类型
 * @Date 22:38 2020/5/22
 * @Param
 * @return
 **/
public enum PayMethod {
    WEIXIN(1,"微信"),
    ALIPAY(2,"支付宝")
    ;
    public final Integer type;
    public final String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
