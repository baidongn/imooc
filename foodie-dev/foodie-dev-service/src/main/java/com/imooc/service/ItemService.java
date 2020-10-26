package com.imooc.service;

import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.utils.PagedGridResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CarouselService
 * @Descrintion
 * @Author bd
 * @Date 2020/5/24 0:09
 * @Version 1.0
 **/
public interface ItemService {

    /*
     * 查询商品详情根据商品Id
     **/
    Items queryItemById(String itemId);

    /*
     * 根据商品Id查询商品图片列表
     **/
    List<ItemsImg> queryItemImgList(String itemId);

    /*
     * 根据商品Id查询商品规格
     **/
    List<ItemsSpec> queryItemSpecList(String itemId);

    /*
     * 根据商品Id查询商品参数
     **/
    ItemsParam queryItemParam(String itemId);

    /*
     * 根据商品Id查询商品评价等级数量
     **/
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /*
     * 根据商品Id查询商品评价（分页）
     **/
    PagedGridResult queryPagedCommemts(String itemId, Integer level, Integer page, Integer pageSize);

    /*
     * 搜索商品列表（分页）
     **/
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /*
     * 根据通过分类Id搜索商品列表（分页）
     **/
    PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /*
     * 根据规格ids查询最新购物车中商品数据（用于刷新渲染购物车中的商品数据）
     **/
    List<ShopcartVO> queryItemsBySpecIds(String specIdsList);

    /*
     * 根据商品规格id获取规格对象的具体信息
     **/
    ItemsSpec queryItemSpecById(String specId);

    /*
     * 根据商品规格id获取主图url
     **/
    String queryItemMainImgById(String itemId);

    /*
     * 减少库存
     **/
    void decreaseItemSpecStock(String specId, int buyCounts);
}
