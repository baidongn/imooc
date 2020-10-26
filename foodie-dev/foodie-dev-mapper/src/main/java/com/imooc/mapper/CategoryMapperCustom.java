package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {

    /*
     *查出来有 二级 ，下面还有三级，二级是重复的，所以放在一起，再对三级放进list集合中。
    *程序相当于将查询出来的进行分类，放入对应的字段，判断二级目录有的，自动添加进 list的三级目录
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);


    //传进来的map参数，传入到sql语句时改名为paramMap，sql语句取参数的时候从paramMap中取
    List<NewItemsVO> getSixNewItemsLazy(@Param("paramMap") Map<String,Object> map);


}