package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsComments;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {
    List<ItemCommentVO> queryItemCommemts(@Param("paramMap") Map<String, Object> map);

    List<SearchItemsVO> searchItems(@Param("paramMap") Map<String, Object> map);

    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramMap") Map<String, Object> map);

    List<ShopcartVO> ueryItemsBySpecIds(@Param("paramList") List specIdsList);

    //更新成功--1   失败为0
    int decreaseItemSpecStock(@Param("specId") String specId,@Param("pendingCounys") int pendingCounys);
}