package com.imooc.controller;

import com.imooc.enums.YesOrNo;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.*;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
public class ItemsController extends BaselController {

    final static Logger logger = LoggerFactory.getLogger(ItemsController.class);
    @Autowired
    private ItemService itemService;

    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    public IMOOCJSONResult info(@ApiParam(name = "itemId", value = "商品Id", required = true)
                                @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItems(items);
        itemInfoVO.setItemsImgs(itemsImgs);
        itemInfoVO.setItemParams(itemsParam);
        itemInfoVO.setItemSpecList(itemsSpecs);
        return IMOOCJSONResult.ok(itemInfoVO);
    }

    @GetMapping("/commentLevel")
    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    public IMOOCJSONResult commentLevel(@ApiParam(name = "itemId", value = "商品Id", required = true)
                                        @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);
        return IMOOCJSONResult.ok(countsVO);

    }

    @GetMapping("/comments")
    @ApiOperation(value = "查询商品评价", notes = "查询商品评价", httpMethod = "GET")
    public IMOOCJSONResult comments(@ApiParam(name = "itemId", value = "商品Id", required = true)
                                    @RequestParam String itemId,
                                    @ApiParam(name = "level", value = "评价等级", required = false)
                                    @RequestParam Integer level,
                                    @ApiParam(name = "page", value = "页码", required = false)
                                    @RequestParam Integer page,
                                    @ApiParam(name = "pageSize", value = "每页数量", required = false)
                                    @RequestParam Integer pageSize) {


        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }

        PagedGridResult gridResult = itemService.queryPagedCommemts(itemId, level, page, pageSize);
        return IMOOCJSONResult.ok(gridResult);

    }

    @GetMapping("/search")
    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    public IMOOCJSONResult search(@ApiParam(name = "keywords", value = "关键字", required = true)
                                  @RequestParam String keywords,
                                  @ApiParam(name = "sort", value = "排序", required = false)
                                  @RequestParam String sort,
                                  @ApiParam(name = "page", value = "页码", required = false)
                                  @RequestParam Integer page,
                                  @ApiParam(name = "pageSize", value = "每页数量", required = false)
                                  @RequestParam Integer pageSize) {


        if (StringUtils.isBlank(keywords)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        System.out.println("" + keywords + sort + page + pageSize);
        PagedGridResult gridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return IMOOCJSONResult.ok(gridResult);

    }

    @GetMapping("/catItems")
    @ApiOperation(value = "通过分类Id搜索商品列表", notes = "通过分类Id搜索商品列表", httpMethod = "GET")
    public IMOOCJSONResult catItems(@ApiParam(name = "catId", value = "三级分类Id", required = true)
                                    @RequestParam Integer catId,
                                    @ApiParam(name = "sort", value = "排序", required = false)
                                    @RequestParam String sort,
                                    @ApiParam(name = "page", value = "页码", required = false)
                                    @RequestParam Integer page,
                                    @ApiParam(name = "pageSize", value = "每页数量", required = false)
                                    @RequestParam Integer pageSize) {


        if (catId == null) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult gridResult = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);
        return IMOOCJSONResult.ok(gridResult);
    }


    //用于用户长时间未登录网址，来刷新购物车中的数据（价格会变化），类似京东淘宝
    @GetMapping("/refresh")
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    public IMOOCJSONResult refresh(@ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true,example = "1001,1003,1005")
                                    @RequestParam String itemSpecIds) {

        if (itemSpecIds == null) {
            return IMOOCJSONResult.ok();
        }
        List<ShopcartVO> shopcartVOS = itemService.queryItemsBySpecIds(itemSpecIds);
        return IMOOCJSONResult.ok(shopcartVOS);

    }
}
