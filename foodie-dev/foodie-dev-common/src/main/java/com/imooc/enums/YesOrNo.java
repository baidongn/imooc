package com.imooc.enums;
/*
 * @Author bd
 * @Description  是否枚举
 * @Date 22:38 2020/5/22
 * @Param
 * @return
 **/
public enum YesOrNo {
    NO(0,"否"),
    YES(1,"是"),

    ;
    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
