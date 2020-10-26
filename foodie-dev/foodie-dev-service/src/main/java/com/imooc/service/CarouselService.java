package com.imooc.service;

import com.imooc.pojo.Carousel;

import java.util.List;

/**
 * @ClassName CarouselService
 * @Descrintion
 * @Author bd
 * @Date 2020/5/24 0:09
 * @Version 1.0
 **/
public interface CarouselService  {

    /*
     * 查询所有轮播图列表
     **/
    List<Carousel> queryAll(Integer isShow);

}
