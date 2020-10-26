package com.imooc.enums;
/*
 * @Author bd
 * @Description  性别枚举
 * @Date 22:38 2020/5/22
 * @Param
 * @return
 **/
public enum Sex {
    woman(1,"女"),
    man(2,"男"),
    secret(3,"保密")
    ;
    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
