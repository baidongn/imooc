package com.imooc.service;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * @ClassName CategoryService
 * @Descrintion
 * @Author bd
 * @Date 2020/5/24 9:29
 * @Version 1.0
 **/
public interface CategoryService {
    /*
     *查询所有一级分类
     **/
    List<Category> queryAllRootLevelCat();

    /*
     *根据一级分类id，查询子分类信息
     **/
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /*
     *查询首页每个一级目录下的6条最新商品数据
     **/
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);

}
