package com.imooc.enums;

/*
 * @Author bd
 * @Description  商品评价等级枚举
 * @Date 22:38 2020/5/22
 * @Param
 * @return
 **/
public enum CommentLevel {
    GOOD(1, "好评"),
    NORMAL(2, "中评"),
    BAD(2, "差评"),


    ;
    public final Integer type;
    public final String value;

    CommentLevel(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
